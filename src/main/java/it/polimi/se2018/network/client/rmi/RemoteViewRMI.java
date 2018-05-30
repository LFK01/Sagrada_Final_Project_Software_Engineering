package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.ServerRMIInterface;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public class RemoteViewRMI extends Observable implements ClientRMIInterface, Observer {

    private ServerRMIInterface server;

    public RemoteViewRMI() throws RemoteException{
    }

    @Override
    public void updateClient(Message message) throws RemoteException {
        setChanged();
        notifyObservers(message);
    }


    @Override
    public void update(Observable o, Object arg) {
        try{
            server.sendToServer((Message)arg);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void setServer(ServerRMIInterface server) {
        this.server = server;
    }
}
