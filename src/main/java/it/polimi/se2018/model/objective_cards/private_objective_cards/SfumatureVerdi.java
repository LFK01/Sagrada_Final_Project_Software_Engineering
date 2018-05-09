package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */

public class SfumatureVerdi extends AbstractObjectiveCard {

    private static SfumatureVerdi thisInstance;

    private SfumatureVerdi() {
        super("Sfumature Verdi", "Somma dei valori su tutti i dadi verdi", "#", true);
    }

    public synchronized static SfumatureVerdi getThisInstance(){
        if(thisInstance==null){
            thisInstance = new SfumatureVerdi();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
