package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Giovanni
 */

public class SfumatureScure extends AbstractObjectiveCard {

    private static SfumatureScure thisInstance;

    private SfumatureScure(){
        super("SfumatureScure","Set di 5 & 6 ovunque","2",false);
    }

    public synchronized static SfumatureScure getThisInstance() {
        if(thisInstance==null){
            thisInstance = new SfumatureScure();
        }
        return thisInstance;
    }

    public int countPoints(SchemaCard schemaCard){
        return 0;

    }
}

