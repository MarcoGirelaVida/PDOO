/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marco
 */
import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    private Labyrinth labyrinth;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    
    public Game(int nplayers){
        players = new ArrayList<>(nplayers);
        monsters = new ArrayList<>();
        
        for(int i=0; i < nplayers; i++){
            char player_number = Character.forDigit(i, 10);
            Player p = new Player(player_number, Dice.randomIntelligence(), Dice.randomStrength());
            players.add(p);
        }
        
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        this.currentPlayer = players.get(this.currentPlayerIndex);
        
        final int NROWS = 20;
        final int NCOLS = 20;
        final int EXITROW = Dice.randomPos(NROWS);
        final int EXITCOL = Dice.randomPos(NCOLS);
        labyrinth = new Labyrinth(NROWS, NCOLS, EXITROW, EXITCOL);
        
        configureLabyrinth(NROWS, NCOLS, EXITROW, EXITCOL);
        this.labyrinth.spreadPlayers(players);
        
        log = "GAME STARTS\n";
        GameState gameState = this.getGameState();
        log += gameState.getLog();
    }
    
    public boolean finished(){
        return labyrinth.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        this.log = "";
        boolean dead = this.currentPlayer.dead();
        if (!dead) {
            Directions direction = this.actualDirection(preferredDirection);
            
            if (direction != preferredDirection) {
                this.logPlayerNoOrders();
            }
            
            Monster monster = this.labyrinth.putPlayer(direction, currentPlayer);
            
            if (monster == null) {
                this.logNoMonster();
            } else {
                GameCharacter winner = this.combat(monster);
                this.manageReward(winner);
            }
        }
        else {
            this.manageResurrection();
        }
        
        boolean endGame = this.finished();
        
        if (!endGame) {
            this.nextPlayer();
        }
        
        return endGame;
    }
    
    public final GameState getGameState(){
        // No sé si la llamada a toString es lo que se necesita
        String strPlayers = players.toString();
        String strMonsters = monsters.toString();
        String strLabyrinth = labyrinth.toString();
        GameState gameState = new GameState(strPlayers, strMonsters, strLabyrinth,
                    this.currentPlayer.getNumber(), this.finished(), this.log);
        return gameState;
    }
    
    private void configureLabyrinth(int NROWS, int NCOLS, int EXITROW, int EXITCOL) {
                    
        int row, col;
        final float percentage_monster_summon = 0.1f;
        final float percentage_block_summon = 0.3f;
        
        for (int i = 0; i <  NROWS*NCOLS*percentage_block_summon; i++) {
            
            do {
                row = Dice.randomPos(NROWS-1);
                col = Dice.randomPos(NROWS-1);
            } while (row == EXITCOL && col == EXITCOL);

            labyrinth.addBlock(Orientation.VERTICAL, row, col, 1);
        }
        
        for (int i = 0; i < NROWS*NCOLS*percentage_monster_summon; i++) {

            do {
                row = Dice.randomPos(NROWS-1);
                col = Dice.randomPos(NROWS-1);
            } while (row == EXITROW && col == EXITCOL);

            Monster monster = new Monster("MONSTER " + i, Dice.randomIntelligence(),Dice.randomStrength());
            
            
            if(labyrinth.addMonster(row, col, monster))
                monsters.add(monster);
        }
    }
    
    private void nextPlayer(){
        this.currentPlayerIndex = (currentPlayerIndex + 1) % this.players.size();
        this.currentPlayer = players.get(currentPlayerIndex);
    }
    
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = this.currentPlayer.getRow();
        int currentCol = this.currentPlayer.getCol();
        ArrayList<Directions> validMoves = labyrinth.validMoves(currentRow, currentCol);
        
        return this.currentPlayer.move(preferredDirection, validMoves);
    }
    
    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        
        float playerAttack = this.currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        
        while ((!lose) && (rounds < MAX_ROUNDS)){
            winner = GameCharacter.MONSTER;
            rounds++;
            
            float monsterAttack = monster.attack();
            lose = this.currentPlayer.defend(monsterAttack);
            
            if (!lose) {
                playerAttack = this.currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }
        
        this.logRounds(rounds, MAX_ROUNDS);
        
        return winner;
    }
    
    private void manageReward(GameCharacter winner){
        if (winner == GameCharacter.PLAYER){
            this.currentPlayer.receiveReward();
            this.logPlayerWon();
        }
        else{
            this.logMonsterWon();
        }
    }
    
    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();
        
        if (resurrect){
            this.currentPlayer.resurrect();
            this.logResurrected();
        }
        else{
            this.logPlayerSkipOrders(); 
        }
    }
    
    private void logPlayerWon(){
        log += "PLAYER WON THE BATTLE\n";
    }
    
    private void logMonsterWon(){
        log += "MONSTER WON THE BATTLE\n";
    }
    
    private void logResurrected(){
        log += "PLAYER HAS RESURRECTED\n";
    }
    
    private void logPlayerSkipOrders(){
        log += "DEAD PLAYER. TURN SKIPPED\n";
    }
    
    private void logPlayerNoOrders(){
        log += "INVALID ORDER. PLAYER DID NOT COMMITTED TO IT\n";
    }
    
    private void logNoMonster(){
        log += "PLAYER MOVED TO A VOID SQUARE OR DID NOT MOVE\n";
    }
    
    private void logRounds(int rounds, int max){
        log += "THE COMBAT HAVE GOT: " + rounds + " of " + max + " ROUNDS\n";
    }
}
