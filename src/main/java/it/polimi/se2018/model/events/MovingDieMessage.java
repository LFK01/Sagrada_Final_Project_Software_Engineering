package it.polimi.se2018.model.events;

import it.polimi.se2018.model.events.messages.Message;

public class MovingDieMessage extends Message {

    private int[] positions;

    public MovingDieMessage(String username, String recipient, int[] positions, String toolCardName) {
        super(username, recipient);
        this.positions = positions;
    }

    public int[] getPositions() {
        return positions;
    }


}
