package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

/**
 * @author giovanni
 */

public class RowCardsCS implements ObjectiveCardEffectInterface {
    @Override
    public void countPoints(Model model, String cardName, int point) {
        boolean isColor=false;
        if(cardName.equals("Sfumature Diversi Riga")){
            isColor = false;
        }
        else {
            isColor = true ;
        }
        for(int k =0; k<model.getParticipants().size(); k++){
            int n=0;
            int points=0;
            for(int i =0;i<4;i++){
                for(int j =0; j<4 && n==0; j++){ //fino alla penultima riga
                    if(!model.getParticipants().get(k).getSchemaCard().getCell(i,j).isFull()){
                        n=1;
                    }
                    else
                        if(!isColor){
                            if(model.getParticipants().get(k).getSchemaCard().getCell(i,j).getAssignedDice().getValue()==(model.getParticipants().get(k).getSchemaCard().getCell(i,j+1).getAssignedDice().getValue())) {
                            n = 1;
                        }
                        else{
                                if(model.getParticipants().get(k).getSchemaCard().getCell(i,j).getAssignedDice().getDiceColor().equals((model.getParticipants().get(k).getSchemaCard().getCell(i,j+1).getAssignedDice().getDiceColor()))) {
                                    n = 1;
                                }

                            }
                    }
                }
                if(n==0){
                    points = points + 5;
                }
                else {
                    n=0;
                }
            }
            model.getParticipants().get(k).setPoints(points);
        }
    }
}
