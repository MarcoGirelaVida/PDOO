/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marco
 */
public class Monster {
    private static final int INITIAL_HEALTH = 5;
    private final String name;
    private final float intelligence;
    private final float strength;
    private float health;
    private int row;
    private int col;
    
    public Monster(String name, float intelligence, float strength){
        this.name = name;
        this.intelligence = intelligence;
        this.health = INITIAL_HEALTH;
        this.strength = strength;
        this.row = -1;
        this.col = -1;
    }
    
    public boolean dead(){
        return health == 0;
    }
    
    public float attack(){
        return Dice.intensity(strength);
    }
    
    public boolean defend(float receivedAttack){
        boolean isDead = this.dead();
        
        if (!isDead){
            float defensiveEnergy = Dice.intensity(intelligence);
            
            if (defensiveEnergy < receivedAttack){
                gotWounded();
                isDead = this.dead();
            } 
        }
        
        return isDead;
    }
    
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    
    public String toString(){
        String header = "MONSTER " + this.name + "\n";
        String location = "POSTION: [ "+ this.row + ", " + this.col + " ]\n";
        String strengthString = "STRENGTH: " + this.strength + "\n";
        String healthString = "HEALTH: " + this.health + "\n";
        String intelligenceString = "INTELLIGENCE: " + this.intelligence + "\n";
        
        return header + location + strengthString + healthString + intelligenceString;
    }
    
    private void gotWounded(){
        health --;
    }
}
