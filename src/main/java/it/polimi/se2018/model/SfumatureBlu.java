package it.polimi.se2018.model;

import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

public class SfumatureBlu extends AbstractObjectiveCard {
    private static SfumatureBlu thisInstance;
    private SfumatureViola(){
        super("SfumatureViola","Somma dei valori su tutti i dadi blu","#",true);
    }

    private static SfumatureBlu getThisInstance(){
        if(thisInstance==null){
            thisInstance= new SfumatureBlu();
        }
        return SfumatureBlu;
    }

    @Override
    public int countPoints(SchemaCard schemaCard){
        return 0;
    }
}
