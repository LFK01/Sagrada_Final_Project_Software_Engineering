package it.polimi.se2018.model.events;

import it.polimi.se2018.model.events.messages.Message;

public class ChangeDieValueMessage extends Message {

    int position;
    String toolCardName;

    public ChangeDieValueMessage(String sender, String recipient, String toolCardName, int position) {
        super(sender, recipient);
        this.position = position;
        this.toolCardName = toolCardName;
    }

    public String getToolCardName() {
        return toolCardName;
    }

    public int getPosition() {
        return position;
    }
}
