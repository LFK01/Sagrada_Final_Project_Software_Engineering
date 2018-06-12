package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.EffectInterface;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.SuccessMessage;
import it.polimi.se2018.model.events.messages.ToolCardErrorMessage;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;

public class ModifyDieValue implements EffectInterface {

    private boolean isDone;

    public ModifyDieValue(){
        isDone = false;
    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) {
        if(toolCardName.equalsIgnoreCase(new ToolCard(1).getName())){
            String[] words = values.split(" ");
            int diePosition = -1;
            boolean increaseValue = false;
            for(int i=0; i < words.length; i++){
                if(words[i].trim().equalsIgnoreCase("DiePosition:")){
                    diePosition = Integer.parseInt(values.split(" ")[0]);
                }
                if(words[i].trim().equalsIgnoreCase("IncreaseValue:")){
                    if(words[i+1].trim().equalsIgnoreCase("y")){
                        increaseValue = true;
                    }
                    else {
                        increaseValue = false;
                    }
                }
            }
            ArrayList<Dice> diceList = model.getGameBoard().getRoundTrack().getRoundDice()[model.getGameBoard().getRoundTrack().getCurrentRound()].getDiceList();
            Dice dieToModify = diceList.get(diePosition);
            if(increaseValue){
                if(dieToModify.getValue() == 6){
                    model.setChanged();
                    model.notifyObservers(new ToolCardErrorMessage("server", username, toolCardName, "NotValidPinzaSgrossatriceMove"));
                } else {
                    dieToModify.setValue(dieToModify.getValue()+1);
                    model.setChanged();
                    model.notifyObservers(new SuccessMessage("server", username, "SuccessfulMove"));
                    Player activePlayer = null;
                    for(Player player: model.getParticipants()){
                        if(player.getName().equals(username)){
                            activePlayer = player;
                        }
                    }
                    for(ToolCard toolCard: model.getGameBoard().getToolCards()){
                        if(toolCard.getName().equalsIgnoreCase(toolCardName)){
                            if(toolCard.isFirstUsage()){
                                activePlayer.decreaseFavorTokens(false);
                            } else {
                                activePlayer.decreaseFavorTokens(true);
                            }
                        }
                    }
                    model.updateGameboard();
                    isDone = true;
                }
            } else {
                if(dieToModify.getValue() == 1){
                    model.setChanged();
                    model.notifyObservers(new ToolCardErrorMessage("server", username, toolCardName, "NotValidPinzaSgrossatriceMove"));
                } else {
                    dieToModify.setValue(dieToModify.getValue()+1);
                    Player activePlayer = null;
                    for(Player player: model.getParticipants()){
                        if(player.getName().equals(username)){
                            activePlayer = player;
                        }
                    }
                    for(ToolCard toolCard: model.getGameBoard().getToolCards()){
                        if(toolCard.getName().equalsIgnoreCase(toolCardName)){
                            if(toolCard.isFirstUsage()){
                                activePlayer.decreaseFavorTokens(false);
                            } else {
                                activePlayer.decreaseFavorTokens(true);
                            }
                        }
                    }
                    model.setChanged();
                    model.notifyObservers(new SuccessMessage("server", username, "SuccessfulMove"));
                    model.updateGameboard();
                    isDone = true;
                }
            }
        }
        if(toolCardName.equalsIgnoreCase(new ToolCard(6).getName())){

        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
