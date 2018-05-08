package it.polimi.se2018.model.objective_cards.public_objective_cards;
/**
 * @author Giovanni
 *
 */

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

public class SfumatureDiverseRiga extends AbstractObjectiveCard {

    private static SfumatureDiverseRiga thisInstance;

    private SfumatureDiverseRiga() {
        super( "Sfumature diverse - Riga",  "Righe senza sfumature ripetute","5" , false);
    }

    public static synchronized SfumatureDiverseRiga getThisInstance() {
        if(thisInstance == null){
            thisInstance = new SfumatureDiverseRiga();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard){
        return 0;
    }
}
