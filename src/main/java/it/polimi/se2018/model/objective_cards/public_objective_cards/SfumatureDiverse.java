package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

public class SfumatureDiverse {
    private static SfumatureDiverse thisInstance;



    public synchronized static SfumatureDiverse getThisInstance() {
        if(thisInstance==null){
            thisInstance = new SfumatureDiverse();
        }
        return thisInstance;
    }
    public int countPoints(SchemaCard schemaCard) {
        int n1 = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                if(schemaCard.getCell(i,j).isFull()){
                    if(schemaCard.getCell(i,j).getAssignedDice().getValue() ==1){
                        n1 = n1 +1 ;
                    }
                    if(schemaCard.getCell(i,j).getAssignedDice().getValue() ==2){
                        n2 = n2 +1 ;
                    }
                    if(schemaCard.getCell(i,j).getAssignedDice().getValue() ==3){
                        n3 = n3 +1 ;
                    }
                    if(schemaCard.getCell(i,j).getAssignedDice().getValue() ==4){
                        n4 = n4 +1 ;
                    }
                    if(schemaCard.getCell(i,j).getAssignedDice().getValue() ==5){
                        n5 = n5 +1 ;
                    }
                    if(schemaCard.getCell(i,j).getAssignedDice().getValue() ==6){
                        n6 = n6 +1 ;
                    }
                }
            }
        }
        //ordino 6 numeri
        return 0;
    }
}
