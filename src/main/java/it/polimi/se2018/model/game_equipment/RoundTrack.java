package it.polimi.se2018.model.game_equipment;

import it.polimi.se2018.model.Model;

/**
 * @author Giovanni
 * A class containing a round array,
 * each round has an arrayList of dice whose size varies according to the number of players
 *
 */
public class RoundTrack {

    private RoundDice[] roundDice;
    /*array of RoundDice where dice left from the draft pool
     are saved*/

    /**
     * constructor method initializing currentRound and
     */
    public RoundTrack() {
        roundDice = new RoundDice[Model.MAXIMUM_ROUND_NUMBER];
    }

    public RoundDice[] getRoundDice() {
        return roundDice;
    }

    /**
     * Create a string of RoundTrack to be able to send it by message
     * @return string of roundTrack
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<roundDice.length; i++){
            builder.append("Round #").append(i).append(" 1: ").append(roundDice[i].getDiceList().toString());
        }
        return builder.toString();
    }
}