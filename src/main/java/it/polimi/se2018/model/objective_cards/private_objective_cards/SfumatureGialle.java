package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */

public class SfumatureGialle extends AbstractObjectiveCard {

    private static SfumatureGialle thisInstance;

    private SfumatureGialle() {
        super("Sfumature Gialle", "Somma dei valori su tutti i dadi gialli", "#", true);
    }

    public synchronized static SfumatureGialle getThisInstance(){
        if(thisInstance==null){
            thisInstance = new SfumatureGialle();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
