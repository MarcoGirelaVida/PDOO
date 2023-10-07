/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marco
 */
import static irrgarten.Directions.UP;
import java.util.ArrayList;
import java.util.Set;

public class Labyrinth {
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
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
        
        labyrinth[exitRow][exitCol] = 'E';
    }
    
    public void spreadPlayers(Player[] players){
        
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
            labyrinth[row][col] = 'M';
            monsters[row][col] = monster;
            monster.setPos(row, col);
        }
    }
    
    public Monster putPlayer(Directions direction, Player player){
        
    }
    
    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        
    }
    
    public Directions[] validMoves(int row, int col){
        
    }
    
    private boolean posOK(int row, int col){
        return (row < nRows) && ( col < nCols);
    }
    
    private boolean emptyPos(int row, int col){
        return labyrinth[row][col] == '-';
    }
    
    private boolean monsterPos(int row, int col){
        return labyrinth[row][col] == 'M';
    }
    
    private boolean exitPos(int row, int col){
        return labyrinth[row][col] == 'E';
    }
    
    private boolean combatPos(int row, int col){
        return labyrinth[row][col] == 'C';
    }
    
    private boolean canStepOn(int row, int col){
        //No pongo && posOK porque ninguna de esas opciones dará true en una posición invalida)
        return this.monsterPos(row, col) || this.emptyPos(row, col) || this.monsterPos(row, col);
    }
    
    private void updateOldPos(int row, int col){
        if(this.posOK(row, col)){
            if(this.combatPos(row, col)){
                labyrinth[row][col] = 'M';
            }
            else{
                labyrinth[row][col] = '-';
            }
        }
    }
    
    private int[] dir2Pos(int row, int col, Directions direction){
        int[] nextPosition = new int[2];
        
        switch (direction) {
            case UP:
                nextPosition[0] = row+1;
                nextPosition[1] = col;
                break;
            case DOWN:
                nextPosition[0] = row-1;
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
        int [] randomPos = new int[2];
        
        do{
            randomPos[0] = Dice.randomPos(nRows-1);
            randomPos[1] = Dice.randomPos(nCols-1);
        while(!this.posOK(randomPos[0], randomPos[1]));
        
        return randomPos;
    }
    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        
    }
}
