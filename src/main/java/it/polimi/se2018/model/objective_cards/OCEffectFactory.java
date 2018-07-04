package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.model.objective_cards.public_objective_cards.effects.*;

/**
 * @author giovanni
 * Factory method that assigns its effect to each Objective card
 */
public class OCEffectFactory {

    /**
     * An effect is created based on the parameter of the ObjectiveCard in the text file
     * @param effectName name of the card effect
     * @return object of ObjectiveCardEffectInterface type
     */
    public ObjectiveCardEffectInterface assigneEffect(String effectName) {
        switch (effectName) {

            default:  return new ColoredDiagonals();
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