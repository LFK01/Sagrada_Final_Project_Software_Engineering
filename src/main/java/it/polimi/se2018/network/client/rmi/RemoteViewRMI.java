package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.model.events.messages.SuccessMoveMessage;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.view.View;

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
        if(arg instanceof String){
            server.sendToServer();
        }else{
            if(arg instanceof Message){
                server.sendToServer((Message) arg);
            }else{
                System.out.println("Errore Inserimento Parametri");
            }
        }
    }

    public void setServer(ServerRMIInterface server) {
        this.server = server;
    }
}
