package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.objective_cards.public_objective_cards.effects.ColumnCardsCS;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

public class OCEffectFactory {

    public ObjectiveCardEffectInterface assigneEffect(String effectName) {
        switch (effectName) {
            default:  return new DefaultCase();
            case "ColumnCardsCS":
                return new ColumnCardsCS();


        }


    }

}