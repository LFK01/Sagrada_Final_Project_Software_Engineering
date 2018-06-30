package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.SchemaCard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Giovanni
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

    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }

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

    public void countPoints(Model model,String name, int points){
        effect.countPoints(model,name,points);
    }

}
