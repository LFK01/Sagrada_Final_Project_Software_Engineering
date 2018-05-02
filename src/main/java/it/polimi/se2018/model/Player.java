package it.polimi.se2018.model;
//Giovanni
public class Player {
    private String name;
    private int favorTokens;
    private ObjectiveCard privateObjective;
    private SchemaCard windowFrame;
    private int points;

    //costruttore
    public Player(String n){
        this.name = n;
        this.favorTokens=0;
        this.privateObjective = null;
        this.windowFrame = null;
        this.points=0;
    }
    //metodi getter
    public String getName(){
        return name;
    }
    public int getFavorTokens() {
        return favorTokens;
    }
    public int getPoints(){
        return points;
    }

    public ObjectiveCard getPrivateObjective() {
        return privateObjective;
    }

    public SchemaCard getWindowFrame() {
        return windowFrame;
    }

    //metodi setter
    public void setPrivateObjectiveCard(){

    }
    public int setFavorTokens(){

    }
    public SchemaCard setSchemaCard(){

    }

    public void setPoints(int points) {
        this.points = points;
    }
}
