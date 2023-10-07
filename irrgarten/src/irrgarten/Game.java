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
        this.currentPlayerIndex = nplayers;
    }
    
    public boolean finished(){
        
    }
    
    public boolean nextStep(Directions preferredDirection){
        
    }
    
    public GameState getGameState(){
        
    }
    
    private void configureLabyrinth(){
        
    }
    
    private void nextPlayer(){
        
    }
    
    private Directions actualDirection(Direction preferredDirection){
        
    }
    
    private GameChracter combat(Monster monster){
        
    }
    
    private void manageReward(GameCharacter winner){
        
    }
    
    private void manageResurrection(){
        
    }
    
    private void logPlayerWon(){
        
    }
    
    private void logMonsterWon(){
        
    }
    
    private void logResurrected(){
        
    }
    
    private void logPlayerSkipOrders(){
        
    }
    
    private void logPlayerNoOrders(){
        
    }
    
    private void logNoMonster(){
        
    }
    
    private void logRounds(int rounds, int max){
        
    }
}
