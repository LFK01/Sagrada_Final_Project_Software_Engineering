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
        boolean correctInput = false;
        boolean comeback = false;
        RemoteViewSocket remoteViewSocket;
        ClientRMIInterface remoteRef;
        ServerRMIInterface serverRMIInterface;
        int choice = 0;
        String username = null;
        View view = new View();
        while(!correctChoice){
            if(!comeback){
                System.out.println("1 RMI 2 Socket 3 Comeback");
            }else{
                System.out.println("1 RMI 2 Socket");
            }
            while(!correctInput){
                try{
                    choice = scanner.nextInt();
                    correctInput = true;
                } catch (InputMismatchException e){
                    correctInput = false;
                }
            }
            switch (choice){

                case 1:{
                    correctChoice = true;
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
                            System.out.println("RMI connection estabilished");
                            view.createPlayer();
                        } catch (RemoteException | NotBoundException | MalformedURLException e){
                            e.printStackTrace();
                        } catch (PlayerNumberExceededException e){
                            System.out.println("Player number exceeded");
                        }
                    }else{
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
                            serverRMIInterface = serverRMIInterface.retrieveOldClient(remoteRef, username);
                            remoteViewRMI.setServer(serverRMIInterface);
                            System.out.println("RMI connection estabilished");
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
                    if (!comeback) {
                        remoteViewSocket = new RemoteViewSocket(host, PORT);
                        view.addObserver(remoteViewSocket);
                        remoteViewSocket.addObserver(view);
                        System.out.println("Socket connection estabilished");
                        view.createPlayer();
                    } else {
                        remoteViewSocket = new RemoteViewSocket(host, PORT, username);
                        view.addObserver(remoteViewSocket);
                        remoteViewSocket.addObserver(view);
                    }
                    break;
                }

                case 3:{
                    String empty = scanner.nextLine();
                    correctChoice = false;
                    correctInput = false;
                    System.out.print("Old match username: ");
                    username = scanner.nextLine();
                    comeback = true;
                    break;
                }

                default: {
                    correctChoice = false;
                }

            }
        }
    }
}
