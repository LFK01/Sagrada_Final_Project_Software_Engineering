package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.messages.MoveMessage;

/**
 * @author Luciano
 */

public class SuccessMessage extends MoveMessage {

    private final String message;

    public SuccessMessage(Player player, String message) {
        super(player);
        this.message=message;
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public boolean isNewRound() {
        return false;
    }
}
