package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Dice;

import java.util.ArrayList;

public class RollDraftPoolDice implements TCEffectInterface {

    private boolean isDone;

    public RollDraftPoolDice() {
        this.isDone = false;
    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) {
        System.out.println("RollDraftPoolDice is working");
        ArrayList<Dice> diceList = model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList();
        for(Dice die: diceList){
            die.roll();
        }
        System.out.println("rolled dice");
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
