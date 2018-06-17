package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

public class Shades implements ObjectiveCardEffectInterface {
    @Override
    public void countPoints(Model model, String cardName, int point) {
        int check1 = 0;
        int check2 = 0;
        if (cardName.equals("Sfumature Chiare ")) {
            check1 = 1;
            check2 = 2;
        }
        if (cardName.equals("Sfumature Medie ")) {
            check1 = 3;
            check2 = 4;
        }
        if (cardName.equals("Sfumature Scure ")) {
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
