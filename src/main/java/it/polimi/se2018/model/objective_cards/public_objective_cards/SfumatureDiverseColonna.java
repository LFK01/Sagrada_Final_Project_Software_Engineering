package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */
public class SfumatureDiverseColonna extends AbstractObjectiveCard{

    private static SfumatureDiverseColonna thisInstance;

    private SfumatureDiverseColonna() {
        super("Sfumature diverse - Colonna", "Colonne senza sfumature ripetute", "4", false);
    }

    public synchronized static SfumatureDiverseColonna getThisInstance() {
        if(thisInstance==null){
            thisInstance = new SfumatureDiverseColonna();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
