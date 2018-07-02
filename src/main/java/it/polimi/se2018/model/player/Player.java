package it.polimi.se2018.model.player;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

import java.io.Serializable;

/**
 * @author Giovanni
 */

public class Player implements Serializable {

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
        for(int i =0; i<10;i++){
            playerTurns[i] = new Round();
        }
    }

    public String getName(){ return name; }

    public boolean getConnected(){
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

    public void setPoints(int points) {
        this.points = this.points + points;
    }

    public void setDiagonalPoints(int points){
        this.points = points;
    }

    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    /**
     *method to decrease player's favor Tokens, also check if a player can use that card
     * @param toolCardHasAlreadyBeenUsed boolean value that determines how many favor tokens
     *                                   has to be detracted
     */
    public void decreaseFavorTokens(boolean toolCardHasAlreadyBeenUsed){
        if(toolCardHasAlreadyBeenUsed){
            this.favorTokens = this.favorTokens - 2;
        }
        else{
            this.favorTokens = this.favorTokens - 1;
        }
    }


}