package it.polimi.se2018.model.events.messages;

public class SendSchemaUpdateMessage extends Message {
    public SendSchemaUpdateMessage(String sender, String recipient) {
        super(sender, recipient);
    }

}
