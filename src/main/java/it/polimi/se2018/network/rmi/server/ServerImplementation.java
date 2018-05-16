package it.polimi.se2018.network.rmi.server;

//import rmi.client.ClientInterface;

import it.polimi.se2018.network.rmi.MessageNetworkRMI;

        import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerImplementation {
    public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {

        private ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();

        protected ServerImplementation() throws RemoteException {
            super(0);
        }

        private static final long serialVersionUID = -7098548671967083832L;

        public void addClient(ClientInterface client) throws RemoteException {
            clients.add(client);
            System.out.println("Client "+ (clients.indexOf(client)+1) + " connesso!");
        }

        public void send(MessageNetworkRMI message) throws RemoteException {
            Iterator<ClientInterface> clientIterator = clients.iterator();
            while(clientIterator.hasNext()){
                try{
                    clientIterator.next().notify(message);
                }catch(ConnectException e) {
                    clientIterator.remove();
                    System.out.println("Client rimosso!");
                }
            }
        }






}
