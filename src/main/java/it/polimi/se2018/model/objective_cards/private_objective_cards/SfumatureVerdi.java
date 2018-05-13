package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.Color;
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
    /**
     * Methos that count the number of green dice in schema
     * @param schemaCard
     * @return n number of  green dice in the schema
     */
    @Override
    public int countPoints(SchemaCard schemaCard) {
        int sum=0;
        for(int i=0; i<3; i++){
            for(int j=0; j<4 ; j++){
                if(schemaCard.getCell(i,j).getAssignedDice().getDiceColor().equals(Color.GREEN))
                    sum = sum + schemaCard.getCell(i,j).getAssignedDice().getValue();
            }

        }
        return sum;
    }
}
