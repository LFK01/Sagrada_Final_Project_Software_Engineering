package it.polimi.se2018.network.rmi.server;
/**
 * @author Giovanni
 */

import  java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.se2018.network.rmi.Message;
import it.polimi.se2018.network.rmi.MessageNetworkRMI;

public interface ServerRMIInterface extends Remote {

    void addClient(ClientInterface client) throws RemoteException;

    void send(MessageNetworkRMI message) throws RemoteException;
}

