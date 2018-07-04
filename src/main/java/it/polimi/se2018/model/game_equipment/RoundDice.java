package it.polimi.se2018.model.game_equipment;

import java.util.*;

/**
 * Class to track the extracted dice on the table since the beginning of the round
 * @author Giovanni
 *
 * edited Luciano 14/05/2018
 */

public class RoundDice {

    private ArrayList<Dice> diceList = new ArrayList<>();
    private int participantsNumber;
    private int round;

    /**
     * Class constructor. Given the dice bag, the participants number and the round number, it creates an ArrayList
     * containing the extracted dice in that specific round.
     * @param diceBag the match dice bag
     */
    public RoundDice(DiceBag diceBag) {
        for(int i = 0; i < (this.participantsNumber *2+1); i++) {
            diceList.add(diceBag.getDice(i));
            diceBag.getDiceList().remove(i);
        }
    }

    /**
     * Round Dice getter
     * @return the ArrayList of dice of the current round
     */
    public ArrayList<Dice> getDiceList() {
        return diceList;
    }

    /**
     * Dice getter
     * @param index index of the chosen dice
     * @return the chosen dice
     */
    public Dice getDice(int index) {
        System.out.println();
        return diceList.get(index);
    }

    public Dice setDice(Dice newDie, int index){
        Dice oldDie = diceList.get(index);
        diceList.remove(oldDie);
        diceList.add(newDie);
        return oldDie;
    }

    /**
     * Removes a die from the draft pool after it is placed on a Schema Card
     * @param index indicates the die number that has to be extracted
     */
    public Dice removeDiceFromList(int index){
        Dice dieToRemove = diceList.get(index);
        diceList.remove(dieToRemove);
        return dieToRemove;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Dice die: diceList){
            builder.append(die.toString()).append(" ");
        }
        return builder.toString();
    }
}
