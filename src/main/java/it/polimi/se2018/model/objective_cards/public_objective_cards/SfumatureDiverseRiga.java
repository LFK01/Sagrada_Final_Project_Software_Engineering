package it.polimi.se2018.model.objective_cards.public_objective_cards;
//Giovanni

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
import it.polimi.se2018.model.objective_cards.InterfaceObjectiveCard;
import it.polimi.se2018.model.tool_cards.InterfaceToolCard;
//Singleton
public class SfumatureDiverseRiga extends AbstractObjectiveCard implements InterfaceObjectiveCard {

    private int n=0;   //serve per saltare alla riga successiva della matrice ( qui rappresentata come array)

    public SfumatureDiverseRiga() {
        super( "sfumatureDiverseRiga",  "",'0' );
    }

    private static SfumatureDiverseRiga thisInstance;

    @Override
    public void countPoints(Player player){
        for(int i=0; i< 4  ; i++){
            for(int j= i*5;j< i*5+5 && n==0;j++){
                for(int k = j+1; k <i*5+5 && n==0;k++){
                    if (player.getSchemaCard().getCell(j).getAssignedDice().getValue() == player.getSchemaCard().getCell(k).getAssignedDice().getValue()){
                        n=1;
                    }
                }
            }
            if(n==1){                                                           //non aggiorna il punteggio
                n=0;
            }
            else if(n==0){
                player.setPoints(player.getPoints() + this.getPoints());        //aggiorna il punteggio
            }


        }


    }

    @Override
    public AbstractObjectiveCard getInstance() {
        return thisInstance;
    }

    public static synchronized SfumatureDiverseRiga getThisInstance() {
        if(thisInstance == null){
            thisInstance = new SfumatureDiverseRiga();
        }
        return thisInstance;
    }

}
