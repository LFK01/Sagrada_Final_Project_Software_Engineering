package it.polimi.se2018.model.events;

import it.polimi.se2018.model.events.messages.Message;

public class ToolCardActivationMessage extends Message {

    String values;
    String toolCardName;

    public ToolCardActivationMessage(String sender, String recipient, String toolCardName, String values) {
        super(sender, recipient);
        this.values = values;
        this.toolCardName = toolCardName;
    }

    public String getToolCardName() {
        return toolCardName;
    }

    public String getValues() {
        return values;
    }
}
