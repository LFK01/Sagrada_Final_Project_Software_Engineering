package it.polimi.se2018.model.player;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

import java.io.Serializable;

/**
 * @author Giovanni
 * class representing the player.
 * Has a name, the number of favor tokens,
 * a parameter representing his private objective card,
 * the score (initially 0),
 * a parameter for your schemacard,
 * an array representing the possible moves a player can make for each turn
 * and a Boolean to figure out if the player is active or no.
 */

public class Player  {

    private String name;
    private boolean isConnected;
    private int favorTokens;
    private ObjectiveCard privateObjective;
    private SchemaCard schemaCard;
    private int points;
    private Round[] playerTurns;

    public Player(String name){
        this.name = name;
        this.isConnected = true;
        this.favorTokens=0;
        this.privateObjective = null;
        this.schemaCard = null;
        this.points=0;
        this.playerTurns = new Round[Model.MAXIMUM_ROUND_NUMBER];
        for(int i =0; i<Model.MAXIMUM_ROUND_NUMBER;i++){
            playerTurns[i] = new Round();
        }
    }

    public String getName(){ return name; }

    public boolean isConnected(){
        return this.isConnected;
    }

    public int getFavorTokens() {
        return favorTokens;
    }

    public int getPoints(){
        return points;
    }


    public SchemaCard getSchemaCard() {
        return schemaCard;
    }

    public Round[] getPlayerTurns() {
        return playerTurns;
    }

    public void setPrivateObjectiveCard(ObjectiveCard privateObjective){
        this.privateObjective = privateObjective;
    }
    public ObjectiveCard getPrivateObjective() {
        return privateObjective;
    }

    public void setSchemaCard(SchemaCard schema){
        this.schemaCard = schema;
        this.favorTokens = schema.getDifficultyLevel();
    }
    public void setPointTo0(){
        this.points =0;
    }

    /**
     * sets player's points, adding the new score to the old one each time
     * @param points new points to add to the score
     */
    public void setPoints(int points) {
        this.points = this.points + points;
    }


    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    /**
     *method to decrease player's favor Tokens, also check if a player can use that card
     * @param isFirstUsage boolean value that determines how many favor tokens
     *                                   has to be detracted
     */
    public void decreaseFavorTokens(boolean isFirstUsage){
        if(!isFirstUsage){
            this.favorTokens = this.favorTokens - 2;
        }
        else{
            this.favorTokens = this.favorTokens - 1;
        }
    }


}