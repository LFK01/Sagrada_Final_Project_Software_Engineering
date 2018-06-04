package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Giovanni
 */

public class SfumatureBlu extends AbstractObjectiveCard {

    private static SfumatureBlu thisInstance;
    private String color;

    private SfumatureBlu(){
        super("SfumatureBlu","Somma dei valori su tutti i dadi viola","#",true);
    }

    public synchronized static SfumatureBlu getThisInstance(){
        if(thisInstance==null){
            thisInstance= new SfumatureBlu();
        }
        return thisInstance;
    }

    /**
     * Methos that count the number of blue dice in schema
     * @param schemaCard
     * @return n number of blue dice in the schema
     */
    @Override
    public int countPoints(SchemaCard schemaCard){
        int sum=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5 ; j++){
                    if(schemaCard.getCell(i,j).getAssignedDice().getDiceColor().equals(Color.BLUE))
                    sum = sum + schemaCard.getCell(i,j).getAssignedDice().getValue();
            }

        }
        return sum;
    }
}
