package it.polimi.se2018.model.game_equipment;

import it.polimi.se2018.model.Model;

/**
 * @author Giorgia
 * modified Luciano 13/05/2018
 * added method getRoundNumber
 */
public class RoundTrack {

    private int currentRound;/*integer value of the current Round*/

    private RoundDice[] roundDice;
    /*array of RoundDice where dice left from the draft pool
     are saved*/

    /**
     * constructor method initializing currentRound and
     */
    public RoundTrack() {
        this.currentRound = 0;
        roundDice = new RoundDice[Model.MAXIMUM_ROUND_NUMBER];
    }

    /**
     *
     * @return integer value of the currentRound number
     */
    public int getCurrentRound() {
        return currentRound;
    }

    //currentRoundDice rappresenta la lista di dadi "avanzati" al termine del turno
    public void setRoundDice(RoundDice currentRoundDice, int currentRound) {
        this.roundDice[currentRound]=currentRoundDice;
        this.currentRound = currentRound;
    }

    public RoundDice[] getRoundDice() {
        return roundDice;
    }

    public Dice getDie(int index) {
        return roundDice[this.currentRound].getDice(index);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<roundDice.length; i++){
            builder.append("Round #").append(i).append(" 1: ").append(roundDice[i].getDiceList().toString());
        }
        return builder.toString();
    }
}