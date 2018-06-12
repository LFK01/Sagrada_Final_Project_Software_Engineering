package it.polimi.se2018.model.events.moves;

import it.polimi.se2018.model.events.messages.Message;

/**
 * @author Luciano
 */

public class UseToolCardMove extends Message {

    private String toolCardName;

    public UseToolCardMove(String sender, String recipient, String toolCardName) {
        super(sender, recipient);
        this.toolCardName = toolCardName;
    }

    public String getToolCardName() {
        return toolCardName;
    }

}
