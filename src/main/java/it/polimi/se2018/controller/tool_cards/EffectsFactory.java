package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.controller.tool_cards.effects.*;
import it.polimi.se2018.model.Model;

public class EffectsFactory {

    public EffectsFactory(){}

    public TCEffectInterface getThisEffect(String effectName){
        if(effectName.equalsIgnoreCase("ModifyDieValue")){
            return new ModifyDieValue();
        }
        if(effectName.equalsIgnoreCase("MoveDieOnWindow")){
            return new MoveDieOnWindow();
        }
        if(effectName.equalsIgnoreCase("SwapDieFromDraftPoolWithDieFromRoundTrack")){
            return new SwapDieFromDraftPoolWithDieFromRoundTrack();
        }
        if(effectName.equalsIgnoreCase("PlaceDie")){
            return new PlaceDie();
        }
        if(effectName.equalsIgnoreCase("RollDraftPoolDice")){
            return new RollDraftPoolDice();
        }
        if(effectName.equalsIgnoreCase("ExtractNewDie")){
            return new ExtractNewDie();
        }
        else {
            return new TCEffectInterface() {
                @Override
                public void doYourJob(String username, String toolCardName, String values, Model model) {
                }
                @Override
                public boolean isDone() {
                    return true;
                }
                @Override
                public void setDone(boolean b) {
                }
            };
        }
    }
}
