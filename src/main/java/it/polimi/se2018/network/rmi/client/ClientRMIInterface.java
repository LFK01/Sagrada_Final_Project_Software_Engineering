package it.polimi.se2018.network.rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientRMIInterface extends Remote {

    public void notify(Message message) throws RemoteException;
}
