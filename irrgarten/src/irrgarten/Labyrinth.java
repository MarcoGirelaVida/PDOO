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

public class Labyrinth {
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;
    private final int nRows;
    private final int nCols;
    private final int exitRow;
    private final int exitCol;
    
    private static Monster[][] monsters;
    private static Player[][] players;
    private static char[][] labyrinth;
    
    /*
    private static LabyrinthSquare[][] labyrinth;
    private static PlayerSquare[][] players;
    private static MonsterSquare[][] monsters;
    */
    
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        monsters = new Monster[nRows][nCols];
        players = new Player[nRows][nCols];
        labyrinth = new char[nRows][nCols];
        
        for(int i=0; i < nRows; i++){
            for(int j=0; j < nCols; j++){
                labyrinth[i][j] = EMPTY_CHAR;
            }
        }
        
        labyrinth[exitRow][exitCol] = EXIT_CHAR;
    }
    
    public void spreadPlayers(ArrayList<Player> players){
        for (Player p : players) {
            int[] pos = this.randomEmptyPos();
            this.putPlayer2D(-1, -1, pos[0], pos[1], p);
        }
    }
    
    public boolean haveAWinner(){
        return players[exitRow][exitCol] != null;
    }
    
    public String toString(){
        
        String completeMatrixString = "";
        
        for (int i = 0; i < nRows; i++){
            completeMatrixString += "| ";
            for (int j = 0; j < nCols; j++){
                completeMatrixString += labyrinth[i][j] + " | ";
            }
            completeMatrixString += "\n";
        }
        
        return completeMatrixString;
    }
    
    public void addMonster(int row, int col, Monster monster){
        if(this.posOK(row, col) && this.emptyPos(row, col)){
            labyrinth[row][col] = MONSTER_CHAR;
            monsters[row][col] = monster;
            monster.setPos(row, col);
        }
    }
    
    public Monster putPlayer(Directions direction, Player player){
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        
        return putPlayer2D(oldRow, oldCol, newPos[0], newPos[1], player);
    }
    
    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        int incRow = 0;
        int incCol = 1;
   
        if (orientation == Orientation.VERTICAL){
            incRow = 1;
            incCol = 0;
        }
        
        int row = startRow;
        int col = startCol;
        
        while (this.posOK(row, col) && (this.emptyPos(row, col) && (length > 0))){
            labyrinth[row][col] = BLOCK_CHAR;
            length--;
            row += incRow;
            col += incCol;
        }
    }
    
    public ArrayList<Directions> validMoves(int row, int col){
        ArrayList<Directions> output = new ArrayList<>();
        
        if (this.canStepOn(row+1, col)){
            output.add(Directions.DOWN);
        }
        if (this.canStepOn(row-1, col)){
            output.add(Directions.UP);
        }
        if (this.canStepOn(row, col+1)){
            output.add(Directions.RIGHT);
        }
        if (this.canStepOn(row, col-1)){
            output.add(Directions.LEFT);
        }
        
        return output;
    }
    
    private boolean posOK(int row, int col){
        return (row < nRows) && ( col < nCols);
    }
    
    private boolean emptyPos(int row, int col){
        return labyrinth[row][col] == EMPTY_CHAR;
    }
    
    private boolean monsterPos(int row, int col){
        return labyrinth[row][col] == MONSTER_CHAR;
    }
    
    private boolean exitPos(int row, int col){
        return labyrinth[row][col] == EXIT_CHAR;
    }
    
    private boolean combatPos(int row, int col){
        return labyrinth[row][col] == COMBAT_CHAR;
    }
    
    private boolean canStepOn(int row, int col){
        //No pongo && posOK porque ninguna de esas opciones dará true en una posición invalida)
        return this.monsterPos(row, col) || this.emptyPos(row, col) || this.monsterPos(row, col);
    }
    
    private void updateOldPos(int row, int col){
        if(this.posOK(row, col)){
            if(this.combatPos(row, col)){
                labyrinth[row][col] = MONSTER_CHAR;
            }
            else{
                labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }
    
    private int[] dir2Pos(int row, int col, Directions direction){
        int[] nextPosition = new int[2];
        
        switch (direction) {
            case UP:
                nextPosition[0] = row-1;
                nextPosition[1] = col;
                break;
            case DOWN:
                nextPosition[0] = row+1;
                nextPosition[1] = col;
                break;
            case LEFT:
                nextPosition[0] = row;
                nextPosition[1] = col-1;
                break;
            case RIGHT:
                nextPosition[0] = row;
                nextPosition[1] = col+1;
                break;
        }
        
        return nextPosition;
    }
    

    private int[] randomEmptyPos(){
        int[] randomPos = new int[2];

        do {
            randomPos[0] = Dice.randomPos(nRows-1);
            randomPos[1] = Dice.randomPos(nCols-1);
        } while (!this.posOK(randomPos[0], randomPos[1]));

        return randomPos;
    }

    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        
    }
}
