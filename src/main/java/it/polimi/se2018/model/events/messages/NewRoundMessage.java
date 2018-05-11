package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.messages.MoveMessage;

public class NewRoundMessage extends MoveMessage {

    public NewRoundMessage(Player player) {
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
