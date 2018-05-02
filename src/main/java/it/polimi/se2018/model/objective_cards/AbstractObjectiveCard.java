package it.polimi.se2018.model.objective_cards;
//Giovanni

public abstract class AbstractObjectiveCard {
    private String name;
    private String description;
    private int points;

    public AbstractObjectiveCard(String name, String description,int points){
        this.name = name;
        this.description = description;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }


}
