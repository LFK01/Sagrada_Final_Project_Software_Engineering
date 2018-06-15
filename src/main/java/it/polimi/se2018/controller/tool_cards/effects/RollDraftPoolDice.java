package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.EffectInterface;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Dice;

import java.util.ArrayList;

public class RollDraftPoolDice  implements EffectInterface{

    private boolean isDone;

    public RollDraftPoolDice(){

    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) {
        ArrayList<Dice> diceList = model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList();
        for(Dice die: diceList){
            die.roll();
        }
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
