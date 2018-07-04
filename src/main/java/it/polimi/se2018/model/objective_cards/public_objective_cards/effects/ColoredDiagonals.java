package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;

/**
 * @author giovanni
 * Count points based on the colored diagonals
 */
public class ColoredDiagonals implements ObjectiveCardEffectInterface {
    private int coloredDice =1;
    private int[][] supportScheme = new int[4][5];
    int numberOfCycles =0;
    @Override
    public void countPoints(Model model, String cardName, int point) {
        for(int k =0; k <model.getParticipants().size();k++){
            for(int s =0;s<4;s++){
                for(int t =0;t<5;t++){
                    supportScheme[s][t] = 0;
                }
            }
            for(int i =0;i<4;i++){
                for(int j=0;j<5;j++){
                    if(model.getParticipants().get(k).getSchemaCard().getCell(i,j).isFull()){
                        if(j==0 ){
                            numberOfCycles =0;
                            searchDownRight(i+1,j+1,model,k,model.getParticipants().get(k).getSchemaCard().getCell(i,j).getAssignedDice().getDiceColor());
                        }
                        else if(j==4){
                            numberOfCycles =0;
                            searchDownLeft(i+1,j-1,model,k,model.getParticipants().get(k).getSchemaCard().getCell(i,j).getAssignedDice().getDiceColor());
                        }
                        else{
                            numberOfCycles =0;
                            searchDownRight(i+1,j+1,model,k,model.getParticipants().get(k).getSchemaCard().getCell(i,j).getAssignedDice().getDiceColor());
                            numberOfCycles =0;
                            searchDownLeft(i+1,j-1,model,k,model.getParticipants().get(k).getSchemaCard().getCell(i,j).getAssignedDice().getDiceColor());
                        }
                    }
                }
            }
        }
    }

    /**
     * Iterative method that checks if there are dice of the same color along the right diagonal
     * @param row row of the first die
     * @param col col of the first die
     * @param model model to use arrayList participants
     * @param playerPosition position of the player
     * @param color color of the comparison die
     */

    private void searchDownRight(int row,int col,Model model, int playerPosition, Color color) {
        if (row<4 && col<5 && row>-1 && col >-1) {
            numberOfCycles = numberOfCycles +1;
            if (model.getParticipants().get(playerPosition).getSchemaCard().getCell(row, col).isFull()) {
                if (model.getParticipants().get(playerPosition).getSchemaCard().getCell(row, col).getAssignedDice().getDiceColor().equals(color)) {
                    if(supportScheme[row][col]== 2 ){
                        supportScheme[row][col] = 3;
                        model.getParticipants().get(playerPosition).setPoints(1); //aumenta di 1
                        if(numberOfCycles ==1){
                            model.getParticipants().get(playerPosition).setPoints(1);   //se sono all'inizio metto un valore in più
                        }
                        searchDownRight(row + 1, col + 1, model, playerPosition, color);
                    }
                    else if(supportScheme[row][col]==0){
                        supportScheme[row][col] = 1;
                        model.getParticipants().get(playerPosition).setPoints(1); //aumenta di 1
                        if(numberOfCycles ==1){
                            model.getParticipants().get(playerPosition).setPoints(1);   //se sono all'inizio metto un valore in più
                        }
                        searchDownRight(row + 1, col + 1, model, playerPosition, color);
                    }
                }
            }
        }
    }

    /**
     * Iterative method that checks if there are dice of the same color along the right diagonal
     * @param row row of the first die
     * @param col col of the first die
     * @param model model to use arrayList participants
     * @param playerPosition position of the player
     * @param color color of the comparison die
     */
    private void searchDownLeft(int row,int col,Model model, int playerPosition, Color color) {
        if (row<4 && col<5&&row>-1 && col >-1) {
            numberOfCycles = numberOfCycles +1;
            if (model.getParticipants().get(playerPosition).getSchemaCard().getCell(row, col).isFull()) {
                if (model.getParticipants().get(playerPosition).getSchemaCard().getCell(row, col).getAssignedDice().getDiceColor().equals(color)) {
                    if(supportScheme[row][col]== 1 ){
                        supportScheme[row][col] = 3;
                        model.getParticipants().get(playerPosition).setPoints(1); //aumenta di 1
                        if(numberOfCycles ==1){
                            model.getParticipants().get(playerPosition).setPoints(1);   //se sono all'inizio metto un valore in più
                        }
                        searchDownLeft(row + 1, col - 1, model, playerPosition, color);
                    }
                    else if(supportScheme[row][col]==0){
                        supportScheme[row][col] = 2;
                        model.getParticipants().get(playerPosition).setPoints(1); //aumenta di 1
                        if(numberOfCycles ==1){
                            model.getParticipants().get(playerPosition).setPoints(1);   //se sono all'inizio metto un valore in più
                        }
                        searchDownLeft(row + 1, col - 1, model, playerPosition, color);
                    }
                }
            }
        }
    }

}
