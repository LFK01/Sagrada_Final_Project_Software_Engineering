package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

/**
 * @author Luciano
 */

public class SfumatureGialle {

    private static SfumatureGialle thisInstance;
    public synchronized static SfumatureGialle getThisInstance(){
        if(thisInstance==null){
            thisInstance = new SfumatureGialle();
        }
        return thisInstance;
    }
    /**
     * Methos that count the number of yellow dice in schema
     * @param schemaCard
     * @return n number of yellow dice in the schema
     */
    public int countPoints(SchemaCard schemaCard) {

        int sum=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5 ; j++){
                if(schemaCard.getCell(i,j).getAssignedDice().getDiceColor().equals(Color.YELLOW))
                    sum = sum + schemaCard.getCell(i,j).getAssignedDice().getValue();
            }

        }
        return sum;
    }
}
