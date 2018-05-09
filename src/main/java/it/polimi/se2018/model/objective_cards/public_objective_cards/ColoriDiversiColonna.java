package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

public class ColoriDiversiColonna extends AbstractObjectiveCard {

    public static ColoriDiversiColonna thisIstance;

    private ColoriDiversiColonna() {
        super("Colori diversi - Colonna", "Colonne senza colori ripetuti", "5", false);
    }

    public synchronized static ColoriDiversiColonna getThisIstance(){
        if(thisIstance==null){
            thisIstance = new ColoriDiversiColonna();
        }
        return thisIstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
