/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marco
 */
public class Weapon {
    private float power;
    private int uses;
    
    // Constructor with parameters
    public Weapon(float power, int uses){
        this.power = power;
        this.uses = uses;
    }
    
    // Constructor without parameters, it set the attributes to 0
    public Weapon(){
        this(Dice.weaponPower(),Dice.usesLeft());
    }
    
    public float attack(){   
        
        float effectivePower = 0;
        
        if (uses > 0){
            effectivePower = power;
            uses--;
        }
        
        return effectivePower;
    }
    
    public String toString(){
        return "W[" + power + ", " + uses + "]";
    }
    
    public boolean discard(){
        return Dice.discardElement(uses);
    }
}
