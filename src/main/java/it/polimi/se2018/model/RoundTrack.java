package it.polimi.se2018.model;

/**
 * @author Giorgia
 * modified Luciano 13/05/2018
 * added method getRoundNumber
 */
public class RoundTrack {


    private int currentRound;/*integer value of the current Round*/

    private RoundDice[] roundDice = new RoundDice[10];
    /*array of RoundDice where dice left from the draft pool
     are saved*/

    /**
     * constructor method initializing currentRound and
     */
    public RoundTrack() {
        this.currentRound = 0;
        roundDice = new RoundDice[10];
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
}