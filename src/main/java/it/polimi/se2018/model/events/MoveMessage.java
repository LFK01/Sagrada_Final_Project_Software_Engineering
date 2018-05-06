package it.polimi.se2018.model.events;
//Luciano

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Player;

public class MoveMessage {
    private Player player;
    private GameBoard gameBoard;

    public MoveMessage(Player player, GameBoard gameBoard) {
        this.player = player;
        this.gameBoard = gameBoard;
    }

    public Player getPlayer() {
        return player;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
