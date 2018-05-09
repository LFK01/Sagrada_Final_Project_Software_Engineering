package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

public class SfumatureRosse extends AbstractObjectiveCard {

    private static SfumatureRosse thisInstance;

    private SfumatureRosse() {
        super("Sfumature Rosse", "Somma dei valori su tutti i dadi rossi", "#", true);
    }

    public static synchronized SfumatureRosse getThisInstance(){
        if(thisInstance==null){
            thisInstance = new SfumatureRosse();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
