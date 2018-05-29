package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class ErrorMessage extends Message {

    private  String message;
    private Player player;

    public ErrorMessage(Player player, String message) {
        this.player = player;
        this.message = message;
    }


    public boolean isError() {
        return true;
    }


    public boolean isNewRound() {
        return false;
    }

    @Override
    public String toString() {
        return message;
    }
}
