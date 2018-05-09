package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
//Giovanni
public class SfumatureDiverse extends AbstractObjectiveCard{
    private static SfumatureDiverse thisInstance;

    private SfumatureDiverse(){
        super("SfumatureDiverse","Set di dadi di ogni  valore ovunque","5",false);
    }

    public static SfumatureDiverse getThisInstance() {
        if(thisInstance==null){
            thisInstance = new SfumatureDiverse();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard){
        return 0;

    }
}
