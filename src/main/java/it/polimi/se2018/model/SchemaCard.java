package it.polimi.se2018.model;

public class SchemaCard {

    private String name1;
    private String name2;
    private Cell cells[][];
    private int difficultyLevel;

    public SchemaCard(int n) {          //costruttore con switch per creare le carte schema pescate dal giocatore

        /*switch (n) {

            case 1:         //Virtus
                this.name1 = "Virtus";
                this.name2 = "Kaleidoscopic Dream";
                this.cells[0] = new Cell(null, 4);
                this.cells[1] = new Cell(null, 0);
                this.cells[2] = new Cell(null, 2);
                this.cells[3] = new Cell(null, 5);
                this.cells[4] = new Cell(Color.GREEN, 0);
                this.cells[5] = new Cell(null, 0);
                this.cells[6] = new Cell(null, 0);
                this.cells[7] = new Cell(null, 6);
                this.cells[8] = new Cell(Color.GREEN, 0);
                this.cells[9] = new Cell(null, 2);
                this.cells[10] = new Cell(null, 0);
                this.cells[11] = new Cell(null, 3);
                this.cells[12] = new Cell(Color.GREEN, 0);
                this.cells[13] = new Cell(null, 4);
                this.cells[14] = new Cell(null, 0);
                this.cells[15] = new Cell(null, 5);
                this.cells[16] = new Cell(Color.GREEN, 0);
                this.cells[17] = new Cell(null, 1);
                this.cells[18] = new Cell(null, 0);
                this.cells[19] = new Cell(null, 0);
                this.difficultyLevel = 5;
                break;

            case 2:
                this.name1 = "Via Lux";
                this.name2 = "Aurorae Magnificus";
                this.cells[0] = new Cell(Color.YELLOW, 0);
                this.cells[1] = new Cell(null, 0);
                this.cells[2] = new Cell(null, 6);
                this.cells[3] = new Cell(null, 0);
                this.cells[4] = new Cell(null, 0);
                this.cells[5] = new Cell(null, 0);
                this.cells[6] = new Cell(null, 1);
                this.cells[7] = new Cell(null, 5);
                this.cells[8] = new Cell(null, 0);
                this.cells[9] = new Cell(null, 2);
                this.cells[10] = new Cell(null, 3);
                this.cells[11] = new Cell(Color.YELLOW, 0);
                this.cells[12] = new Cell(Color.RED, 0);
                this.cells[13] = new Cell(Color.PURPLE, 0);
                this.cells[14] = new Cell(null, 0);
                this.cells[15] = new Cell(null, 0);
                this.cells[16] = new Cell(null, 0);
                this.cells[17] = new Cell(null, 4);
                this.cells[18] = new Cell(null, 3);
                this.cells[19] = new Cell(Color.RED, 0);
                this.difficultyLevel = 4;
                break;

            case 3:
                this.name1 = "Bellesguard";
                this.name2 = "Sun Catcher";
                this.cells[0] = new Cell(Color.BLUE, 0);
                this.cells[1] = new Cell(null, 6);
                this.cells[2] = new Cell(null, 0);
                this.cells[3] = new Cell(null, 0);
                this.cells[4] = new Cell(Color.YELLOW, 0);
                this.cells[5] = new Cell(null, 0);
                this.cells[6] = new Cell(null, 3);
                this.cells[7] = new Cell(Color.BLUE, 0);
                this.cells[8] = new Cell(null, 0);
                this.cells[9] = new Cell(null, 0);
                this.cells[10] = new Cell(null, 0);
                this.cells[11] = new Cell(null, 5);
                this.cells[12] = new Cell(null, 6);
                this.cells[13] = new Cell(null, 2);
                this.cells[14] = new Cell(null, 0);
                this.cells[15] = new Cell(null, 0);
                this.cells[16] = new Cell(null, 4);
                this.cells[17] = new Cell(null, 0);
                this.cells[18] = new Cell(null, 1);
                this.cells[19] = new Cell(Color.GREEN, 0);
                this.difficultyLevel = 3;
                break;

            case 4:
                this.name1 = "Firmitas";
                this.name2 = "Symphony of Light";
                this.cells[0] = new Cell(Color.PURPLE, 0);
                this.cells[1] = new Cell(null, 6);
                this.cells[2] = new Cell(null, 0);
                this.cells[3] = new Cell(null, 0);
                this.cells[4] = new Cell(null, 3);
                this.cells[5] = new Cell(null, 5);
                this.cells[6] = new Cell(Color.PURPLE, 0);
                this.cells[7] = new Cell(null, 3);
                this.cells[8] = new Cell(null, 0);
                this.cells[9] = new Cell(null, 0);
                this.cells[10] = new Cell(null, 0);
                this.cells[11] = new Cell(null, 2);
                this.cells[12] = new Cell(Color.PURPLE, 0);
                this.cells[13] = new Cell(null, 1);
                this.cells[14] = new Cell(null, 0);
                this.cells[15] = new Cell(null, 0);
                this.cells[16] = new Cell(null, 1);
                this.cells[17] = new Cell(null, 5);
                this.cells[18] = new Cell(Color.PURPLE, 0);
                this.cells[19] = new Cell(null, 4);
                this.difficultyLevel = 5;
                break;

            case 5:
                this.name1 = "Aurora Sagradis";
                this.name2 = "Industria";
                this.cells[0] = new Cell(Color.RED, 0);
                this.cells[1] = new Cell(null, 0);
                this.cells[2] = new Cell(Color.BLUE, 0);
                this.cells[3] = new Cell(null, 0);
                this.cells[4] = new Cell(Color.YELLOW, 0);
                this.cells[5] = new Cell(null, 4);
                this.cells[6] = new Cell(Color.PURPLE, 0);
                this.cells[7] = new Cell(null, 3);
                this.cells[8] = new Cell(Color.GREEN, 0);
                this.cells[9] = new Cell(null, 2);
                this.cells[10] = new Cell(null, 0);
                this.cells[11] = new Cell(null, 1);
                this.cells[12] = new Cell(null, 0);
                this.cells[13] = new Cell(null, 5);
                this.cells[14] = new Cell(null, 0);
                this.cells[15] = new Cell(null, 0);
                this.cells[16] = new Cell(null, 0);
                this.cells[17] = new Cell(null, 6);
                this.cells[18] = new Cell(null, 0);
                this.cells[19] = new Cell(null, 0);
                this.difficultyLevel = 4;
                break;

                //[...] resto delle carte

        }*/

    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public boolean isEmpty() {
        for(int i=0; i<cells.length; i++){
            for(int j=0; j<cells[i].length; j++){
                if (cells[i][j].isFull()) return false;
            }
        }
        return true;
    }
}
