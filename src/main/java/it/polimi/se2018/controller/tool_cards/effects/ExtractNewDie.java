package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.EffectInterface;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Dice;

import java.util.Random;

public class ExtractNewDie implements EffectInterface {

    boolean isDone = false;

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) {
        String[] words = values.split(" ");
        int diePosition = -1;
        for(int i = 0; i<words.length; i++){
            if(words[i].trim().equalsIgnoreCase("DiePosition:")){
                diePosition = Integer.parseInt(words[i+1]);
            }
        }
        Dice replacingDie = model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDice(diePosition);
        model.getGameBoard().getDiceBag().getDiceBag().add(replacingDie);
        int randomDie = new Random().nextInt(model.getGameBoard().getDiceBag().getDiceBag().size());
        Dice newDie = model.getGameBoard().getDiceBag().getDiceBag().get(randomDie);
        model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().add(newDie);
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void setDone(boolean b) {

    }
}
