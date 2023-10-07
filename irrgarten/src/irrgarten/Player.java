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
    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits;
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    
    public Player(char number, float intelligence, float strength){
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.consecutiveHits = 0;
        this.name = "Player #" + number;
        this.health = this.INITIAL_HEALTH;
    }
    
    public void resurrect(){
        this.health = this.INITIAL_HEALTH;
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
            receiveWeapon(w: wnew);
        }
        
        for (int i = 0; i < sReward; i++){
            Shield snew = new Shield();
            receiveShield(s: snew);
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
        
        for(int i = 0; i < Math.max(this.weapons.size(), this.shields.size()); i++){
            if (i < this.weapons.size()){
                weaponsString += (this.weapons.get(i)).toString();
            }
            
            if (i < this.shields.size()){
                shieldsString += (this.shields.get(i)).toString();
            }
        }

        return header + location + strengthString + healthString + intelligenceString
                + consecutivehitsString + weaponsString + shieldsString;
    }
    
    private void receiveWeapon(Weapon w){

    }
    
    private void receiveShield(Shield s){
        
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
        
        if((consecutiveHits == this.HITS2LOSE) || dead()){
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
