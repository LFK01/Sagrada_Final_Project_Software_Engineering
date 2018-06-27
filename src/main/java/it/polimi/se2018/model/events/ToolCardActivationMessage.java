package it.polimi.se2018.model.events;

import it.polimi.se2018.model.events.messages.Message;

public class ToolCardActivationMessage extends Message {

    private String values;
    private String toolCardID;

    public ToolCardActivationMessage(String sender, String recipient, String toolCardID, String values) {
        super(sender, recipient);
        this.values = values;
        this.toolCardID = toolCardID;
    }

    public String getToolCardID() {
        return toolCardID;
    }

    public String getValues() {
        return values;
    }
}
