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
        
        for(int i=0; i < players.size(); i++){
            Player p = new Player((char)i, Dice.randomIntelligence(), Dice.randomStrength());
            players.add(p);
        }
        
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        this.currentPlayer = players.get(this.currentPlayerIndex);
        labyrinth = new Labyrinth(20, 20, Dice.randomPos(20), Dice.randomPos(20));
        
        labyrinth.spreadPlayers(players);
        
        log = "GAME STARTS\n";
        GameState gameState = this.getGameState();
        log += gameState.getLog();
    }
    
    public boolean finished(){
        return labyrinth.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        
    }
    
    public final GameState getGameState(){
        // No sé si la llamada a toString es lo que se necesita
        GameState gameState = new GameState(labyrinth.toString(), players.toString(),
                                    monsters.toString(), this.currentPlayer.getNumber(),
                                    this.finished(), this.log);
        return gameState;
    }
    
    private void configureLabyrinth(){
        // No se hacer esta clase, además de que no debería estar en la clase Labyrinth?
    }
    
    private void nextPlayer(){
        this.currentPlayerIndex = (currentPlayerIndex + 1) % currentPlayerIndex;
        this.currentPlayer = players.get(currentPlayerIndex);
    }
    
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = this.currentPlayer.getRow();
        int currentCol = this.currentPlayer.getCol();
        ArrayList<Directions> validMoves = labyrinth.validMoves(currentRow, currentCol);
        
        return this.currentPlayer.move(preferredDirection, validMoves);
    }
    
    private GameCharacter combat(Monster monster){
        
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
        log += "INVALID ORDER. PLAYER DID NOT COMMITED TO IT\n";
    }
    
    private void logNoMonster(){
        log += "PLAYER MOVED TO A VOID SQUARE OR DID NOT MOVE\n";
    }
    
    private void logRounds(int rounds, int max){
        log += "THE COMBAT HAVE DONE: " + rounds + " of " + this.MAX_ROUNDS + " ROUNDS";
    }
}
