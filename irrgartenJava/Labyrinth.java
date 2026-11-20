/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ch4k0
 */
public class Labyrinth {
    private static final char BLOCKCHAR = 'X';
    private static final char EMPTYCHAR = '-';
    private static final char MONSTERCHAR = 'M';
    private static final char COMBATCHAR = 'C';
    private static final char EXITCHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    private Player[][] players;
    private Monster[][] monsters;
    private char[][] labyrinth;
    
    Dice dice1 = new Dice();
    
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;

        players = new Player[nRows][nCols];
        monsters = new Monster[nRows][nCols];
        labyrinth = new char[nRows][nCols];

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {  
                if(i == 0 || j == 0 || i == nRows-1 || j == nCols-1){
                    labyrinth[i][j] = BLOCKCHAR;
                }
                else{
                    labyrinth[i][j] = EMPTYCHAR;
                }
            }
        }

        labyrinth[exitRow][exitCol] = EXITCHAR;
    }
    
    public void spreadPlayers(ArrayList<Player> players){
        int n_players = players.size();
        Player p;
        int[] pos;
        Monster monster;
        
        for(int i=0; i<n_players; i++){
            p = players.get(i);
            pos = randomEmptyPos();
            monster = putPlayer2D(-1, -1, pos[ROW], pos[COL], p);
        }
    }
    
    public boolean haveAWinner(){
        return players[exitRow][exitCol] != null;
    }
    
    public String toString() {
        String resultado = "";

        resultado += "\tEstado del Laberinto " + nRows + "x" + nCols + "\n\n";

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                resultado += labyrinth[i][j] + " ";
            }
            resultado += "\n";
        }

        return resultado;
    }
    
    public void addMonster(int row, int col, Monster monster){
        if(posOK(row, col) && emptyPos(row, col)){
            labyrinth[row][col] = 'M';
            monster.setPos(row,col);
            monsters[row][col] = monster;
        }
    }
    
    public Monster putPlayer(Directions direction, Player player){
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        
        Monster monster = putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
                
        return monster;
    }
    
    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        int incRow, incCol;
        
        if(orientation == Orientation.VERTICAL){
            incRow = 1;
            incCol = 0;
        }
        else{
            incRow = 0;
            incCol = 1;
        }
       
        int row = startRow;
        int col = startCol;
        
        while( (posOK(row,col)) && (emptyPos(row,col)) && (length > 0) ){
            labyrinth[row][col] = BLOCKCHAR;
            length -= 1;
            row += incRow;
            col += incCol;
        }
    }
    
    public Directions[] validMoves(int row, int col){
        ArrayList<Directions> moves = new ArrayList<>();
        
        if(canStepOn(row+1,col)){
            moves.add(Directions.DOWN);
        }
        if(canStepOn(row-1,col)){
            moves.add(Directions.UP);
        }
        if(canStepOn(row,col+1)){
            moves.add(Directions.RIGHT);
        }
        if(canStepOn(row,col-1)){
            moves.add(Directions.LEFT);
        }
        
        Directions[] output = moves.toArray(new Directions[0]);
        
        return output;
    }
    
    private boolean posOK(int row, int col){
        return (row < nRows && row >= 0 && col < nCols && col >= 0);
    }
    
    private boolean emptyPos(int row, int col){
        return (labyrinth[row][col] == '-');
    }
    
    private boolean monsterPos(int row, int col){
        return (monsters[row][col] != null);
    }
    
    private boolean exitPos(int row, int col){
        return (row == this.exitRow && col == this.exitCol);
    }
    
    private boolean combatPos(int row, int col){
        return (labyrinth[row][col] == 'C');
    }
    
    private boolean canStepOn(int row, int col){
       return (posOK(row,col) && emptyPos(row,col) || monsterPos(row,col) || exitPos(row,col));
    }

    private void updateOldPos(int row, int col){
        if(posOK(row,col)){
            if(labyrinth[row][col] == 'C'){
                labyrinth[row][col] = 'M';
            }
            else{
                labyrinth[row][col] = '-';
            }
        }
    }

    private int[] dir2Pos(int row, int col, Directions direction) {
        int newRow = row;
        int newCol = col;

        switch(direction) {
            case UP:
                newRow = row - 1;
                break;
            case DOWN:
                newRow = row + 1;
                break;
            case LEFT:
                newCol = col - 1;
                break;
            case RIGHT:
                newCol = col + 1;
                break;
        }

        return new int[]{newRow, newCol};
    }

    private int[] randomEmptyPos(){
        
        int newRow;
        int newCol;
        
        do{
            newRow = dice1.randomPos(this.nRows);
            newCol = dice1.randomPos(this.nCols);
        }while(emptyPos(newRow, newCol) == false);
        
        return new int[]{newRow, newCol};
    }

    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output = null;
        
        if( canStepOn(row,col) ){
            if( posOK(oldRow, oldCol) ){
                Player p = players[oldRow][oldCol];
                if(p == player){
                    updateOldPos(oldRow,oldCol);
                    players[oldRow][oldCol] = null;
                }
            }
            boolean monsterPos = monsterPos(row,col);
            if(monsterPos){
                labyrinth[row][col] = COMBATCHAR;
                output = monsters[row][col];
            }
            else{
                char number = player.getNumber();
                labyrinth[row][col] = number;
            }
            
            players[row][col] = player;
            player.setPos(row,col);
        }
        return output;
    }
}
