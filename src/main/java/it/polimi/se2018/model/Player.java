package it.polimi.se2018.model;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

//Giovanni

public class Player {

    private String name;
    private boolean state;
    private int favorTokens;
    private AbstractObjectiveCard privateObjective;
    private SchemaCard schemaCard;
    private int points;

    //costruttore
    public Player(String name){
        this.name = name;
        this.state = true;
        this.favorTokens=0;
        this.privateObjective = null;
        this.schemaCard = null;
        this.points=0;

    }
    //metodi getter
    public String getName(){ return name; }

    public boolean getState(){
        return this.state;
    }

    public int getFavorTokens() {
        return favorTokens;
    }

    public int getPoints(){
        return points;
    }

    public AbstractObjectiveCard getPrivateObjective() {
        return privateObjective;
    }

    public SchemaCard getSchemaCard() {
        return schemaCard;
    }

    //metodi setter
    public void setPrivateObjectiveCard(AbstractObjectiveCard privateObjective){

    }
    
    public void setSchemaCard(SchemaCard schema){

    }

    public void setPoints(int points) {
        this.points = points;
    }
}
