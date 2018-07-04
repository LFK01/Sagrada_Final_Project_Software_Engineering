package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;

/**
 * @author giovanni
 */
public class SendWinnerMessage extends Message {
    private ArrayList<String> ranking;
    private ArrayList<Integer> score;


    public SendWinnerMessage(String sender, String recipient,ArrayList<String> ranking,ArrayList<Integer> score) {
        super(sender, recipient);
        this.ranking = ranking;
        this.score = score;

    }

    public ArrayList<String> getParticipants() {
        return ranking;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }
}
