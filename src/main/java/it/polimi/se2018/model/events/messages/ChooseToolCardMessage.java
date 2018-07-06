package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.events.messages.Message;

/**
 * @author Luciano
 * Message to request activation of a tool Card
 */

public class ChooseToolCardMessage extends Message {

    private String toolCardID;

    public ChooseToolCardMessage(String sender, String recipient, String toolCardID) {
        super(sender, recipient);
        this.toolCardID = toolCardID;
    }

    public String getToolCardID() {
        return toolCardID;
    }

}
