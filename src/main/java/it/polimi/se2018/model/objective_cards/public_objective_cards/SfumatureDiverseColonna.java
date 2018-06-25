package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

public class SfumatureDiverseColonna {

    private static SfumatureDiverseColonna thisInstance;


    public synchronized static SfumatureDiverseColonna getThisInstance() {
        if(thisInstance==null){
            thisInstance = new SfumatureDiverseColonna();
        }
        return thisInstance;
    }


    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
