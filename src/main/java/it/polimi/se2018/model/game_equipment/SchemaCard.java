package it.polimi.se2018.model.game_equipment;

import it.polimi.se2018.model.exceptions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Schema Cards class
 * @author Giorgia
 * edited Giovanni
 */

public class SchemaCard {

    private String name;
    private Cell[][] cells = new Cell[4][5];
    private int difficultyLevel;

    /**
     * constructor method initializing schemacard using n to choose one of the 24 different schemacard
     * @param schemaCardIndex
     */
    public SchemaCard(int schemaCardIndex) {
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream("src\\main\\java" +
                    "\\it\\polimi\\se2018\\SchemaCards.txt"));
            String line = "";
            boolean hasNextLine = true;
            boolean cardFound = false;
            boolean completedRows = false;
            boolean stopReading = false;
            int row=0;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine && !stopReading){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length && !completedRows){
                    if(words[i].trim().equals("Number:")){
                        if(schemaCardIndex == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                    }
                    if(cardFound){
                        if(words[i].trim().equals("name:")){
                            name = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equals("difficulty:")){
                            difficultyLevel = Integer.parseInt(words[i+1]);
                            i++;
                        }
                        if(words[i].startsWith("[")){
                            for(int col=0; col<words.length; col++){
                                switch(words[col].trim()){
                                    case "[]":{
                                        this.cells[row][col] = new Cell(null, 0);
                                        break;
                                    }
                                    case "[1]":{
                                        this.cells[row][col] = new Cell(null, 1);
                                        break;
                                    }
                                    case "[2]":{
                                        this.cells[row][col] = new Cell(null, 2);
                                        break;
                                    }
                                    case "[3]":{
                                        this.cells[row][col] = new Cell(null, 3);
                                        break;
                                    }
                                    case "[4]":{
                                        this.cells[row][col] = new Cell(null, 4);
                                        break;
                                    }
                                    case "[5]":{
                                        this.cells[row][col] = new Cell(null, 5);
                                        break;
                                    }
                                    case "[6]":{
                                        this.cells[row][col] = new Cell(null, 6);
                                        break;
                                    }
                                    case "[Y]":{
                                        this.cells[row][col] = new Cell(Color.YELLOW, 0);
                                        break;
                                    }
                                    case "[R]":{
                                        this.cells[row][col] = new Cell(Color.RED, 0);
                                        break;
                                    }
                                    case "[B]":{
                                        this.cells[row][col] = new Cell(Color.BLUE, 0);
                                        break;
                                    }
                                    case "[G]":{
                                        this.cells[row][col] = new Cell(Color.GREEN, 0);
                                        break;
                                    }
                                    case "[P]":{
                                        this.cells[row][col] = new Cell(Color.PURPLE, 0);
                                        break;
                                    }
                                }
                            }
                            row++;
                            if(row>3){
                                completedRows = true;
                                cardFound = false;
                                stopReading = true;
                            }
                        }
                    }
                    i=words.length;
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputFile.close();
        }
    }

    public SchemaCard(String schemaName){
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream("src\\main\\java\\it\\" +
                    "polimi\\se2018\\SchemaCards.txt"));
            String line = "";
            boolean hasNextLine = true;
            boolean cardFound = false;
            boolean completedRows = false;
            boolean stopReading = false;
            int row=0;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine && !stopReading){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length && !completedRows){
                    if(words[i].trim().equals("name:")){
                        if(schemaName.replace(' ', '/').equals(words[i+1])){
                            cardFound = true;
                            name = words[i+1].replace('/', ' ');
                            i++;
                        }
                    }
                    if(cardFound){
                        if(words[i].trim().equals("difficulty:")){
                            difficultyLevel = Integer.parseInt(words[i+1]);
                            i++;
                        }
                        if(words[i].startsWith("[")){
                            for(int col=0; col<words.length; col++){
                                switch(words[col].trim()){
                                    case "[]":{
                                        this.cells[row][col] = new Cell(null, 0);
                                        break;
                                    }
                                    case "[1]":{
                                        this.cells[row][col] = new Cell(null, 1);
                                        break;
                                    }
                                    case "[2]":{
                                        this.cells[row][col] = new Cell(null, 2);
                                        break;
                                    }
                                    case "[3]":{
                                        this.cells[row][col] = new Cell(null, 3);
                                        break;
                                    }
                                    case "[4]":{
                                        this.cells[row][col] = new Cell(null, 4);
                                        break;
                                    }
                                    case "[5]":{
                                        this.cells[row][col] = new Cell(null, 5);
                                        break;
                                    }
                                    case "[6]":{
                                        this.cells[row][col] = new Cell(null, 6);
                                        break;
                                    }
                                    case "[Y]":{
                                        this.cells[row][col] = new Cell(Color.YELLOW, 0);
                                        break;
                                    }
                                    case "[R]":{
                                        this.cells[row][col] = new Cell(Color.RED, 0);
                                        break;
                                    }
                                    case "[B]":{
                                        this.cells[row][col] = new Cell(Color.BLUE, 0);
                                        break;
                                    }
                                    case "[G]":{
                                        this.cells[row][col] = new Cell(Color.GREEN, 0);
                                        break;
                                    }
                                    case "[P]":{
                                        this.cells[row][col] = new Cell(Color.PURPLE, 0);
                                        break;
                                    }
                                }
                            }
                            row++;
                            if(row>3){
                                completedRows = true;
                                cardFound = false;
                                stopReading = true;
                            }
                        }
                    }
                    i=words.length;
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputFile.close();
        }
    }

    /**
     *
     * @return name1 first name of schemacard
     */
    public String getName () {
            return name;
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

    public boolean hasLessThanTwoDie(){
        int counter = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].isFull()) {
                    counter++;
                    if(counter>2){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void placeDie(Dice die, int row, int col, boolean avoidColorRestrictions, boolean avoidValueRestrictions, boolean avoidNearnessRestriction) throws RestrictionsNotRespectedException, FullCellException{
        boolean isPlacingFirstDie = (this.isEmpty() && ((row==0||row==3) || (col==0||col==4)));
        if (isPlacingFirstDie){
            this.getCell(row, col).setAssignedDice(die, avoidColorRestrictions, avoidValueRestrictions);
        }
        else{
            if(this.isEmpty()){
                throw (new RestrictionsNotRespectedException("Il dado deve essere piazzato lungo il bordo"));                
            }
            else{
                boolean hasADieNear = this.hasADieNear(row, col,die.getDiceColor(),die.getValue());
                if(hasADieNear || avoidNearnessRestriction){
                    this.getCell(row, col).setAssignedDice(die, avoidColorRestrictions, avoidValueRestrictions);
                }
                else{
                    throw (new RestrictionsNotRespectedException("Il dado deve avere un altro dado vicino!"));
                }
            }
        }
    }

    /**
     * method to check if there's a full cell that has at least one corner
     * in common with the cell of the considered die
     *
     * @return true if there's die in any die in the cells near the one
     * of the considered die
     */
    private boolean hasADieNear(int row, int col,Color color, int value) {
        if (col == 0) {
            if (row == 0) {
                /*checks upper left corner*/
                return checkUpperLeftCorner(row, col,color,value);
            }
            if (row == 3) {
                /*checks down left corner*/
                return checkDownLeftCorner(row, col,color,value);
            }
            /*check left column*/
            return checkLeftColumn(row, col,color,value);
        }
        if (col == 4) {
            if (row == 0) {
                /*check upper right corner*/
                return checkUpperRightCorner(row, col,color,value);
            }
            if (row == 3) {
                /*check down right corner*/
                return checkDownRightCorner(row, col,color,value);
            }
            /*checks right column*/
            return checkRightColumn(row, col,color,value);
        }
        if (row == 0) {
            /*check upper row*/
            return checkUpperRow(row, col,color,value);
        }
        if (row == 3) {
            /*checks down row*/
            return checkDownRow(row, col,color,value);
        }
        /*checks middle cells*/
        return checkMiddelCells(row, col, color, value);
    }

    /**
     * Method to check if a die on the left column has any cell filled with a die
     * with at least a corner in common
     * @param col
     * @param row
     * @return
     */
    private boolean checkUpperLeftCorner(int row, int col, Color color,int value){
        if ((this.getCell(row, col + 1).isFull())) {
            if((this.getCell(row,col+1).getAssignedDice().getValue())!=value && !this.getCell(row,col+1).getAssignedDice().getDiceColor().equals(color)) {
                return true;
            }
        }
        if (this.getCell(row + 1, col + 1).isFull()) {
            return true;
        }

        if ((this.getCell(row + 1, col).isFull())){
            if((this.getCell(row +1,col).getAssignedDice().getValue())!=value && !this.getCell(row+1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
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
    private boolean checkDownLeftCorner(int row, int col, Color color, int value){
        if (this.getCell(row - 1, col).isFull()) {
            if((this.getCell(row -1,col).getAssignedDice().getValue())!=value && !this.getCell(row-1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }

        }
        if (this.getCell(row - 1, col + 1).isFull()) {
            return true;
        }
        if (this.getCell(row, col + 1).isFull()) {
            if((this.getCell(row ,col+1).getAssignedDice().getValue())!=value && !this.getCell(row,col+1).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
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
    private boolean checkLeftColumn(int row, int col, Color color, int value){
        for (int i = 0; i < 3; i++) {  //CHECK
            if (this.getCell(row - 1 + i, col + 1).isFull()) {
                if(row-1+i == row){
                    if((this.getCell(row ,col+1).getAssignedDice().getValue())==value || this.getCell(row,col+1).getAssignedDice().getDiceColor().equals(color)){
                        return false;
                    }

                }
                return true;
            }
        }
        if (this.getCell(row - 1, col).isFull()) {
            if((this.getCell(row -1,col).getAssignedDice().getValue())!=value && !this.getCell(row-1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
            }
        if (this.getCell(row + 1, col).isFull()) {
            if((this.getCell(row +1,col).getAssignedDice().getValue())!=value && !this.getCell(row+1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }

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
    private boolean checkUpperRightCorner(int row, int col, Color color, int value){
        if (this.getCell(row, col - 1).isFull()) {
            if((this.getCell(row ,col-1).getAssignedDice().getValue())!=value && !this.getCell(row,col-1).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
        }
        if (this.getCell(row + 1, col - 1).isFull()) {
            return true;
        }

        if (this.getCell(row + 1, col).isFull()) {
            if((this.getCell(row +1,col).getAssignedDice().getValue())!=value && !this.getCell(row+1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }

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
    private boolean checkDownRightCorner(int row, int col, Color color, int value){
        if (this.getCell(row - 1, col).isFull()) {
            if((this.getCell(row -1,col).getAssignedDice().getValue())!=value && !this.getCell(row-1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }

        }
        if (this.getCell(row - 1, col - 1).isFull()) {
            return true;
        }

        if (this.getCell(row, col - 1).isFull()) {
            if((this.getCell(row ,col-1).getAssignedDice().getValue())!=value && !this.getCell(row,col-1).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }

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
    private boolean checkRightColumn(int row, int col, Color color, int value){
        for (int i = 0; i < 3; i++) { //CHECK
            if (this.getCell(row - 1 + i, col - 1).isFull()) {
                if(row-1+i == row){
                    if((this.getCell(row ,col-1).getAssignedDice().getValue())==value || this.getCell(row,col-1).getAssignedDice().getDiceColor().equals(color)){
                        return false;
                    }

                }
                return true;
            }
        }
        if (this.getCell(row - 1, col).isFull()) {
            if((this.getCell(row -1,col).getAssignedDice().getValue())!=value && !this.getCell(row-1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
        }
        if (this.getCell(row + 1, col).isFull()) {
            if((this.getCell(row +1,col).getAssignedDice().getValue())!=value && !this.getCell(row+1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
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
    private boolean checkUpperRow(int row, int col, Color color, int value){
        for (int i = 0; i < 3; i++) { //CHECK
            if (this.getCell(row + 1, col - 1 + i).isFull()) {
                if(col == col-1+i){
                    if((this.getCell(row +1,col).getAssignedDice().getValue())==value || this.getCell(row+1,col).getAssignedDice().getDiceColor().equals(color)){
                        return false;
                    }
                }
                return true;
            }
        }
        if (this.getCell(row, col - 1).isFull()) {
            if((this.getCell(row ,col-1).getAssignedDice().getValue())!=value && !this.getCell(row,col-1).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }

        }
        if (this.getCell(row, col + 1).isFull()) {
            if((this.getCell(row ,col + 1).getAssignedDice().getValue())!=value && !this.getCell(row,col+1).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
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
    private boolean checkDownRow(int row, int col, Color color, int value){
        for (int i = 0; i < 3; i++) { //CHECK
            if (this.getCell(row - 1, col - 1 + i).isFull()) {
                if(col-1+i == col){
                    if((this.getCell(row -1,col).getAssignedDice().getValue())==value || this.getCell(row-1,col).getAssignedDice().getDiceColor().equals(color)){
                        return false;
                    }

                }
                return true;
            }
        }
        if (this.getCell(row, col - 1).isFull()) {
            if((this.getCell(row ,col-1).getAssignedDice().getValue())!=value || !this.getCell(row,col-1).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
        }
        if (this.getCell(row, col + 1).isFull()) {
            if((this.getCell(row ,col+1).getAssignedDice().getValue())!=value || !this.getCell(row,col+1).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
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
    private boolean checkMiddelCells(int row, int col, Color color, int value){
        for(int i=0; i<3; i++){  //CHECK
            if (this.getCell(row-1, col-1+i).isFull()) {
                if(col== col-1+i){
                    if((this.getCell(row-1,col).getAssignedDice().getValue())==value || this.getCell(row-1,col).getAssignedDice().getDiceColor().equals(color)){
                        return false;
                    }
                }
                return true;
            }

            if (this.getCell(row+1, col-1+i).isFull()) {
                if(col-1+i == col){
                    if((this.getCell(row +1,col).getAssignedDice().getValue())==value || this.getCell(row+1,col).getAssignedDice().getDiceColor().equals(color)){
                        return false;
                    }
                }
                return true;
            }
        }
        if (this.getCell(row-1, col).isFull()) {
            if((this.getCell(row -1,col).getAssignedDice().getValue())!=value || !this.getCell(row-1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }

        }
        if (this.getCell(row+1, col).isFull()) {
            if((this.getCell(row +1,col).getAssignedDice().getValue())!=value || !this.getCell(row+1,col).getAssignedDice().getDiceColor().equals(color)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder schema = new StringBuilder();
        schema.append(name).append("\n");
        schema.append("difficulty level: ").append(difficultyLevel).append("\n");
        for(int row=0; row<cells.length; row++){
            for(int col=0; col<cells[row].length; col++){
                if(cells[row][col].isFull()){
                    schema.append(cells[row][col].getAssignedDice().toString());
                }
                else {
                    try{
                        switch (cells[row][col].getCellColor()){
                            case BLUE:{
                                schema.append(PaintingTool.ANSI_BLUE.escape()).append("[ ]").append(PaintingTool.RESET);
                                break;
                            }
                            case GREEN:{
                                schema.append(PaintingTool.ANSI_GREEN.escape()).append("[ ]").append(PaintingTool.RESET);
                                break;
                            }
                            case PURPLE:{
                                schema.append(PaintingTool.ANSI_PURPLE.escape()).append("[ ]").append(PaintingTool.RESET);
                                break;
                            }
                            case RED:{
                                schema.append(PaintingTool.ANSI_RED.escape()).append("[ ]").append(PaintingTool.RESET);
                                break;
                            }
                            case YELLOW:{
                                schema.append(PaintingTool.ANSI_YELLOW.escape()).append("[ ]").append(PaintingTool.RESET);
                                break;
                            }
                        }
                    } catch (NoColorException e){
                        switch (cells[row][col].getValue()){
                            case 0:{
                                schema.append("[ ]");
                                break;
                            }
                            case 1:{
                                schema.append("[1]");
                                break;
                            }
                            case 2:{
                                schema.append("[2]");
                                break;
                            }
                            case 3:{
                                schema.append("[3]");
                                break;
                            }
                            case 4:{
                                schema.append("[4]");
                                break;
                            }
                            case 5:{
                                schema.append("[5]");
                                break;
                            }
                            case 6:{
                                schema.append("[6]");
                                break;
                            }
                        }
                    }
                }
            }
            schema.append("\n");
        }
        return schema.toString();
    }


}

