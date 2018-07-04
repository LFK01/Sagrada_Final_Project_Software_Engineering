package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

/**
 * @author giovanni
 * class that implements ObjectiveCardEffectInterface with a method for calculating scores in columns
 */
public class ColumnCardsCS implements ObjectiveCardEffectInterface {
    /**
     * Count the scores of all players by scrolling along the columns.
     * He can look for both colors and values
     * @param model model of the game
     * @param cardName name of the card
     * @param point card score
     */
    @Override
    public void countPoints(Model model, String cardName, int point) {
        boolean searchColor = false;
        if(cardName.equals("Column Color Variety")){
            searchColor = true;
        }
        else {
            searchColor = false;
        }
        for(int k =0; k<model.getParticipants().size(); k++){
            int n=0;
            int points=0;
            for(int i =0;i<5;i++){
                for(int j =0; j<3 && n==0; j++){ //fino alla penultima riga
                    if(!model.getParticipants().get(k).getSchemaCard().getCell(j,i).isFull()){
                        n=1;
                    }
                    else
                    if(model.getParticipants().get(k).getSchemaCard().getCell(j + 1, i).isFull()) {
                        if (searchColor) {
                            if (model.getParticipants().get(k).getSchemaCard().getCell(j, i).getAssignedDice().getDiceColor().equals(model.getParticipants().get(k).getSchemaCard().getCell(j + 1, i).getAssignedDice().getDiceColor())) {
                                n = 1;
                            }
                        }
                        if (!searchColor) {
                            if (model.getParticipants().get(k).getSchemaCard().getCell(j, i).getAssignedDice().getValue() == (model.getParticipants().get(k).getSchemaCard().getCell(j + 1, i).getAssignedDice().getValue())) {
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



