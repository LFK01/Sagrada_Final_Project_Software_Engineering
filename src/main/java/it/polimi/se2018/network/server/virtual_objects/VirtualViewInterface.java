package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.Message;
import java.net.Socket;
import java.util.Observer;

public interface VirtualViewInterface extends Observer{

    String getUsername();

    void setClientConnection(Socket clientConnection);
}
