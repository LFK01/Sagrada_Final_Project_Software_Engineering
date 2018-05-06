package it.polimi.se2018.model.objective_cards.public_objective_cards;
//Giovanni

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

public class SfumatureDiverseRiga extends AbstractObjectiveCard {
    private int n=0;   //serve per saltare alla riga successiva della matrice ( qui rappresentata come array)
    public SfumatureDiverseRiga() {
        super( "sfumatureDiverseRiga",  "",'0' );
    }
    public void countPoints(Player player){
        for(int i=0; i< 4  ; i++){
            for(int j= i*5;j< i*5+5 && n==0;j++){
                for(int k = j+1; k <i*5+5 && n==0;k++){
                    if (player.getWindowFrame().getCell(j) == player.getWindowFrame().getCell(k)){
                        n=1;
                    }
                }
            }
            if(n==1){
                n=0;
            }
            else if(n==0){
                player.setPoints(player.getPoints() + this.getPoints());
            }


        }


    }
}
