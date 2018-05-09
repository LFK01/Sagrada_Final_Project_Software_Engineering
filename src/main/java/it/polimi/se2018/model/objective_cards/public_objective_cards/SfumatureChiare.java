package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */
public class SfumatureChiare extends AbstractObjectiveCard {

    private static SfumatureChiare thisIstance;

    private SfumatureChiare() {
        super("Sfumature Chiare", "Set di 1 & 2 ovunque", "2", false);
    }

    public synchronized static SfumatureChiare getThisIstance() {
        if(thisIstance==null){
            thisIstance = new SfumatureChiare();
        }
        return thisIstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
