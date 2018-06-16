package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

/**
 * @author Luciano
 */

public class SfumatureMedie  {

    private static SfumatureMedie thisInstance;

    public synchronized static SfumatureMedie getThisInstance(){
        if(thisInstance==null){
            thisInstance = new SfumatureMedie();
        }
        return thisInstance;
    }

    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
