package it.polimi.se2018.model;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
import it.polimi.se2018.model.objective_cards.InterfaceObjectiveCard;

import javax.xml.validation.Schema;

//Giovanni
public class Player {

    private String name;
    private int favorTokens;
    private InterfaceObjectiveCard privateObjective;
    private SchemaCard schemaCard;
    private int points;

    //costruttore
    public Player(String name){
        this.name = name;
        this.favorTokens=0;
        this.privateObjective = null;
        this.schemaCard = null;
        this.points=0;
    }
    //metodi getter
    public String getName(){ return name; }

    public int getFavorTokens() {
        return favorTokens;
    }

    public int getPoints(){
        return points;
    }

    public AbstractObjectiveCard getPrivateObjective() {
        return privateObjective.getInstance();
    }

    public SchemaCard getSchemaCard() {
        return schemaCard;
    }

    //metodi setter
    public void setPrivateObjectiveCard(InterfaceObjectiveCard privateObjective){

    }
    
    public void setSchemaCard(SchemaCard schema){

    }

    public void setPoints(int points) {
        this.points = points;
    }
}
