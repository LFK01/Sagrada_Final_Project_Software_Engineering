package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.SchemaCard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Giovanni
 * Class that is initialized through a parser and represents the Objective cards.
 * Based on a parameter, you can understand if they are public or private
 */

public class ObjectiveCard {

    private boolean isPrivate;
    private String name = null;
    private String description = null;
    private int points;
    private ObjectiveCardEffectInterface effect;
    OCEffectFactory factory = new OCEffectFactory();


    public ObjectiveCard(String name ,String description,int points,ObjectiveCardEffectInterface effect){
        this.name = name;
        this.description = description;
        this.points = points;
        this.effect = effect;
    }


    public boolean isPrivate(){
        return isPrivate;
    }

    /*public int countPoints(SchemaCard schemaCard) {
        return 0;
    }*/

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

    public ObjectiveCardEffectInterface getEffect() {
        return effect;
    }

    /**
     * Generic method that calculates points for each player
     * Each card implements its own point calculation using this method
     * @param model model of the game
     * @param name name of the ObjectiveCard
     * @param points points of the ObjectiveCard
     */
    public void countPoints(Model model,String name, int points){
        effect.countPoints(model,name,points);
    }

}
