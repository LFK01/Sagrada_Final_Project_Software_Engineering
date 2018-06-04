package it.polimi.se2018.model.events.messages;

import java.net.Socket;

public class ComebackSocketMessage extends ComebackMessage {

    public ComebackSocketMessage(String sender, String recipient, String oldUsername) {
        super(sender, recipient, oldUsername);
    }

}
