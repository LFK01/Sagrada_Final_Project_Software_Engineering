package it.polimi.se2018.model.objective_cards.private_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

public class SfumatureVerdi extends AbstractObjectiveCard {

    private static SfumatureVerdi thisIstance;
    public SfumatureVerdi() {
        super("Sfumature Verdi", "Somma dei valori su tutti i dadi verdi", "#", true);
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
