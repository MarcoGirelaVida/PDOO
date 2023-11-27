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
    
    private Monster[][] monsters;
    private Player[][] players;
    private char[][] labyrinth;
    
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
    
    // Reparte los jugadores dados en el vector "players" en posiciones alaetorias
    // del laberinto
    public void spreadPlayers(ArrayList<Player> players){
        for (Player p : players) {
            int[] pos = this.randomEmptyPos();
            this.putPlayer2D(-1, -1, pos[ROW], pos[COL], p);
        }
    }
    
    // Devuelve true si hay un jugador en la casilla de salida
    public boolean haveAWinner(){
        return players[exitRow][exitCol] != null;
    }
    
    // Muestra una representación en cadena de caracteres del estado del laberinto
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
    
    // 1. Si la posición suministrada está dentro del tablero y
    // está vacía, anota en el laberinto la presencia de un monstruo
    // 2. guarda la referencia del monstruo en el atributo contenedor 
    // 3. e indica al monstruo cual es su posición actual (setPos)
    // 4. Si se ha podido colocar devuelve true (modificación propia)
    public boolean addMonster(int row, int col, Monster monster){
        boolean successful = true;
        if(this.posOK(row, col) && this.emptyPos(row, col)){
            labyrinth[row][col] = MONSTER_CHAR;
            monsters[row][col] = monster;
            monster.setPos(row, col);
        }
        else{
            successful = false;
        }
        
        return successful;
    }
    
    // Actualiza la posición de un jugador
    public Monster putPlayer(Directions direction, Player player){
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        
        return putPlayer2D(oldRow, oldCol, newPos[0], newPos[1], player);
    }
    
    // Añade bloques en la orientación deseada
    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        // Suponemos que la orientación es horizontal por default
        int incRow = 0;
        int incCol = 1;
        
        // Si no lo es la cambiamos
        if (orientation == Orientation.VERTICAL){
            incRow = 1;
            incCol = 0;
        }
        
        int row = startRow;
        int col = startCol;
        
        
        // Generamos tantos bloques como se hayan solicitado en la dirección deseada
        // Siempre que la posición sea válida
        while (this.posOK(row, col) && (this.emptyPos(row, col) && (length > 0))){
            labyrinth[row][col] = BLOCK_CHAR;
            length--;
            row += incRow;
            col += incCol;
        }
    }
    
    // Devuelve una array con todas los posibles movimientos que se pueden dar 
    // dada una posición
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
    
    // Devuelve true si la posición proporcionada está dentro del laberinto
    private boolean posOK(int row, int col){
        return (row < nRows) && ( col < nCols) && (row >= 0) && (col >= 0);
    }
    
    // Devuelve true si la posición suministrada está vacía.
    private boolean emptyPos(int row, int col){
        return labyrinth[row][col] == EMPTY_CHAR;
    }
    
    // Devuelve true si la posición suministrada alberga exclusivamente un monstruo.
    private boolean monsterPos(int row, int col){
        return labyrinth[row][col] == MONSTER_CHAR;
    }
    
    // Devuelve true si la posición suministrada es la de salida.
    private boolean exitPos(int row, int col){
        return labyrinth[row][col] == EXIT_CHAR;
    }
    
    // Devuelve true si la posición suministrada contiene un combate
    private boolean combatPos(int row, int col){
        return labyrinth[row][col] == COMBAT_CHAR;
    }
    
    // True si:
    // 1. Esta dentro del laberinto
    // 2. Es o una casilla vacía, o un monstruo o la salida (= es cualquier cosa
    // menos un bloque o combate)
    private boolean canStepOn(int row, int col){
        //posOk realmente es redundante, pero lo pongo por si acaso
        return (this.monsterPos(row, col) || this.emptyPos(row, col) || this.exitPos(row, col)) && this.posOK(row,col);
    }
    
    // 1. Este método solo realiza su función si esta dentro del laberínto
    // 2. Si la posición antigua era de combate ahora será solo montruo
    // 3. En cualquier otro caso pasa a ser casilla vacía
    // PRE: Este método se llama solo cuando un jugador abandona una casilla
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
    
    // Este método calcula cuál sería la dirección a la que se llegaría si se
    // avanzase desde la posición en la orientación deseada
    // Nota: No se harán comprobaciones aquí, eso se hace en otros lados
    private int[] dir2Pos(int row, int col, Directions direction){
        int[] nextPosition = new int[2];
        
        switch (direction) {
            case UP:
                nextPosition[ROW] = row-1;
                nextPosition[COL] = col;
                break;
            case DOWN:
                nextPosition[ROW] = row+1;
                nextPosition[COL] = col;
                break;
            case LEFT:
                nextPosition[ROW] = row;
                nextPosition[COL] = col-1;
                break;
            case RIGHT:
                nextPosition[ROW] = row;
                nextPosition[COL] = col+1;
                break;
        }
        
        return nextPosition;
    }
    
    // Devuelve una posición aleatoria vacía y válida del laberinto
    private int[] randomEmptyPos(){
        int[] randomPos = new int[2];

        do {
            randomPos[ROW] = Dice.randomPos(nRows-1);
            randomPos[COL] = Dice.randomPos(nCols-1);
        } while (!this.posOK(randomPos[ROW], randomPos[COL]) || !this.emptyPos(randomPos[ROW], randomPos[COL]) );

        return randomPos;
    }

    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output = null;
        
        if (canStepOn(row, col)) {
            if (posOK(oldRow, oldCol)) {
                Player p = players[oldRow][oldCol];
                
                if (p == player) {
                    this.updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol] = null;
                }
            }
            
            if (this.monsterPos(row, col)) {
                labyrinth[row][col] = COMBAT_CHAR;
                output = monsters[row][col];
            }
            else{
                labyrinth[row][col] = (char) player.getNumber();
            }
            
            players[row][col] = player;
            player.setPos(row, col);
        }
        
        return output;
    }
}
