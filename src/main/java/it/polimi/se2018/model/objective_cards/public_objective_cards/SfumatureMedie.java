package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */

public class SfumatureMedie extends AbstractObjectiveCard {

    private static SfumatureMedie thisInstance;

    private SfumatureMedie(){
        super("SfumatureMedie","Set di 3 & 4 ovunque","2",false);
    }

    public synchronized static SfumatureMedie getThisInstance(){
        if(thisInstance==null){
            thisInstance = new SfumatureMedie();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
