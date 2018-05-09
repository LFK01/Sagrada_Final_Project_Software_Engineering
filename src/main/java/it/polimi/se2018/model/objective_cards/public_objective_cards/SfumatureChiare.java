package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */
public class SfumatureChiare extends AbstractObjectiveCard {

    private static SfumatureChiare thisInstance;

    private SfumatureChiare() {
        super("Sfumature Chiare", "Set di 1 & 2 ovunque", "2", false);
    }

    public static SfumatureChiare getThisInstance() {
        if(thisInstance ==null){
            thisInstance = new SfumatureChiare();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
