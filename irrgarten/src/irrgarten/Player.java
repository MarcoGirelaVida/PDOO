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

public class Player {
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    private final String name;
    private final char number;
    private final float intelligence;
    private final float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits;
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    
    // Number no debería ser un int?
    public Player(char number, float intelligence, float strength){
        weapons = new ArrayList<>();
        shields = new ArrayList<>();
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.consecutiveHits = 0;
        this.name = "Player #" + number;
        this.health = INITIAL_HEALTH;
        this.row = -1;
        this.col = -1;
    }
    
    public void resurrect(){
        this.health = INITIAL_HEALTH;
        this.consecutiveHits = 0;
        weapons.clear();
        shields.clear();
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public char getNumber(){
        return number;
    }
    
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    public boolean dead(){
        return this.health == 0;
    }
    
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        
        if ((size > 0) && (!contained)){
            return validMoves.get(0);
        }
        else{
            return direction;
        }
    }
    
    public float attack(){
        return this.strength + this.sumWeapons();
    }
    
    public boolean defend(float receivedAttack){
        return this.manageHit(receivedAttack);
    }
    
    public void receiveReward(){
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        
        for(int i = 0; i < wReward; i++){
            Weapon wnew = new Weapon();
            receiveWeapon(wnew);
        }
        
        for (int i = 0; i < sReward; i++){
            Shield snew = new Shield();
            receiveShield(snew);
        }
        
        int extraHealth = Dice.healthReward();
        
        health += extraHealth;
    }
    
    public String toString(){
        String header = "PLAYER \"" + this.name + "\"\n";
        String location = "POSTION: [ "+ this.row + ", " + this.col + " ]\n";
        String strengthString = "STRENGTH: " + this.strength + "\n";
        String healthString = "HEALTH: " + this.health + "\n";
        String intelligenceString = "INTELLIGENCE: " + this.intelligence + "\n";
        String consecutivehitsString = "CONSECUTIVE HITS: " + this.consecutiveHits + "\n";
        String weaponsString = "";
        String shieldsString = "";
        
        System.err.println("Tamaño de wapons: " + weapons.size());
        for (Weapon w : weapons){
            weaponsString += w.toString();
            weaponsString += "\n";
        }

        for (Shield s : shields){
            shieldsString += s.toString();
            shieldsString += "\n";
        }
        

        return header + location + strengthString + healthString + intelligenceString
                + consecutivehitsString + weaponsString + shieldsString;
    }
    
    private void receiveWeapon(Weapon w){
        for (Weapon wi : this.weapons){
            boolean discard = wi.discard();
            if (discard) {
                this.weapons.remove(wi);
            }
        }
        
        int size = this.weapons.size();
        if (size < MAX_WEAPONS) {
            this.weapons.add(w);
        }
    }
    
    private void receiveShield(Shield s){
        for (Shield si : this.shields){
            boolean discard = si.discard();
            if (discard){
                this.shields.add(si);
            }
        }
        
        int size = this.shields.size();
        if (size < MAX_SHIELDS) {
            this.shields.add(s);
        }
    }
    
    private Weapon newWeapon(){
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }
    
    private Shield newShield(){
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }
    
    private float sumWeapons(){
        float sum = 0;
        
        for (int i=0; i < this.weapons.size(); i++){
            sum += (this.weapons.get(i)).attack();
        }
        
        return sum;
    }
    
    private float sumShields(){
        float sum = 0;
        
        for (int i=0; i < this.shields.size(); i++){
            sum += (this.shields.get(i)).protect();
        }
        
        return sum;
    }
    
    private float defensiveEnergy(){
        return this.intelligence + this.sumShields();
    }
    
    private boolean manageHit(float receivedAttack){
        float defense = this.defensiveEnergy();
        boolean lose = false;
        
        if (defense < receivedAttack){
            this.gotWounded();
            this.incConsecutiveHits();
        }
        else{
            this.resetHits();
         }
        
        if((consecutiveHits == HITS2LOSE) || dead()){
            resetHits();
            lose = true;
        }
        
        return lose;
    }
    
    private void resetHits(){
        this.consecutiveHits = 0;
    }
    
    private void gotWounded(){
        this.health--;
    }
    
    private void incConsecutiveHits(){
        this.consecutiveHits++;
    }
}
