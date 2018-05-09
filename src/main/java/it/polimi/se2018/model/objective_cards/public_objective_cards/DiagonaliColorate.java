package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
//Giovanni
public class DiagonaliColorate extends AbstractObjectiveCard{
    private static DiagonaliColorate thisInstance;

    private DiagonaliColorate(){
        super("DiagonaliColorate","Numero di dadi dello stesso colore diagonalmente adiacenti","0",false);
    }

    public static DiagonaliColorate getThisInstance() {
        if(thisInstance == null){
            thisInstance= new DiagonaliColorate();

        }
        return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard){
        return 0;

    }
}
