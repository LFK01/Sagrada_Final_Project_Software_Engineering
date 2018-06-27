package it.polimi.se2018.model.events.moves;

import it.polimi.se2018.model.events.messages.Message;

/**
 * @author Luciano
 */

public class UseToolCardMove extends Message {

    private String toolCardID;

    public UseToolCardMove(String sender, String recipient, String toolCardID) {
        super(sender, recipient);
        this.toolCardID = toolCardID;
    }

    public String getToolCardID() {
        return toolCardID;
    }

}
