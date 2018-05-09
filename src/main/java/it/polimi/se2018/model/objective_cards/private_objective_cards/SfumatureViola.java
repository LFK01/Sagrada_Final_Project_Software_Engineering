package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Giovanni
 */

public class SfumatureViola extends AbstractObjectiveCard {

    private static SfumatureViola thisInstance;

    private SfumatureViola(){
        super("SfumatureViola","Somma dei valori su tutti i dadi viola","#",true);
    }

    public synchronized static SfumatureViola getThisInstance(){
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
