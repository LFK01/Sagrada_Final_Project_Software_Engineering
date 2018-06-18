package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.ToolCardErrorMessage;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.view.comand_line.InputManager;

import java.util.ArrayList;

public class ModifyDieValue implements TCEffectInterface {

    private boolean isDone;

    public ModifyDieValue(){
        isDone = false;
    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) throws ExecutingEffectException {
        String[] words = values.split(" ");
        int diePosition = -1;
        int newValue = -1;
        for(int i=0; i < words.length; i++){
            if(words[i].trim().equalsIgnoreCase("DiePosition:")){
                /*gets die position from message values*/
                diePosition = Integer.parseInt(words[i+1]);
                if(diePosition>model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().size()-1){
                    throw new ExecutingEffectException();
                }
            }
            if(words[i].trim().equalsIgnoreCase("NewValue:")){
                newValue = Integer.parseInt(words[i+1]);
            }
        }
        ArrayList<Dice> diceList = model.getGameBoard().getRoundTrack().getRoundDice()[model.getGameBoard().getRoundTrack().getCurrentRound()].getDiceList();
        Dice dieToModify = diceList.get(diePosition);
        if(toolCardName.equalsIgnoreCase(ToolCard.searchNameByNumber(1))){
            /*Pinza Sgrossatrice*/
            boolean increaseValue = false;
            for(int i=0; i < words.length; i++){
                if(words[i].trim().equalsIgnoreCase("IncreaseValue:")){
                    /*gets player choice from message values*/
                    if(words[i+1].trim().equalsIgnoreCase("y")){
                        increaseValue = true;
                    }
                    else {
                        increaseValue = false;
                    }
                }
            }
            if(increaseValue){
                if(dieToModify.getValue() == 6){
                    /*impossible to increase a die value above 6*/
                    model.setChanged();
                    model.notifyObservers(new ToolCardErrorMessage("server", username, toolCardName, "NotValidPinzaSgrossatriceMove", InputManager.INPUT_MODIFY_DIE_VALUE));
                } else {
                    dieToModify.setValue(dieToModify.getValue()+1);
                }
            } else {
                if(dieToModify.getValue() == 1){
                    /*impossible to decrease a die value below 1*/
                    model.setChanged();
                    model.notifyObservers(new ToolCardErrorMessage("server", username, toolCardName, "NotValidPinzaSgrossatriceMove", InputManager.INPUT_MODIFY_DIE_VALUE));
                } else {
                    dieToModify.setValue(dieToModify.getValue()-1);
                }
            }
        }
        if(toolCardName.equalsIgnoreCase(ToolCard.searchNameByNumber(6))){
            model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].
                    getDiceList().get(diePosition).roll();
        }
        if(toolCardName.equalsIgnoreCase(ToolCard.searchNameByNumber(10))){
            model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDice(diePosition).setValue(newValue);
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
