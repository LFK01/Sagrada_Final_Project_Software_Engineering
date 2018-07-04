package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

/**
 * @author giovanni
 * class that implements ObjectiveCardEffectInterface
 * with a method for calculating scores in rows
 */

public class RowCardsCS implements ObjectiveCardEffectInterface {
    /**
     * Count the scores of all players by scrolling along the rows.
     * He can look for both colors and values
     * @param model model of the game
     * @param cardName name of the card
     * @param point card score
     */
    @Override
    public void countPoints(Model model, String cardName, int point) {
        boolean searchColor=false;
        if(cardName.equals("Sfumature Diversi Riga")){
            searchColor = false;
        }
        else {
            searchColor = true ;
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
                    if(model.getParticipants().get(k).getSchemaCard().getCell(i, j+1).isFull()) {
                        if (searchColor) {
                            if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getDiceColor().equals(model.getParticipants().get(k).getSchemaCard().getCell(i, j+1).getAssignedDice().getDiceColor())) {
                                n = 1;
                            }
                        }
                        if (!searchColor) {
                            if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue() == (model.getParticipants().get(k).getSchemaCard().getCell(i, j+1).getAssignedDice().getValue())) {
                                n = 1;
                            }
                        }
                    }
                    else n=1;
                }
                if(n==0){
                    points = points + point;
                }
                else {
                    n=0;
                }
            }
            model.getParticipants().get(k).setPoints(points);
        }
    }
}
