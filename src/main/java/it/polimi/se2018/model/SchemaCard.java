package it.polimi.se2018.model;

/**
 * Schema Cards class
 * @author Giorgia
 * edited Giovanni
 */

public class SchemaCard {

    private String name1;
    private String name2;
    private Cell[][] cells;
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
}

