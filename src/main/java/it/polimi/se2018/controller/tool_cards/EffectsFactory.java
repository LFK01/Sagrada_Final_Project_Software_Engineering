package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.controller.tool_cards.effects.ModifyDieValue;
import it.polimi.se2018.model.Model;

public class EffectsFactory {

    public EffectsFactory(){}

    public EffectInterface getThisEffect(String effectName){
        if(effectName.equalsIgnoreCase("ModifyDieValue")){
            return new ModifyDieValue();
        } else {
            return new EffectInterface() {

                @Override
                public void doYourJob(String username, String toolCardName, String values, Model model) {

                }

                @Override
                public boolean isDone() {
                    return true;
                }
            };
        }
    }
}
