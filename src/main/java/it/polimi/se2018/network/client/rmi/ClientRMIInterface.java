package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.model.events.moves.NoActionMove;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRMIInterface extends Remote{

    void updateClient(Message message) throws RemoteException;


}
