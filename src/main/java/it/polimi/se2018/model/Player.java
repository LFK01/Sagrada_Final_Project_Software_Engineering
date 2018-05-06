package it.polimi.se2018.model;
import it.polimi.se2018.model.objective_cards.InterfaceObjectiveCard;

//Giovanni
public class Player {
    private String name;
    private int favorTokens;
    private InterfaceObjectiveCard privateObjective;
    private SchemaCard windowFrame;
    private int points;

    //costruttore
    public Player(String name){
        this.name = name;
        this.favorTokens=0;
        this.privateObjective = null;
        this.windowFrame = null;
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

    public InterfaceObjectiveCard getPrivateObjective() {
        return privateObjective;
    }

    public SchemaCard getWindowFrame() {
        return windowFrame;
    }

    //metodi setter
    public void setPrivateObjectiveCard(){

    }

    public void setFavorTokens(){

    }
    
    public void setSchemaCard(){

    }

    public void setPoints(int points) {
        this.points = points;
    }
}
