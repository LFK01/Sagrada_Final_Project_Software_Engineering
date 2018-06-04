package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

public class ColoriDiversiColonna extends AbstractObjectiveCard {

    public static ColoriDiversiColonna thisInstance;

    private ColoriDiversiColonna() {
        super("Colori diversi - Colonna", "Colonne senza colori ripetuti", "5", false);
    }

    public synchronized static ColoriDiversiColonna getThisInstance(){
        if(thisInstance==null){
            thisInstance = new ColoriDiversiColonna();
        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }
}
