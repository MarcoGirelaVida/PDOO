/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;
import java.util.Random;
/**
 *
 * @author marco
 */
public class Dice {
    private static final int MAX_USES = 5;
    private static final float MAX_INTELLIGENCE = 10.0f;
    private static final float MAX_STRENGTH = 10.0f;
    private static final float RESURRECT_PROB = 0.3f;
    private static final int WEAPONS_REWARD = 2;
    private static final int SHIELDS_REWARD = 3;
    private static final int HEALTH_REWARD = 5;
    private static final int MAX_ATTACK = 3;
    private static final int MAX_SHIELD = 2;
    private static final Random generator = new Random();
    
    public static int randomPos(int max){
        return generator.nextInt(max+1);
    }
    
    public static int whoStarts(int nplayers){
        return generator.nextInt(nplayers+1);
    }
    
    public static float randomIntelligence(){
        return MAX_INTELLIGENCE * generator.nextFloat();
    }
    
    public static float randomStrength(){
        return MAX_STRENGTH * generator.nextFloat();
    }
    
    public static boolean resurrectPlayer(){
        return 0 <= (RESURRECT_PROB - generator.nextFloat());
    }
    
    public static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD+1);
    }
    
    public static int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD+1);
    }
    
    public static int healthReward(){
        return generator.nextInt(HEALTH_REWARD);
    }
    
    public static float weaponPower(){
        return MAX_ATTACK * generator.nextFloat();
    }
    
    public static float shieldPower(){
        return MAX_SHIELD * generator.nextFloat();
    }
    
    public static int usesLeft(){
        return generator.nextInt(MAX_USES+1);
    }
    
    public static float intensity(float competence){
        return competence * generator.nextFloat();
    }
    
    public static boolean discardElement(int usesLeft){
        // No se ha puesto MAX_USES+1 intencionalmente, para garantizar que si
        // uses_Left==MAX_USES la probabilidad sea del 100%
        // En caso ningún caso 0 será mayor que un número entre [0, MAX_USES],
        // como mucho será igual (a cero), por lo que la probabilidad de que
        // usesLeft=0 devuelva true es 0%.
        boolean resultado = false;
        if( generator.nextInt(MAX_USES) < usesLeft){
            resultado = true;
        }
        
        return resultado;
    }
}
