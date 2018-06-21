package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

/**
 * @author giovanni
 */
public class DifferentShades implements ObjectiveCardEffectInterface {
    @Override
    public void countPoints(Model model, String cardName, int point) {
        for (int k = 0; k < model.getParticipants().size(); k++) {
            int n1 = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 =0;
            int points = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).isFull()) {
                        if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue() == 1) {
                            n1 = n1 + 1;
                        }
                        if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue() == 2) {
                            n2 = n2 + 1;
                        }
                        if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue() == 3) {
                            n3 = n3 + 1;
                        }
                        if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue() == 4) {
                            n4 = n4 + 1;
                        }
                        if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue() == 5) {
                            n5 = n5 + 1;
                        }
                        if(model.getParticipants().get(k).getSchemaCard().getCell(i,j).getAssignedDice().getValue() == 6){
                            n6 = n6 +1;
                        }
                    }
                }
            }
            int[] sort = new int[6];
            sort[0] = n1;
            sort[1] = n2;
            sort[2] = n3;
            sort[3] = n4;
            sort[4] = n5;
            sort[5] = n6;
            int index =0;
            for(int s =0; s<5;s++){
                if(sort[s]<sort[s+1]){
                    index = sort[s];
                    sort[s] = sort[s+1];
                    sort[s+1] = index;
                }
            }
            model.getParticipants().get(k).setPoints(point * sort[5]);
        }

    }
}
