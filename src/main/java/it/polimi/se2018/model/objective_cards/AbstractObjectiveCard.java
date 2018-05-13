package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.model.SchemaCard;

/**
 * @author Luciano
 */

public abstract class AbstractObjectiveCard {
    protected String name;
    protected String description;
    protected String points;
    protected boolean isPrivate;

    public AbstractObjectiveCard(String name, String description,String points, boolean isPrivate){
        this.name = name;
        this.description = description;
        this.points = points;
        this.isPrivate=isPrivate;
    }

    public String getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isPrivate(){
        return isPrivate;
    }

    public abstract int countPoints(SchemaCard schemaCard);
}
