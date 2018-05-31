package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMIInterface extends Remote{

    ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException;

    void sendToServer(Message message) throws RemoteException;

    void updateClient(Message message) throws RemoteException;

}
