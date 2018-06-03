package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.network.server.Server;

public interface VirtualClientInterface {

    String getUsername();

    void setUsername(String username);

    Server getServer();
}
