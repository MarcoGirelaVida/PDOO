/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marco
 */
public class TestP1 {
    public static Dice dice = new Dice();
    
    public static int testWeapon(){
        System.out.println("TESTING WEAPON");
        
        // Test constructor without parameters
        Weapon voidWeapon = new Weapon();
        
        // Test constructor with parameters
        Weapon filledWeapon = new Weapon(dice.weaponPower() , dice.usesLeft());
        
        // Test to_String
        System.out.println("Weapons state after being created: ");
        System.out.println("Void weapon: " + voidWeapon.toString());
        System.out.println("Filled weapon: " + filledWeapon.toString() + "\n");
        
        // Test attack
        float attackPowerVoidWeapon = voidWeapon.attack();
        float attackPowerFilledWeapon = filledWeapon.attack();
        
        // State after attack
        System.out.println("State after attacking: ");
        System.out.println("Void weapon: " + voidWeapon.toString());
        System.out.println("Attack power of void weapon: " + attackPowerVoidWeapon);
        System.out.println("Filled weapon: " + filledWeapon.toString());
        System.out.println("Attack power of filled weapon: " + attackPowerFilledWeapon + "\n");

                
        // Discard method testing
        Weapon maxWeapon = new Weapon(2, 5);
        Weapon minWeapon = voidWeapon;
        Weapon averageWeapon = new Weapon(1, 3);
        System.out.println("Result discarting maxWeapon: " + maxWeapon.discard());
        System.out.println("Result discarting minWeapon: " + minWeapon.discard());
        System.out.println("Result discarting averageWeapon: " + averageWeapon.discard());
        System.out.println("\n\n");
        
        return 0;
    }
    
    public static int testShield(){
        System.out.println("TESTING SHIELD");
        
        // Test constructor without parameters
        Shield voidShield = new Shield();
        
        // Test constructor with parameters
        Shield filledShield = new Shield(dice.shieldPower() , dice.usesLeft());
        
        // Test to_String
        System.out.println("Shields state after being created: ");
        System.out.println("Void Shield: " + voidShield.toString());
        System.out.println("Filled Shield: " + filledShield.toString() + "\n");
        
        // Test Protect
        float ProtectPowerVoidShield = voidShield.protect();
        float ProtectPowerFilledShield = filledShield.protect();
        
        // State after Protect
        System.out.println("State after Protecting: ");
        System.out.println("Void Shield: " + voidShield.toString());
        System.out.println("Protect power of void Shield: " + ProtectPowerVoidShield);
        System.out.println("Filled Shield: " + filledShield.toString());
        System.out.println("Protect power of filled Shield: " + ProtectPowerFilledShield + "\n");
        
        // Discard method testing
        Shield maxShield = new Shield(2, 5);
        Shield minShield = voidShield;
        Shield averageShield = new Shield(1, 3);
        System.out.println("Result discarting maxShield: " + maxShield.discard());
        System.out.println("Result discarting minShield: " + minShield.discard());
        System.out.println("Result discarting averageShield: " + averageShield.discard());
        System.out.println("\n\n");
        
        return 0;
    }
    
    
    public static int testGameState(){
        System.out.println("TESTING GAMESTATE");
        
        GameState gs = new GameState("Labyrinth state", "Players",
                                    "Monsters", 0, true,
                                    "Log");
        System.out.println("Laberynth state: " + gs.getLabyrinthv());
        System.out.println("Players: " + gs.getPlayers());
        System.out.println("Monsters: " + gs.getMonsters());
        System.out.println("Current Player: " + gs.getCurrentPlayer());
        System.out.println("Winner: " + gs.isWinner());
        System.out.println("Log: " + gs.getLog());
        System.out.println("\n\n");
        
        return 0;
    }
    
    public static int testEnums(){
        System.out.println("TESTING ENUMS");
        
        GameCharacter gc = GameCharacter.PLAYER;
        Directions dir = Directions.UP;
        Orientation ori = Orientation.HORIZONTAL;
        
        System.out.println("Game Character: " + gc);
        System.out.println("Directions: " + dir);
        System.out.println("Orientation: " + ori);
        System.out.println("\n\n");
        
        return 0;
    }
    
    public static int testDice(){
        System.out.println("TESTING DICE");

        float averageProbUses = 0;
        float averageProbIntelligence = 0;
        float averageProbStrength = 0;
        float averageProbResurrect = 0;
        float averageProbWeaponsReward = 0;
        float averageProbShieldsReward = 0;
        float averageProbHealthReward = 0;
        float averageProbAttack = 0;
        float averageProbProtect = 0;
        float averageProbIntensity = 0;
    
        int n = 10000;
        
        for(int i=0; i<n; i++){
            averageProbUses += dice.usesLeft();
            averageProbIntelligence += dice.randomIntelligence();
            averageProbStrength += dice.randomStrength();
            averageProbResurrect += (dice.resurrectPlayer() == true) ? 1 : 0;
            averageProbWeaponsReward += dice.weaponsReward();
            averageProbShieldsReward += dice.shieldsReward();
            averageProbHealthReward += dice.healthReward();
            averageProbAttack += dice.weaponPower();
            averageProbProtect += dice.shieldPower();
            averageProbIntensity += dice.intensity(10);
        }
        
        averageProbUses /= n*5;
        averageProbIntelligence /= n*10.0f;
        averageProbStrength /= n*10.0f;
        averageProbResurrect /= n*1;
        averageProbWeaponsReward /= n*2;
        averageProbShieldsReward /= n*3;
        averageProbHealthReward /= n*5;
        averageProbAttack /= n*3;
        averageProbProtect /= n*2;
        averageProbIntensity /= n*10;
        
        System.out.println( "Average Prob Uses :" + averageProbUses*100 + "%");
        System.out.println( "Average Prob Intelligence :" + averageProbIntelligence*100 + "%");
        System.out.println( "Average Prob Strength :" + averageProbStrength*100 + "%");
        System.out.println( "Average Prob Resurrect :" + averageProbResurrect*100 + "%");
        System.out.println( "Average Prob Weapons Reward :" + averageProbWeaponsReward*100 + "%");
        System.out.println( "Average Prob Shields Reward :" + averageProbShieldsReward*100 + "%");
        System.out.println( "Average Prob HealthReward :" + averageProbHealthReward*100 + "%");
        System.out.println( "Average Prob Attack :" + averageProbAttack*100 + "%");
        System.out.println( "Average Prob Protect :" + averageProbProtect*100 + "%");
        System.out.println( "Average Prob Intensity :" + averageProbIntensity*100 + "%");
        System.out.println("\n\n");
        
        return 0;
    }
    
    public static void main(String[] args){
        
        int status = testWeapon() + testShield() + testGameState() + 
                    testEnums() + testDice();
        
        System.exit(status);
    }
}
