package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */

public class SfumatureRosse extends AbstractObjectiveCard {

    private static SfumatureRosse thisIstance;

    private SfumatureRosse() {
        super("Sfumature Rosse", "Somma dei valori su tutti i dadi rossi", "#", true);
    }

    public synchronized static SfumatureRosse getThisIstance(){
        if(thisIstance==null){
            thisIstance = new SfumatureRosse();
        }
        return thisIstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
