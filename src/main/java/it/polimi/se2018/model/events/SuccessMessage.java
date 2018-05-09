package it.polimi.se2018.model.events;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class SuccessMessage extends MoveMessage {

    public SuccessMessage(Player player) {
        super(player);
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
