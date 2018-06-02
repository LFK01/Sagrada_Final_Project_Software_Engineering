package it.polimi.se2018.network.client;

import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.client.rmi.RemoteViewRMI;
import it.polimi.se2018.network.client.socket.RemoteViewSocket;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;
import it.polimi.se2018.view.View;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * @author Luciano
 */
public class Client {

    private static RemoteViewRMI remoteViewRMI;
    private static final int PORT = 1111;
    private static final String host = "localhost";

    private static Scanner scanner = new Scanner(new InputStreamReader(System.in));

    public static void main(String args[]){
        boolean correctChoice = false;
        boolean comeback = false;
        String username = null;
        View view = new View();
        /*while(!correctChoice){
            if(!comeback){
                System.out.println("1 RMI 2 Socket 3 Comeback");
            }else{
                System.out.println("1 RMI 2 Socket");
            }*/
            //int choice = scanner.nextInt();
        int choice =0;
        while(choice ==0) {
                 choice = view.chooseConnectionWindow();
            }
            switch (choice){

                case 1:{
                    correctChoice = true;
                    ClientRMIInterface remoteRef;
                    ServerRMIInterface serverRMIInterface;
                    if(!comeback){
                        try {
                            remoteViewRMI = new RemoteViewRMI();
                            view.addObserver(remoteViewRMI);
                            remoteViewRMI.addObserver(view);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
                        try{
                            serverRMIInterface = (ServerRMIInterface) Naming.lookup("//localhost/ClientGathererRMI");
                            remoteRef = (ClientRMIInterface) UnicastRemoteObject.exportObject(remoteViewRMI, 0);
                            serverRMIInterface = serverRMIInterface.addClient(remoteRef);
                            remoteViewRMI.setServer(serverRMIInterface);
                        } catch (RemoteException | NotBoundException | MalformedURLException | PlayerNumberExceededException e){
                            e.printStackTrace();
                        }
                        view.createPlayer();
                    }else{
                        try{
                            serverRMIInterface = (ServerRMIInterface) Naming.lookup("//localhost/ClientGathererRMI");
                            remoteRef = (ClientRMIInterface) UnicastRemoteObject.exportObject(remoteViewRMI, 0);
                            serverRMIInterface = serverRMIInterface.retrieveOldClient(remoteRef, username);
                            remoteViewRMI.setServer(serverRMIInterface);
                        } catch (PlayerNotFoundException e) {
                            System.out.println("Username not found");
                            comeback = false;
                            correctChoice = false;
                        } catch (RemoteException | NotBoundException | MalformedURLException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                }

                case 2: {
                    correctChoice = true;
                    RemoteViewSocket remoteViewSocket;
                    if (!comeback) {
                        System.out.print("Not Comeback. ");
                        remoteViewSocket = new RemoteViewSocket(host, PORT);
                        view.addObserver(remoteViewSocket);
                        remoteViewSocket.addObserver(view);
                        view.createPlayer();
                    } else {
                        remoteViewSocket = new RemoteViewSocket(host, PORT, username);
                        view.addObserver(remoteViewSocket);
                        remoteViewSocket.addObserver(view);
                    }
                    break;
                }

                case 3:{
                    correctChoice = false;
                    System.out.print("Old match username: ");
                    username = scanner.nextLine();
                    comeback = true;
                    break;
                }

                default: correctChoice = false;
            }

    }
}
