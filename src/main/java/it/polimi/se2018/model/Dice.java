package it.polimi.se2018.model;
//Giorgia

public class Dice {

    private Color diceColor;
    private int value;
    private boolean inSchema;
    private boolean inTrack;
    private boolean inDraftPool;

    public Dice(Color color, int value){

        this.diceColor = color;
        this.value = value;
        inSchema = false;
        inTrack = false;
        inDraftPool = false;

    }

    public Color getDiceColor() {
        return diceColor;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {           //metodo per eventuali modifiche di valore del dado con carte utensili
        this.value = value;
    }

}
