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
    public void doYourJob(String username, String effectParameter, String values, Model model) throws ExecutingEffectException {
        System.out.println("modifyDieValue is working");
        String[] words = values.split(" ");
        int diePosition = -1;
        int newValue = -1;
        for(int i=0; i < words.length; i++){
            System.out.println("reading: " + words[i]);
            if(words[i].trim().equalsIgnoreCase("draftPoolDiePosition:")){
                /*gets die position from message values*/
                diePosition = Integer.parseInt(words[i+1]);
                System.out.println("read die position: " + diePosition);
                if(diePosition>model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().size()-1){
                    throw new ExecutingEffectException();
                }
            }
            if(words[i].trim().equalsIgnoreCase("NewValue:")){
                System.out.println("read new value: " + newValue);
                newValue = Integer.parseInt(words[i+1]);
            }
        }
        ArrayList<Dice> diceList = model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].getDiceList();
        Dice dieToModify = diceList.get(diePosition);
        if(effectParameter.equalsIgnoreCase("IncreaseDecrease")){
            System.out.println("hashmap reading works");
            boolean increaseValue = false;
            for(int i=0; i < words.length; i++){
                if(words[i].trim().equalsIgnoreCase("IncreaseValue:")){
                    /*gets player choice from message values*/
                    System.out.println("read increase value: " + words[i+1]);
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
                    throw new ExecutingEffectException();
                } else {
                    dieToModify.setValue(dieToModify.getValue()+1);
                    System.out.println("modified die value: " + dieToModify.getValue() + " " + dieToModify.toString());
                }
            } else {
                if(dieToModify.getValue() == 1){
                    /*impossible to decrease a die value below 1*/
                    throw new ExecutingEffectException();
                } else {
                    dieToModify.setValue(dieToModify.getValue()-1);
                    System.out.println("modified die value: " + dieToModify.getValue() + " " + dieToModify.toString());
                }
            }
        }
        if(effectParameter.equalsIgnoreCase("Roll")){
            dieToModify.roll();
            System.out.println("modified die value: " + dieToModify.getValue());
        }
        if(effectParameter.equalsIgnoreCase("TurnFace")){
            dieToModify.turnDieFace();
            System.out.println("modified die value: " + dieToModify.getValue());
        }
        if(effectParameter.equalsIgnoreCase("SetValue")){
            dieToModify.setValue(newValue);
            System.out.println("modified die value: " + dieToModify.getValue());
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
