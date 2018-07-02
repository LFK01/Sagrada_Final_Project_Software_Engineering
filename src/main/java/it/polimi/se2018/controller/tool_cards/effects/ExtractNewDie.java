package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Dice;

import java.util.ArrayList;
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
            if(words[i].trim().equalsIgnoreCase("draftPoolDiePosition:")){
                diePosition = Integer.parseInt(words[i+1]);
                System.out.println("read die position: " + diePosition);
                if(diePosition<0 ||
                        diePosition>model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().size()){
                    throw new ExecutingEffectException();
                }
            }
        }
        ArrayList<Dice> draftPool = model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList();
        Dice replacingDie = draftPool.get(diePosition);
        draftPool.remove(replacingDie);
        ArrayList<Dice> diceBag = model.getGameBoard().getDiceBag().getDiceList();
        diceBag.add(replacingDie);
        System.out.println("added die: " + replacingDie.toString() + " to dice bag");
        int randomDie = new Random().nextInt(model.getGameBoard().getDiceBag().getDiceList().size()-1);
        Dice newDie = diceBag.get(randomDie);
        diceBag.remove(newDie);
        System.out.println("drafted new die: " + newDie.toString());
        draftPool.add(diePosition, newDie);
        isDone = true;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void setDone(boolean b) {
        isDone =b;
    }
}
