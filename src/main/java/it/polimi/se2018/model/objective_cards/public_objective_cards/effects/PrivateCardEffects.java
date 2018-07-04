package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

/**
 * @author giovanni
 * class that implements ObjectiveCardEffectInterface
 * with a method for calculating scores of all PrivateObjecive Cards
 */
public class PrivateCardEffects implements ObjectiveCardEffectInterface {
    /**
     * calculates the score based on the color of the card
     * @param model model of the game
     * @param cardName name of the card
     * @param point card score
     */
    @Override
    public void countPoints(Model model, String cardName, int point) {
        Color color = null;
        if(cardName.equalsIgnoreCase("Green Shades"))   {
            color = Color.GREEN;
        }
        if(cardName.equalsIgnoreCase("Blue Shades"))   {
            color =Color.BLUE;
        }
        if(cardName.equalsIgnoreCase("Purple Shades"))   {
            color =Color.PURPLE;
        }
        if(cardName.equalsIgnoreCase("Yellow Shades"))   {
            color =Color.YELLOW;
        }
        if(cardName.equalsIgnoreCase("Red Shades"))   {
            color =Color.RED;
        }
        for(int k=0;k<model.getParticipants().size();k++){
            if(model.getParticipants().get(k).getPrivateObjective().getName().equals(cardName)) {
                int sum = 0;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).isFull()) {
                            if (model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getDiceColor().equals(color))
                                sum = sum + model.getParticipants().get(k).getSchemaCard().getCell(i, j).getAssignedDice().getValue();
                        }
                    }

                }

                model.getParticipants().get(k).setPoints(sum);
            }
        }
    }
}
