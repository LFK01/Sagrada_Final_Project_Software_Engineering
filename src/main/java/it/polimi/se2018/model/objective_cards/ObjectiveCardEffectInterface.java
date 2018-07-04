package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.model.Model;

/**
 * @author Giovanni
 * Interface for managing the effect of objective cards
 */
public interface ObjectiveCardEffectInterface {

    void countPoints(Model model, String cardName, int point);

}
