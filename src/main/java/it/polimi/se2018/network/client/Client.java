package it.polimi.se2018.network.client;

import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.client.rmi.RemoteViewRMI;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.client_gatherer.ClientGathererRMI;
import it.polimi.se2018.view.View;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client {

    private static View view;
    private static ClientGathererRMI clientGathererRMI;
    private static RemoteViewRMI remoteViewRMI;
    private static ServerRMIInterface serverRMIInterface;

    public static void main(String args[]){
        view = new View();
        int choice = view.demandConnectionType();
        if(choice == 1){
            try {
                remoteViewRMI = new RemoteViewRMI();
                view.addObserver(remoteViewRMI);
                remoteViewRMI.addObserver(view);
            }catch (RemoteException e){
                e.printStackTrace();
            }
            try{
                serverRMIInterface = (ServerRMIInterface) Naming.lookup("//localhost/ClientGathererRMI");
                ClientRMIInterface remoteRef = (ClientRMIInterface) UnicastRemoteObject.exportObject(remoteViewRMI, 0);
                serverRMIInterface = serverRMIInterface.addClient(remoteRef);
                remoteViewRMI.setServer(serverRMIInterface);
            } catch (RemoteException e){
                e.printStackTrace();
            } catch (NotBoundException e){
                e.printStackTrace();
            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            view.startRMIConnection();
        }
        else{
            view.startSocketConnection();
        }
    }
}
