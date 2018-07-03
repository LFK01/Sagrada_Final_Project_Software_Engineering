package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Dice;

public class SwapDieFromDraftPoolWithDieFromRoundTrack implements TCEffectInterface {

    private boolean isDone;
    private int draftPoolPosition;
    private int roundNumber;
    private int roundTrackPosition;

    public SwapDieFromDraftPoolWithDieFromRoundTrack(){
        draftPoolPosition = -1;
        roundNumber = -1;
        roundTrackPosition = -1;
        isDone = false;
    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) throws ExecutingEffectException {
        String[] words = values.split(" ");
        for(int i = 0; i<words.length; i++){
            if(words[i].trim().equalsIgnoreCase("DraftPoolPosition:")){
                draftPoolPosition = Integer.parseInt(words[i+1]);
                System.out.println("read DraftPoolPosition: " + draftPoolPosition);
                int maximumDraftPoolPosition = model.getGameBoard().getRoundDice()[model.getRoundNumber()].
                        getDiceList().size()-1;
                if(draftPoolPosition>maximumDraftPoolPosition){
                    System.out.println("error");
                    throw new ExecutingEffectException();
                }
            }
            if(words[i].trim().equalsIgnoreCase("RoundNumber:")){
                int maximumRoundNumber = model.getRoundNumber();
                roundNumber = Integer.parseInt(words[i+1]);
                System.out.println("read roundNumber: " + roundNumber);
                if(roundNumber>maximumRoundNumber){
                    throw new ExecutingEffectException();
                }
            }
            if(words[i].trim().equalsIgnoreCase("RoundTrackPosition:")){
                roundTrackPosition = Integer.parseInt(words[i+1]);
                System.out.println("read roundTrackPosition: " + roundTrackPosition);
                int maximumTrackPosition = model.getGameBoard().getRoundTrack().
                        getRoundDice()[roundNumber].getDiceList().size();
                if(roundTrackPosition > maximumTrackPosition){
                    throw new ExecutingEffectException();
                }
            }
        }
        Dice draftPoolDie = model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()]
                .removeDiceFromList(draftPoolPosition);
        model.getGameBoard().getRoundTrack().getRoundDice()[roundNumber].getDiceList().add(draftPoolDie);
        Dice supportDie = model.getGameBoard().getRoundTrack().getRoundDice()[roundNumber]
                .removeDiceFromList(roundTrackPosition);
        model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].getDiceList()
                .add(draftPoolPosition, supportDie);
        System.out.println("swapped dice");
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
