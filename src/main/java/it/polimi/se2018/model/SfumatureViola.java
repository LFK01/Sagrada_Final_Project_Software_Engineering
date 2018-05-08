package it.polimi.se2018.model;

import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
import it.polimi.se2018.model.objective_cards.public_objective_cards.SfumatureMedie;

public class SfumatureViola extends AbstractObjectiveCard {
    private static SfumatureViola thisInstance;
    private SfumatureViola(){
        super("SfumatureViola","Somma dei valori su tutti i dadi viola","#",true)
    }

    public static SfumatureViola getThisInstance(){
        if(thisInstance == null){
            thisInstance = new SfumatureViola();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard){
        return 0;
    }



}
