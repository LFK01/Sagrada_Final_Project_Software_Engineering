package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

/**
 * @author Giovanni
 */

public class SfumatureScure {

    private static SfumatureScure thisInstance;

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

