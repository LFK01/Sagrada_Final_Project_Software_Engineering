package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

/**
 * @author Luciano
 */
public class SfumatureChiare {

    private static SfumatureChiare thisInstance;


    public synchronized static SfumatureChiare getThisInstance() {
        if(thisInstance==null){
            thisInstance = new SfumatureChiare();
        }
        return thisInstance;
    }

    public int countPoints(SchemaCard schemaCard) {
        int n1=0;
        int n2=0;
        int n =0;
        int points =0;
        for(int i =0;i<4;i++){
            for(int j=0;j<5;j++){
                if(schemaCard.getCell(i,j).isFull()) {
                    if (schemaCard.getCell(i, j).getAssignedDice().getValue() == 1) {
                        n1 = n1 + 1;
                    }
                    if (schemaCard.getCell(i, j).getAssignedDice().getValue() == 2) {
                        n2 = n2 + 1;
                    }
                }
            }

        }
        if(n1<n2){
            points = 2 * n1;
        }
        else{
            points = 2 * n2;
        }

        return points;
    }
}
