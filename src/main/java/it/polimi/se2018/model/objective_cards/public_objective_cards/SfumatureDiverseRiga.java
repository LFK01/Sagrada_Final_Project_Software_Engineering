package it.polimi.se2018.model.objective_cards.public_objective_cards;


import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

import static java.lang.Integer.parseInt;

public class SfumatureDiverseRiga {

    private static SfumatureDiverseRiga thisInstance;


    public static synchronized SfumatureDiverseRiga getThisInstance() {
        if(thisInstance == null){
            thisInstance = new SfumatureDiverseRiga();
        }
        return thisInstance;
    }


    public int countPoints(SchemaCard schemaCard){
        int n=0;        //variabile che serve per il controllo
        int points=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5 && n==0; j++){
                for(int k=0; k<4 && n==0; k++){
                    if(schemaCard.getCell(i,j).getAssignedDice().getValue()==schemaCard.getCell(i,k).getAssignedDice().getValue()) n=1;
                }
            }
            if(n==0){
                //points = points + parseInt(ColoriDiversiRiga.getThisInstance().getPoints());
            }
            n=0;
        }
        return points;
    }
}
