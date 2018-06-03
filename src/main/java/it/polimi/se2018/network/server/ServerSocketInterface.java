package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.messages.Message;

import java.net.SocketAddress;

public interface ServerSocketInterface {

    void sendToServer(Message message);

    String getAddress();
}
