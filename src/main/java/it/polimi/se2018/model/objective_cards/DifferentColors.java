package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.model.Model;

public class DifferentColors implements ObjectiveCardEffectInterface {

    @Override
    public void countsPoints(Model model, String cardName, int point) {
        int dim1 =0;
        int dim2=0;
        if(cardName.equals("Colori Diversi Riga")){
            dim1 =4;
            dim2 = 5;
        }
        else {
            dim1 =5;
            dim2 = 4;
        }
        for(int i =0; i<model.getParticipants().size(); i++){
            int n=0;        //variabile che serve per il controllo
            int points=0;
            for(int s=0; s<dim1; s++){
                for(int j=0; j<dim2 && n==0; j++){
                    for(int k=0; k<dim2 && n==0; k++){
                        if(model.getParticipants().get(i).getSchemaCard().getCell(s,j).getAssignedDice().getDiceColor()==model.getParticipants().get(i).getSchemaCard().getCell(s,k).getAssignedDice().getDiceColor()) n=1;
                    }
                }
                if(n==0){
                    points = points + point;
                }
                n=0;
            }
            model.getParticipants().get(i).setPoints(points);
        }
    }
}


