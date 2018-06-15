package it.polimi.se2018.model.game_equipment;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.DiceBag;

import java.util.*;

/**
 * Class to track the extracted dice on the table since the beginning of the round
 * @author Giorgia
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
     * @param participantsNumber participants number, to calculate the exact number of dice to extract (participants*2+1)
     * @param diceBag the match dice bag
     * @param currentRound round number, to track the new set of dice to extract from the dice bag. The new set will
     *                     go from (currentRound-1)*(participantsNumber*2+1) to
     *                     currentRound*(participantsNumber*2+1).
     */
    //si passano al costruttore il numero dei partecipanti e il numero del turno per calcolare i dadi da pescare, più il sacchetto
    public RoundDice(int participantsNumber, DiceBag diceBag, int currentRound) {

        this.participantsNumber = participantsNumber;

        for(int i = 0; i < (this.participantsNumber *2+1); i++) {
            diceList.add(diceBag.getDice(i));  //prendo e rimuovo sempre
            diceBag.getDiceBag().remove(i); //rimuovo il dado dalla dicebag
        }
        this.round = currentRound;

    }

    /**
     * Round Dice getter
     * @return the ArrayList of dice of the current round
     */
    public ArrayList<Dice> getDiceList() {
        return diceList;
    }

    /**
     * Round getter
     * @return the current round
     */
    public int getRound() {
        return round;
    }

    /**
     * Dice getter
     * @param index index of the chosen dice
     * @return the chosen dice
     */
    public Dice getDice(int index) {
        return diceList.get(index);
    }

    public Dice setDice(Dice newDie, int index){
        Dice oldDie = diceList.get(index);
        diceList.remove(oldDie);
        diceList.add(newDie);
        return oldDie;
    }

    /**
     * Participants number getter
     * @return the participants number
     */
    public int getParticipantsNumber() {
        return participantsNumber;
    }

    /**
     * Removes a die from the draft pool after it is placed on a Schema Card
     * @param index indicates the die number that has to be extracted
     */
    public void removeDiceFromDraftPool(int index){
        Dice dieToRemove = diceList.get(index);
        diceList.remove(dieToRemove);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Dice die: diceList){
            builder.append(die.toString());
        }
        return builder.toString();
    }
}
