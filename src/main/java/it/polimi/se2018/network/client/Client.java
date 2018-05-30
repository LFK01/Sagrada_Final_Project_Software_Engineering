package it.polimi.se2018.network.client;

import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.client.rmi.RemoteViewRMI;
import it.polimi.se2018.network.client.socket.RemoteViewSocket;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.client_gatherer.ClientGathererRMI;
import it.polimi.se2018.view.View;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client {

    private static View view;
    private static ClientGathererRMI clientGathererRMI;
    private static RemoteViewRMI remoteViewRMI;
    private static ServerRMIInterface serverRMIInterface;
    private static RemoteViewSocket remoteViewSocket;
    private static final int PORT=1111;
    private static final String host = "localhost";
    //per testing

    private static Scanner input = new Scanner(System.in);

    public static void main(String args[]){
        view = new View();
        int choiceGUI = view.demandConnectionType();
        int choice =0;

        System.out.println("1 RMI o 2 Socket");
        choice = input.nextInt();

        //per provare la connessione


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
                System.out.println("collegato");
            } catch (RemoteException e){
                e.printStackTrace();
            } catch (NotBoundException e){
                e.printStackTrace();
            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            view.createPlayer();
        }
        else{
            remoteViewSocket = new RemoteViewSocket(host, PORT);
            view.addObserver(remoteViewSocket);
            remoteViewSocket.addObserver(view);
            view.createPlayer();
        }
    }
}
