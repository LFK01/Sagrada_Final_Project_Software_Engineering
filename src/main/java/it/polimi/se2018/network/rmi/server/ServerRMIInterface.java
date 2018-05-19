package it.polimi.se2018.network.rmi.server;
/**
 * @author Giovanni
 */

import  java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.se2018.network.rmi.MessageNetworkRMI;
import it.polimi.se2018.network.rmi.MessageNetworkRMI;
import it.polimi.se2018.network.rmi.client.ClientRMI;
import it.polimi.se2018.network.rmi.client.ClientRMIInterface;


public interface ServerRMIInterface extends Remote {

    void addClient(ClientRMIInterface client) throws RemoteException;

    void send(MessageNetworkRMI message) throws RemoteException;
}

