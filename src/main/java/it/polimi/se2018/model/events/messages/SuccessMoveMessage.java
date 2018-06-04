package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.game_equipment.GameBoard;
import it.polimi.se2018.model.game_equipment.Player;

/**
 * @author Luciano
 */

public class SuccessMoveMessage extends Message {

    private GameBoard gameBoard;
    private Player player;

    public SuccessMoveMessage( String sender, String recipient,GameBoard gameBoard) {
        super(sender, recipient);
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
