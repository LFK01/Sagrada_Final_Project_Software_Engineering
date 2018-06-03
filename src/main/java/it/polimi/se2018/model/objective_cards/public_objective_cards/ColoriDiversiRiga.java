package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

import static java.lang.Integer.parseInt;

/**
 * @author Luciano
 * edited giovanni
 */
public class ColoriDiversiRiga extends AbstractObjectiveCard {

    private static ColoriDiversiRiga thisInstance = null; //tentativo

    private ColoriDiversiRiga() {
        super("Colori diversi - Riga", "Righe senza colori ripetuti", "6", false);
    }

    public static synchronized ColoriDiversiRiga getThisInstance(){
        if(thisInstance==null){
            thisInstance=new ColoriDiversiRiga();
        }
        return thisInstance;
    }

    /**
     * @param schemaCard
     * @return notifyView count points
     */
    @Override
    public int countPoints(SchemaCard schemaCard) {
        int n=0;        //variabile che serve per il controllo
        int points=0;
    for(int i=0; i<4; i++){
        for(int j=0; j<5 && n==0; j++){
            for(int k=0; k<5 && n==0; k++){
                if(schemaCard.getCell(i,j).getAssignedDice().getDiceColor()==schemaCard.getCell(i,k).getAssignedDice().getDiceColor()) n=1;
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
