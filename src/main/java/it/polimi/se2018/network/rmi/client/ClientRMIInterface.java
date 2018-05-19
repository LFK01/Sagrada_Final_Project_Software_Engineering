package it.polimi.se2018.network.rmi.client;

import it.polimi.se2018.network.rmi.MessageNetworkRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientRMIInterface extends Remote {

    public void notify(MessageNetworkRMI message) throws RemoteException;
}
