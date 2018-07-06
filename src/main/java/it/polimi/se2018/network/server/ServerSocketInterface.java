package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.messages.Message;

import java.io.IOException;

public interface ServerSocketInterface {

    void sendToServer(Message message) throws IOException;

}
