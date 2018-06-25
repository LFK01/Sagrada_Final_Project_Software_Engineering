package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.model.objective_cards.public_objective_cards.effects.*;

public class OCEffectFactory {

    public ObjectiveCardEffectInterface assigneEffect(String effectName) {
        switch (effectName) {

            default:  return new DefaultCase();
                    case "ColumnCardsCS":
                        return new ColumnCardsCS();


                    case "RowCardsCS":
                        return new RowCardsCS();

                    case "PrivateCardEffects":
                        return new PrivateCardEffects();

                    case "Shades":
                        return new Shades();

                    case "DifferentShades":
                        return new DifferentShades();

                    case "ColorVariety":
                        return new ColorVariety();

                    case "ColoredDiagonals":
                        return new ColoredDiagonals();
        }
    }
}