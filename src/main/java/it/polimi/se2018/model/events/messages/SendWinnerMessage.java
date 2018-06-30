package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;

/**
 * @author giovanni
 */
public class SendWinnerMessage extends Message {
    private ArrayList<Player> participants;

    public SendWinnerMessage(String sender, String recipient,ArrayList<Player> participants) {
        super(sender, recipient);
        this.participants = participants;

    }

    public ArrayList<Player> getParticipants() {
        return participants;
    }
}
