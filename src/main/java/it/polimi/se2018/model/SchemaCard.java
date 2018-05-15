package it.polimi.se2018.model;

import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;

/**
 * Schema Cards class
 * @author Giorgia
 * edited Giovanni
 */

public class SchemaCard {

    private String name1;
    private String name2;
    private Cell[][] cells = new Cell[4][5];
    private int difficultyLevel;

    /**
     * constructor method initializing schemacard using n to choose one of the 24 different schemacard
     * @param n
     */
    public SchemaCard(int n) {          //costruttore con switch per creare le carte schema pescate dal giocatore

        switch (n) {

            case 1:         //Virtus
                this.name1 = "Virtus";
                this.name2 = "Kaleidoscopic Dream";
                this.cells[0][0] = new Cell(null, 4);
                this.cells[0][1] = new Cell(null, 0);
                this.cells[0][2] = new Cell(null, 2);
                this.cells[0][3] = new Cell(null, 5);
                this.cells[0][4] = new Cell(Color.GREEN, 0);
                this.cells[1][0] = new Cell(null, 0);
                this.cells[1][1] = new Cell(null, 0);
                this.cells[1][2] = new Cell(null, 6);
                this.cells[1][3] = new Cell(Color.GREEN, 0);
                this.cells[1][4] = new Cell(null, 2);
                this.cells[2][0] = new Cell(null, 0);
                this.cells[2][1] = new Cell(null, 3);
                this.cells[2][2] = new Cell(Color.GREEN, 0);
                this.cells[2][3] = new Cell(null, 4);
                this.cells[2][4] = new Cell(null, 0);
                this.cells[3][0] = new Cell(null, 5);
                this.cells[3][1] = new Cell(Color.GREEN, 0);
                this.cells[3][2] = new Cell(null, 1);
                this.cells[3][3] = new Cell(null, 0);
                this.cells[3][4] = new Cell(null, 0);
                this.difficultyLevel = 5;
                break;

            case 2:
                this.name1 = "Via Lux";
                this.name2 = "Aurorae Magnificus";
                this.cells[0][0] = new Cell(Color.YELLOW, 0);
                this.cells[1][1] = new Cell(null, 0);
                this.cells[2][2] = new Cell(null, 6);
                this.cells[3][3] = new Cell(null, 0);
                this.cells[0][4] = new Cell(null, 0);
                this.cells[1][0] = new Cell(null, 0);
                this.cells[2][1] = new Cell(null, 1);
                this.cells[3][2] = new Cell(null, 5);
                this.cells[0][3] = new Cell(null, 0);
                this.cells[1][4] = new Cell(null, 2);
                this.cells[2][0] = new Cell(null, 3);
                this.cells[3][1] = new Cell(Color.YELLOW, 0);
                this.cells[0][2] = new Cell(Color.RED, 0);
                this.cells[1][3] = new Cell(Color.PURPLE, 0);
                this.cells[2][4] = new Cell(null, 0);
                this.cells[3][0] = new Cell(null, 0);
                this.cells[0][1] = new Cell(null, 0);
                this.cells[1][2] = new Cell(null, 4);
                this.cells[2][3] = new Cell(null, 3);
                this.cells[3][4] = new Cell(Color.RED, 0);
                this.difficultyLevel = 4;
                break;

            case 3:
                this.name1 = "Bellesguard";
                this.name2 = "Sun Catcher";
                this.cells[0][0] = new Cell(Color.BLUE, 0);
                this.cells[1][1] = new Cell(null, 6);
                this.cells[2][2] = new Cell(null, 0);
                this.cells[3][3] = new Cell(null, 0);
                this.cells[0][4] = new Cell(Color.YELLOW, 0);
                this.cells[1][0] = new Cell(null, 0);
                this.cells[2][1] = new Cell(null, 3);
                this.cells[3][2] = new Cell(Color.BLUE, 0);
                this.cells[0][3] = new Cell(null, 0);
                this.cells[1][4] = new Cell(null, 0);
                this.cells[2][0] = new Cell(null, 0);
                this.cells[3][1] = new Cell(null, 5);
                this.cells[0][2] = new Cell(null, 6);
                this.cells[1][3] = new Cell(null, 2);
                this.cells[2][4] = new Cell(null, 0);
                this.cells[3][0] = new Cell(null, 0);
                this.cells[0][1] = new Cell(null, 4);
                this.cells[1][2] = new Cell(null, 0);
                this.cells[2][3] = new Cell(null, 1);
                this.cells[3][4] = new Cell(Color.GREEN, 0);
                this.difficultyLevel = 3;
                break;

            case 4:
                this.name1 = "Firmitas";
                this.name2 = "Symphony of Light";
                this.cells[0][0] = new Cell(Color.PURPLE, 0);
                this.cells[1][1] = new Cell(null, 6);
                this.cells[2][2] = new Cell(null, 0);
                this.cells[3][3] = new Cell(null, 0);
                this.cells[0][4] = new Cell(null, 3);
                this.cells[1][0] = new Cell(null, 5);
                this.cells[2][1] = new Cell(Color.PURPLE, 0);
                this.cells[3][2] = new Cell(null, 3);
                this.cells[0][3] = new Cell(null, 0);
                this.cells[1][4] = new Cell(null, 0);
                this.cells[2][0] = new Cell(null, 0);
                this.cells[3][1] = new Cell(null, 2);
                this.cells[0][2] = new Cell(Color.PURPLE, 0);
                this.cells[1][3] = new Cell(null, 1);
                this.cells[2][4] = new Cell(null, 0);
                this.cells[3][0] = new Cell(null, 0);
                this.cells[0][1] = new Cell(null, 1);
                this.cells[1][2] = new Cell(null, 5);
                this.cells[2][3] = new Cell(Color.PURPLE, 0);
                this.cells[3][4] = new Cell(null, 4);
                this.difficultyLevel = 5;
                break;

            case 5:
                this.name1 = "Aurora Sagradis";
                this.name2 = "Industria";
                this.cells[0][0] = new Cell(Color.RED, 0);
                this.cells[1][1] = new Cell(null, 0);
                this.cells[2][2] = new Cell(Color.BLUE, 0);
                this.cells[3][3] = new Cell(null, 0);
                this.cells[0][4] = new Cell(Color.YELLOW, 0);
                this.cells[1][0] = new Cell(null, 4);
                this.cells[2][1] = new Cell(Color.PURPLE, 0);
                this.cells[3][2] = new Cell(null, 3);
                this.cells[0][3] = new Cell(Color.GREEN, 0);
                this.cells[1][4] = new Cell(null, 2);
                this.cells[2][0] = new Cell(null, 0);
                this.cells[3][1] = new Cell(null, 1);
                this.cells[0][2] = new Cell(null, 0);
                this.cells[1][3] = new Cell(null, 5);
                this.cells[2][4] = new Cell(null, 0);
                this.cells[3][0] = new Cell(null, 0);
                this.cells[0][1] = new Cell(null, 0);
                this.cells[1][2] = new Cell(null, 6);
                this.cells[2][3] = new Cell(null, 0);
                this.cells[3][4] = new Cell(null, 0);
                this.difficultyLevel = 4;
                break;

                //[...] resto delle carte*/

        }

        }

    /**
     *
     * @return name1 first name of schemacard
     */
    public String getName1 () {
            return name1;
        }

    /**
     *
     * @return name2 second name of schemacard
     */

    public String getName2 () {
            return name2;
        }

    /**
     *
     * @param row number of row
     * @param col nomber of col
     * @return cells return reference to cell
     */
    public Cell getCell ( int row, int col){
            return cells[row][col];
        }

    /**
     *
     * @return fiffivulty level
     */
    public int getDifficultyLevel () {
            return difficultyLevel;
        }

    /**
     *
     * @return reference to cells
     */
        public Cell[][] getCells () {
            return cells;
        }

    /**
     *
     * @return true if schemacard is empty or false if not
     */
    public boolean isEmpty () {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j].isFull()) return false;
                }
            }
            return true;
    }
    
    public void placeDie(Dice die, int row, int col) throws RestrictionsNotRespectedException, FullCellException{
        if (!hasADieNearOrEmptySchema(col, row)) throw (new RestrictionsNotRespectedException("Il dado deve essere piazzato lungo il bordo o deve avere un altro dado vicino"));
        this.getCell(row, col).setAssignedDice(die);
    }

    /**
     * method to check if there's a full cell that has at least one corner
     * in common with the cell of the considered die
     *
     * @return true if there's die in any die in the cells near the one
     * of the considered die
     */
    private boolean hasADieNearOrEmptySchema(int row, int col) {
        if(this.isEmpty() && ((row==0||row==3) || (col==0||col==4))){
            return true;
        }
        if (col == 0) {
            if (row == 0) {
                /*checks upper left corner*/
                return checkUpperLeftCorner(col, row);
            }
            if (row == 3) {
                /*checks down left corner*/
                return checkDownLeftCorner(col, row);
            }
            /*check left column*/
            return checkLeftColumn(col, row);
        }
        if (col == 4) {
            if (row == 0) {
                /*check upper right corner*/
                return checkUpperRightCorner(col, row);
            }
            if (row == 3) {
                /*check down right corner*/
                return checkDownRightCorner(col, row);
            }
            /*checks right column*/
            return checkRightColumn(col, row);
        }
        if (row == 0) {
            /*check upper row*/
            return checkUpperRow(col, row);
        }
        if (row == 3) {
            /*checks down row*/
            return checkDownRow(col, row);
        }
        /*checks middle cells*/
        return checkMiddelCells(col, row);
    }

    /**
     * Method to check if a die on the left column has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkUpperLeftCorner(int row, int col){
        if (this.getCell(row, col - 1).isFull()) {
            return true;
        }
        if (this.getCell(row + 1, col - 1).isFull()) {
            return true;
        }

        if (this.getCell(row + 1, col).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if a die on the left column has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkDownLeftCorner(int row, int col){
        if (this.getCell(row - 1, col).isFull()) {
            return true;
        }
        if (this.getCell(row - 1, col + 1).isFull()) {
            return true;
        }
        if (this.getCell(row, col + 1).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if a die on the left column has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkLeftColumn(int row, int col){
        for (int i = 0; i < 3; i++) {
            if (this.getCell(row - 1 + i, col + 1).isFull()) {
                return true;
            }
        }
        if (this.getCell(row - 1, col).isFull()) {
            return true;
        }
        if (this.getCell(row + 1, col).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if a die on the upper right corner has any cell filled with a die
     * with at least a corner in common with itself
     * @param col
     * @param row
     * @return
     */
    private boolean checkUpperRightCorner(int row, int col){
        if (this.getCell(row, col - 1).isFull()) {
            return true;
        }
        if (this.getCell(row + 1, col - 1).isFull()) {
            return true;
        }

        if (this.getCell(row + 1, col).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if a die on the down right corner has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkDownRightCorner(int row, int col){
        if (this.getCell(row - 1, col).isFull()) {
            return true;
        }
        if (this.getCell(row - 1, col - 1).isFull()) {
            return true;
        }

        if (this.getCell(row, col - 1).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if a die on the right column has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkRightColumn(int row, int col){
        for (int i = 0; i < 3; i++) {
            if (this.getCell(row - 1 + i, col - 1).isFull()) {
                return true;
            }
        }
        if (this.getCell(row - 1, col).isFull()) {
            return true;
        }
        if (this.getCell(row + 1, col).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if a die on the upper row has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkUpperRow(int row, int col){
        for (int i = 0; i < 3; i++) {
            if (this.getCell(row + 1, col - 1 + i).isFull()) {
                return true;
            }
        }
        if (this.getCell(row, col - 1).isFull()) {
            return true;
        }
        if (this.getCell(row, col + 1).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if a die on the down row has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkDownRow(int row, int col){
        for (int i = 0; i < 3; i++) {
            if (this.getCell(row - 1, col - 1 + i).isFull()) {
                return true;
            }
        }
        if (this.getCell(row, col - 1).isFull()) {
            return true;
        }
        if (this.getCell(row, col + 1).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if a die on the middel cells has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkMiddelCells(int row, int col){
        for(int i=0; i<3; i++){
            if (this.getCell(row-1, col-1+i).isFull()) {
                return true;
            }
            if (this.getCell(row+1, col-1+i).isFull()) {
                return true;
            }
        }
        if (this.getCell(row-1, col).isFull()) {
            return true;
        }
        if (this.getCell(row+1, col).isFull()) {
            return true;
        }
        return false;
    }
}

