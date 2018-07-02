package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.ToolCardErrorMessage;
import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.view.comand_line.InputManager;

import java.util.ArrayList;

/**
 * Effects class. Invocated when "" is used. Activates a method
 * to place a die on the schema card.
 */
public class PlaceDie implements TCEffectInterface {

    private boolean isDone;

    private int draftPoolDiePosition;
    private int row;
    private int col;

    public PlaceDie() {
        isDone = false;
    }

    @Override
    public void doYourJob(String username, String effectParameter, String values, Model model) throws ExecutingEffectException {
        System.out.println("PlaceDie is working");
        String[] words = values.split(" ");
        for(int i = 0; i<words.length; i++){
            if(words[i].trim().equalsIgnoreCase("draftPoolDiePosition:")){
                draftPoolDiePosition = Integer.parseInt(words[i+1]);
                System.out.println("read draftPoolDiePosition: " + draftPoolDiePosition);
                if(draftPoolDiePosition>model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().size()-1){
                    throw new ExecutingEffectException();
                }
            }
            if(words[i].trim().equalsIgnoreCase("row:")){
                row = Integer.parseInt(words[i+1]);
                System.out.println("read row: " + row);
            }
            if(words[i].trim().equalsIgnoreCase("col:")){
                col = Integer.parseInt(words[i+1]);
                System.out.println("read col: " + col);
            }
        }


        ArrayList<Dice> diceList = model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList();
        Dice die = diceList.get(draftPoolDiePosition);
        for(Player player: model.getParticipants()){
            if(player.getName().equals(username)){
                try{
                    if(effectParameter.equals("NoNearnessRestriction")) {
                        System.out.println("trying to place die w/" + die.toString());
                        player.getSchemaCard().placeDie(die, row, col, false,
                            false, true);
                        System.out.println("placed Die");
                        model.removeDieFromDraftPool(draftPoolDiePosition);
                }else {
                        System.out.println("trying to place die w/" + die.toString());
                        player.getSchemaCard().placeDie(die, row, col, false,
                                false, false);
                        System.out.println("placed Die");
                        model.removeDieFromDraftPool(draftPoolDiePosition);
                    }
                } catch (RestrictionsNotRespectedException e){
                    System.out.println("error restrictions");
                    throw new ExecutingEffectException();
                } catch (FullCellException e){
                    System.out.println("error fullcell");
                    throw new ExecutingEffectException();
                }
            }
        }
        isDone = true;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void setDone(boolean b) {
        isDone = b;
    }
}
