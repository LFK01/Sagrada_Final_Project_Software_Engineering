package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.ServerSocketInterface;

import java.util.Observable;
import java.util.Observer;

public class RemoteViewSocket extends Observable implements ClientSocketInterface, Observer{

    private ServerSocketInterface server;

    public RemoteViewSocket(String localhost, int port){
        server = new NetworkHandler(localhost, port, this);
    }

    @Override
    public void updateClient(Message message) {
        setChanged();
        notifyObservers(message);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Message){
            server.sendToServer((Message) arg);
        }
    }
}
