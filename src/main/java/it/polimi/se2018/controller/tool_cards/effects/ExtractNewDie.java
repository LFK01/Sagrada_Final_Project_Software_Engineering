package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Dice;

import java.util.Random;

public class ExtractNewDie implements TCEffectInterface {

    boolean isDone;

    public ExtractNewDie() {
        this.isDone = false;
    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) throws ExecutingEffectException {
        System.out.println("extractNewDie is working");
        String[] words = values.split(" ");
        int diePosition = -1;
        for(int i = 0; i<words.length; i++){
            if(words[i].trim().equalsIgnoreCase("DiePosition:")){
                diePosition = Integer.parseInt(words[i+1]);
                System.out.println("read die position: " + diePosition);
                if(diePosition<0 ||
                        diePosition>model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().size()){
                    throw new ExecutingEffectException();
                }
            }
        }
        Dice replacingDie = model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDice(diePosition);
        model.getGameBoard().getDiceBag().getDiceBag().add(replacingDie);
        System.out.println("added die: " + replacingDie.toString() + " to dice bag");
        int randomDie = new Random().nextInt(model.getGameBoard().getDiceBag().getDiceBag().size());
        Dice newDie = model.getGameBoard().getDiceBag().getDiceBag().get(randomDie);
        System.out.println("drafted new die: " + newDie.toString());
        model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().add(newDie);
        isDone = true;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void setDone(boolean b) {

    }
}
