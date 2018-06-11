package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.game_equipment.GameBoard;
import it.polimi.se2018.model.game_equipment.Player;

/**
 * @author Luciano
 */

public class SuccessMoveMessage extends Message {

    public SuccessMoveMessage( String sender, String recipient) {
        super(sender, recipient);

    }
}
