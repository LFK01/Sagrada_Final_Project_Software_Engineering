package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.messages.Message;

public interface ServerSocketInterface {

    void sendToServer(Message message);

    String getAddress();
}
