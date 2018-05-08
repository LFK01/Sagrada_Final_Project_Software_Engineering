package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */
public class SfumatureDiverseColonna extends AbstractObjectiveCard{

    private static SfumatureDiverseColonna thisIstance;

    private SfumatureDiverseColonna() {
        super("Sfumature diverse - Colonna", "Colonne senza sfumature ripetute", "4", false);
    }

    public static SfumatureDiverseColonna getThisIstance() {
        if(thisIstance==null){
            thisIstance = new SfumatureDiverseColonna();
        }
        return thisIstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
