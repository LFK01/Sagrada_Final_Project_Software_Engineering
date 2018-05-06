package it.polimi.se2018.model.objective_cards;
//Giovanni

import it.polimi.se2018.model.Player;

public class SfumatureDiverseRiga extends AbstractObjectiveCard {
    private int n=0;   //serve per saltare alla riga successiva della matrice ( qui rappresentata come array)
    public SfumatureDiverseRiga() {
        super( "sfumatureDiverseRiga",  "",'0' );
    }
    public void countPoints(Player player){
        for(int i=0; i< 4  ; i++){
            for(int j= i*5;j< i*5+5 && n==0;j++){
                for(int k = j+1; k <i*5+5 && n==0;k++){
                    if (player.getWindowFrame().getCell(j).getValue() == player.getWindowFrame().getCell(k).getValue()){
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
}
