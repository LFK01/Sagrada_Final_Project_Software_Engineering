package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

public class ColoriDiversiColonna  {

    public static ColoriDiversiColonna thisInstance;

    private ColoriDiversiColonna() {

    }

    public synchronized static ColoriDiversiColonna getThisInstance(){
        if(thisInstance==null){
            thisInstance = new ColoriDiversiColonna();
        }
        return thisInstance;
    }

    public int countPoints(SchemaCard schemaCard) {
       int n=0;
       int points=0;
        for(int i =0;i<5;i++){
            for(int j =0; j<3 && n==0; j++){ //fino alla penultima riga
                if(!schemaCard.getCell(j,i).isFull()){
                    n=1;
                }
                else if(schemaCard.getCell(j,i).getAssignedDice().getDiceColor().equals(schemaCard.getCell(j+1,i).getAssignedDice().getDiceColor())){
                    n=1;
                }
            }
            if(n==0){
                points = points + 5;
            }
            else {
                n=0;
            }
        }
        return points;
    }
}
