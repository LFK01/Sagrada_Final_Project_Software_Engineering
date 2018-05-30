package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.Player;

public class NewRoundMessage extends Message {

    private Player player;

    public NewRoundMessage(String sender, String recipient) {
        super(sender,recipient);
    }


    public boolean isError() {
        return false;
    }


    public boolean isNewRound() {
        return false;
    }
}
