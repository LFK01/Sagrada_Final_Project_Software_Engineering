package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.messages.MoveMessage;

/**
 * @author Luciano
 */

public class SuccessMoveMessage extends Message {

    private GameBoard gameBoard;
    private Player player;

    public SuccessMoveMessage(Player player, GameBoard gameBoard) {
        this.player = player;
        this.gameBoard = gameBoard;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getPlayer() {
        return player;
    }

    //forse sono inutili
    public boolean isError() {
        return false;
    }

    public boolean isNewRound() {
        return false;
    }
}
