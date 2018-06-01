package it.polimi.se2018.model.events.messages;

import java.net.Socket;

public class ComebackSocketMessage extends ComebackMessage {

    Socket newClientConnection;

    public ComebackSocketMessage(String sender, String recipient, String oldUsername, Socket newClientConnection) {
        super(sender, recipient, oldUsername);
        this.newClientConnection = newClientConnection;
    }

    public Socket getNewClientConnection() {
        return newClientConnection;
    }
}
