package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

/**
 * @author giovanni
 * class that implements ObjectiveCardEffectInterface
 * with a method for calculating scores based on dice values
 */
public class Shades implements ObjectiveCardEffectInterface {

    /**
     *Count the scores of all players based on dice value
     * @param model model of the game
     * @param cardName name of the card
     * @param point card score
     */
    @Override
    public void countPoints(Model model, String cardName, int point) {
        int check1 = 0;
        int check2 = 0;
        if (cardName.equals("Light Shades")) {
            check1 = 1;
            check2 = 2;
        }
        if (cardName.equals("Medium Shades")) {
            check1 = 3;
            check2 = 4;
        }
        if (cardName.equals("Deep Shades")) {
            check1 = 5;
            check2 = 6;
        }
        for (int k = 0; k < model.getParticipants().size(); k++) {
            int n1 = 0;
            int n2 = 0;
            int points = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).isFull()) {
                        if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue() == check1) {
                            n1 = n1 + 1;
                        }
                        if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue() == check2) {
                            n2 = n2 + 1;
                        }
                    }
                }
            }
            if (n1 < n2) {
                points = point * n1;
            } else {
                points = point * n2;
            }
            model.getParticipants().get(k).setPoints(points);
        }
    }
}
