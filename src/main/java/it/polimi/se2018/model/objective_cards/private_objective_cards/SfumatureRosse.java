package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.Color;
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
    /**
     * Methos that count the number of red dice in schema
     * @param schemaCard
     * @return n number of red dice in the schema
     */
    @Override
    public int countPoints(SchemaCard schemaCard) {
        int sum=0;
        for(int i=0; i<3; i++){
            for(int j=0; j<4 ; j++){
                if(schemaCard.getCell(i,j).getAssignedDice().getDiceColor().equals(Color.RED))
                    sum = sum + schemaCard.getCell(i,j).getAssignedDice().getValue();
            }

        }
        return sum;
    }
}
