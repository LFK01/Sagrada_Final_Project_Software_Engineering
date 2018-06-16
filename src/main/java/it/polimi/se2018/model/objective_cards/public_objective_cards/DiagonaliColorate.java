package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;
//Giovanni
public class DiagonaliColorate {
    private static DiagonaliColorate thisInstance;


    public static synchronized DiagonaliColorate getThisInstance() {
        if(thisInstance == null){
            thisInstance= new DiagonaliColorate();

        }
        return thisInstance;
    }

    public int countPoints(SchemaCard schemaCard){
        return 0;

    }
}
