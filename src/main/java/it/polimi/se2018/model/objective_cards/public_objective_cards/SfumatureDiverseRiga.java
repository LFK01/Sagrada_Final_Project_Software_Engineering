package it.polimi.se2018.model.objective_cards.public_objective_cards;
/**
 * @author Giovanni
 *
 */

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

import static java.lang.Integer.parseInt;

public class SfumatureDiverseRiga extends AbstractObjectiveCard {

    private static SfumatureDiverseRiga thisInstance;

    private SfumatureDiverseRiga() {
        super( "Sfumature diverse - Riga",  "Righe senza sfumature ripetute","5" , false);
    }

    public static synchronized SfumatureDiverseRiga getThisInstance() {
        if(thisInstance == null){
            thisInstance = new SfumatureDiverseRiga();
        }
        return thisInstance;
    }

    /**
     * @param schemaCard
     * @return update count points
     */
    @Override
    public int countPoints(SchemaCard schemaCard){
        int n=0;        //variabile che serve per il controllo
        int points=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5 && n==0; j++){
                for(int k=0; k<4 && n==0; k++){
                    if(schemaCard.getCell(i,j).getAssignedDice().getValue()==schemaCard.getCell(i,k).getAssignedDice().getValue()) n=1;
                }
            }
            if(n==0){
                points = points + parseInt(ColoriDiversiRiga.getThisInstance().getPoints());
            }
            n=0;
        }
        return points;
    }
}
