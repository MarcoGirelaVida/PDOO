
package irrgarten.UI;

import irrgarten.Directions;
import irrgarten.GameState;
import java.util.Scanner;


public class TextUI {
    
    private static Scanner in = new Scanner(System.in);
    
    private char readChar() {
        String s = in.nextLine();     
        return s.charAt(0);
    }
    

    public Directions nextMove() {
        System.out.print("Where? ");
        
        Directions direction = Directions.DOWN;
        boolean gotInput = false;
        
        while (!gotInput) {
            char c = readChar();
            switch(c) {
                case 'w':
                    System.out.print(" UP\n");
                    direction = Directions.UP;
                    gotInput = true;
                    break;
                case 's':
                    System.out.print(" DOWN\n");
                    direction = Directions.DOWN;
                    gotInput = true;
                    break;
                case 'd':
                    System.out.print("RIGHT\n");
                    direction = Directions.RIGHT;
                    gotInput = true;
                    break;
                case 'a':
                    System.out.print(" LEFT\n");
                    direction = Directions.LEFT;
                    gotInput = true;    
                    break;
            }
        }    
        return direction;
    }
    
    public void showGame(GameState gameState) {
        System.out.println("\t\t CURRENT GAMESTATE \n");
        System.out.println("\t\tMONSTERS\n" + gameState.getMonsters() + "\n");
        System.out.println("\t\tLABYRINTHV\n" + gameState.getLabyrinthv() + "\n");
        System.out.println("\t\tPLAYERS\n" + gameState.getPlayers());
        System.out.println("CURRENT PLAYER: " + (char) gameState.getCurrentPlayer());
        System.out.println("IS THERE A WINNER?: " + gameState.isWinner() + "\n");
        System.out.println("\t\tLOG\n" + gameState.getLog() + "\n");
    }
    
}
