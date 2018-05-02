package it.polimi.se2018.model;
//Giorgia

public class Dice {

    private Color diceColor;
    private int value;
    private boolean inSchema;
    private boolean inTrack;
    private boolean inDraftPool;
    private SchemaCard schema;
    private int pos;

    public Dice(Color color, int value){

        this.diceColor = color;
        this.value = value;
        inSchema = false;
        inTrack = false;
        inDraftPool = false;
        schema = null;
        int pos = 0;

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
    
    public void setInSchema(SchemaCard schema, int pos) {       //data una carta schema e un indice associa il dado alla cella corrispondente
        this.schema = schema;
        this.pos = pos;
    }

}
