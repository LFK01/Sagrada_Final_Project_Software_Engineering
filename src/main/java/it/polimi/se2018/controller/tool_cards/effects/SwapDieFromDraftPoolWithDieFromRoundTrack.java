package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.ToolCardErrorMessage;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.view.comand_line.InputManager;

public class SwapDieFromDraftPoolWithDieFromRoundTrack implements TCEffectInterface {

    boolean isDone;
    int draftPoolPosition;
    int roundNumber;
    int roundTrackPosition;

    public SwapDieFromDraftPoolWithDieFromRoundTrack(){
        draftPoolPosition = -1;
        roundNumber = -1;
        roundTrackPosition = -1;
        isDone = false;
    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) {
        String[] words = values.split(" ");
        boolean correctInput = true;
        for(int i = 0; i<words.length; i++){
            if(words[i].trim().equalsIgnoreCase("DraftPoolPosition:")){
                draftPoolPosition = Integer.parseInt(words[i+1]);
                int maximumDraftPoolPosition = model.getGameBoard().getRoundDice()[model.getRoundNumber()].
                        getDiceList().size();
                if(draftPoolPosition>maximumDraftPoolPosition){
                    model.setChanged();
                    model.notifyObservers(new ToolCardErrorMessage("server", username, toolCardName,
                            "NotValidParameter", InputManager.INPUT_POSITION_DRAFTPOOL_POSITION_ROUNDTRACK));
                }
            }
            if(words[i].trim().equalsIgnoreCase("RoundNumber:")){
                int maximumRoundNumber = model.getRoundNumber();
                roundNumber = Integer.parseInt(words[i+1]);
                if(roundNumber>maximumRoundNumber){
                    model.setChanged();
                    model.notifyObservers(new ToolCardErrorMessage("server", username,
                            toolCardName, "NotValidParameter", InputManager.INPUT_POSITION_DRAFTPOOL_POSITION_ROUNDTRACK));
                }
            }
            if(words[i].trim().equalsIgnoreCase("RoundTrackPosition:")){
                roundTrackPosition = Integer.parseInt(words[i+1]);
                int maximumTrackPosition = model.getGameBoard().getRoundTrack().
                        getRoundDice()[roundNumber].getDiceList().size();
                if(roundTrackPosition > maximumTrackPosition){
                    model.setChanged();
                    model.notifyObservers(new ToolCardErrorMessage("server", username, toolCardName,
                            "NotValidParametere", InputManager.INPUT_POSITION_DRAFTPOOL_POSITION_ROUNDTRACK));
                }
            }
        }
        Dice draftPoolDie = model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].
                getDice(draftPoolPosition);
        Dice supportDie = model.getGameBoard().getRoundTrack().getRoundDice()[roundNumber].setDice(draftPoolDie, roundTrackPosition);
        model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].setDice(supportDie, draftPoolPosition);
        model.updateGameboard();
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
