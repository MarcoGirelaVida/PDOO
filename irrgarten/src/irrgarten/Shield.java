/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marco
 */
public class Shield {
    private float protection;
    private int uses;
    
    public Shield(float protection, int uses){
        if (protection < 0 || uses < 0){ 
            throw new IllegalArgumentException("Parámetros incorrectos.");
        }
            
        this.protection = protection;
        this.uses = uses;
    }
    
    public Shield(){
        this(0.0f, 0);
    }
    
    public float protect(){   

        float effectiveProtection = 0;

        if (uses > 0){
            effectiveProtection = protection;
            uses--;
        }

        return effectiveProtection;
    }
    
    public String toString(){
        return "S[" + protection + ", " + uses + "]";
    }
    
    public boolean discard(){
        return Dice.discardElement(uses);
    }
}
