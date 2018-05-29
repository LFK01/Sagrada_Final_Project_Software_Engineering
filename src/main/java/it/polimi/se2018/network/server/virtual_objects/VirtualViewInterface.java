package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.Message;

import java.util.Observer;

public interface VirtualViewInterface extends Observer{

    void sendToClient(Message message);

    void sendToServer(Message message);
}
