package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.Message;

import java.util.Observable;
import java.util.Observer;

public class VirtualViewSocket extends Observable implements VirtualViewInterface {

    private VirtualClientSocket clientSocket;

    public VirtualViewSocket(VirtualClientSocket clientSocket){
        this.clientSocket = clientSocket;
    }


    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Message){
            notifyClient((Message) arg);
        }
    }

    @Override
    public void notifyClient(Message message) {
        clientSocket.notifyClient(message);
    }

    @Override
    public void updateServer(Message message) {
        setChanged();
        notifyObservers(message);
    }
}
