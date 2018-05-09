package it.polimi.se2018.model.events;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class ErrorMessage extends MoveMessage {

    private final String message;

    public ErrorMessage(Player player, String message) {
        super(player);
        this.message = message;
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public boolean isNewRound() {
        return false;
    }

    @Override
    public String toString() {
        return message;
    }
}
