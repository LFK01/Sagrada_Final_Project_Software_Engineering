package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Giovanni
 */

public class SfumatureBlu extends AbstractObjectiveCard {

    private static SfumatureBlu thisInstance;

    private SfumatureBlu(){
        super("SfumatureBlu","Somma dei valori su tutti i dadi viola","#",true);
    }

    public synchronized static SfumatureBlu getThisInstance(){
        if(thisInstance==null){
            thisInstance= new SfumatureBlu();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard){
        return 0;
    }
}
