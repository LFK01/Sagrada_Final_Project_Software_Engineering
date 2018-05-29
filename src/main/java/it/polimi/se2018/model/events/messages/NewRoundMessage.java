package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.messages.MoveMessage;

public class NewRoundMessage extends Message {

    private Player player;

    public NewRoundMessage(Player player) {
        this.player = player;
    }


    public boolean isError() {
        return false;
    }


    public boolean isNewRound() {
        return false;
    }
}
