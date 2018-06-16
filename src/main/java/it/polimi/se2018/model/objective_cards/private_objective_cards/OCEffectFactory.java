package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.model.objective_cards.DifferentColors;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

public class OCEffectFactory {

    public ObjectiveCardEffectInterface assigneEffect(String effectName) {
        switch (effectName) {
            default:  return new DefaultCase();
            case "DifferentColor":
                return new DifferentColors();


        }


    }

}