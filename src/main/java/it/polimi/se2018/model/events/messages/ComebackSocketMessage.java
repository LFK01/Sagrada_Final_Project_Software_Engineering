package it.polimi.se2018.model.events.messages;

public class ComebackSocketMessage extends ComebackMessage {

    public ComebackSocketMessage(String sender, String recipient, String oldUsername) {
        super(sender, recipient, oldUsername);
    }

}
