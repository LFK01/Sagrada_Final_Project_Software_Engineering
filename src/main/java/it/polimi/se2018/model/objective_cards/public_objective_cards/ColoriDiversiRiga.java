package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Luciano
 */
public class ColoriDiversiRiga extends AbstractObjectiveCard {

    private static ColoriDiversiRiga thisInstance;

    private ColoriDiversiRiga() {
        super("Colori diversi - Riga", "Righe senza colori ripetuti", "6", false);
    }

    public static synchronized ColoriDiversiRiga getThisInstance(){
        if(thisInstance==null){
            thisInstance=new ColoriDiversiRiga();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
