package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Player;

/**
 *@author Luciano
 */
public abstract class MoveMessage {

    private Player player;

    public MoveMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract boolean isError();

    public abstract boolean isNewRound();
}
