package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

public class DefaultCase implements ObjectiveCardEffectInterface {
    @Override
    public void countsPoints(Model model, String cardName, int point) {
        return 0;
    }
}
