package it.polimi.se2018.model;
//Giorgia

public class Cell {

    private Color cellColor;
    private boolean noColor;
    private int value;
    private boolean placedDice;
    private boolean avoidValueRestriction;
    private boolean avoidColorRestriction;
    private boolean avoidNearnessRestriction;
    private Dice assignedDice;

    public Cell(Color color, int value){

        this.cellColor = color;

        if (color == null)                  //controllo per gestione delle celle senza restrizioni di colore
            this.noColor = true;
        else
            this.noColor = false;

        this.value = value;
        this.placedDice = false;
        this.avoidValueRestriction = false;
        this.avoidColorRestriction = false;
        this.avoidNearnessRestriction = false;
        this.assignedDice = null;

    }

    public Color getCellColor() {
        return cellColor;
    }

    public int getValue() {
        return value;
    }

    public boolean isFull() {
        return placedDice;
    }

    public void setAssignedDice(Dice dice) {
        this.assignedDice = dice;
    }

    public Dice getAssignedDice() {
        return assignedDice;
    }

}
