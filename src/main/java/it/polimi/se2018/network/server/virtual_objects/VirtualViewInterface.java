package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.utils.ProjectObserver;

import java.net.Socket;
import java.util.Observer;

public interface VirtualViewInterface extends ProjectObserver{

    String getUsername();

    void setClientConnection(Socket clientConnection);
}
