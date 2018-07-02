package it.polimi.se2018.network.client;

import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.client.rmi.RemoteViewRMI;
import it.polimi.se2018.network.client.socket.RemoteViewSocket;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;
import it.polimi.se2018.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Luciano
 */
public class Client {

    private final static String FILE_ADDRESS = "src\\main\\java\\it\\polimi\\se2018\\in.txt";
    private static RemoteViewRMI remoteViewRMI;
    private static int portSocket = 1111;
    private static String serverIP;
    private static Scanner scanner = new Scanner(new InputStreamReader(System.in));

    public static void main(String args[]){
        FileParser parser = new FileParser();
        boolean correctChoice = false;
        boolean correctInput = false;
        boolean comeback = false;

        RemoteViewSocket remoteViewSocket;
        ClientRMIInterface remoteRef;
        ServerRMIInterface serverRMIInterface;

        int choice = 0;
        String username = null;
        View view = new View();
        while(!correctChoice && !correctInput && view.isStillPlaying()){
            if(!comeback){
                System.out.print("Type 1 to choose RMI connection\nType 2 to choose Socket connection\nType 3 to connect to a previous match\n");
            }else{
                System.out.print("Type 1 to choose RMI connection\nType 2 to choose Socket connection\n");
            }
            try{
                System.out.print("choice: ");
                String dump = scanner.nextLine();
                choice = Integer.parseInt(dump);
                correctInput = true;
            } catch (NumberFormatException e){
                System.out.println("Wrong input!");
                correctInput = false;
            }
            if(correctInput){
                serverIP = parser.readServerIP(FILE_ADDRESS);
                portSocket = parser.readPortSocket(FILE_ADDRESS);
                switch (choice) {
                    case 1: {
                        correctChoice = true;
                        if (!comeback) {
                            try {
                                remoteViewRMI = new RemoteViewRMI();
                                view.addObserver(remoteViewRMI);
                                remoteViewRMI.addObserver(view);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            try {
                                serverRMIInterface = (ServerRMIInterface) Naming.lookup("//" + serverIP + "/ClientGathererRMI");
                                remoteRef = (ClientRMIInterface) UnicastRemoteObject.exportObject(remoteViewRMI, 0);
                                serverRMIInterface = serverRMIInterface.addClient(remoteRef);
                                remoteViewRMI.setServer(serverRMIInterface);
                                System.out.println("RMI connection established");
                                view.createPlayer();
                            } catch (RemoteException | NotBoundException | MalformedURLException e) {
                                System.out.println("Impossible connection! Unable to connect to server!");
                                correctInput = false;
                                correctChoice = false;
                                comeback = false;
                            } catch (PlayerNumberExceededException e) {
                                System.out.println("Player number exceeded");
                            }
                        } else {
                            try {
                                remoteViewRMI = new RemoteViewRMI();
                                view.addObserver(remoteViewRMI);
                                remoteViewRMI.addObserver(view);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            try {
                                serverRMIInterface = (ServerRMIInterface) Naming.lookup("//" + serverIP + "/ClientGathererRMI");
                                remoteRef = (ClientRMIInterface) UnicastRemoteObject.exportObject(remoteViewRMI, 0);
                                serverRMIInterface = serverRMIInterface.retrieveOldClient(remoteRef, username);
                                remoteViewRMI.setServer(serverRMIInterface);
                                System.out.println("RMI connection estabilished");
                            } catch (PlayerNotFoundException e) {
                                System.out.println("Username not found");
                                comeback = false;
                                correctChoice = false;
                            } catch (RemoteException | NotBoundException | MalformedURLException e) {
                                System.out.println("Impossible connection! Unable to connect to server!");
                                correctInput = false;
                                correctChoice = false;
                                comeback = false;
                            }
                        }
                        break;
                    }
                    case 2: {
                        correctChoice = true;
                        if (!comeback) {
                            try {
                                remoteViewSocket = new RemoteViewSocket(serverIP, portSocket);
                                view.addObserver(remoteViewSocket);
                                remoteViewSocket.addObserver(view);
                                System.out.println("Socket connection established");
                                view.createPlayer();
                            } catch (java.net.ConnectException e){
                                System.out.println("Impossible connection! Unable to connect to server!");
                                correctInput = false;
                                correctChoice = false;
                                comeback = false;
                            }
                        } else {
                            try {
                                System.out.println("Comeback with socket, old username: " + username);
                                remoteViewSocket = new RemoteViewSocket(serverIP, portSocket, username);
                                view.addObserver(remoteViewSocket);
                                remoteViewSocket.addObserver(view);
                            } catch (java.net.ConnectException e){
                                System.out.println("Impossible connection! Unable to connect to server!");
                                correctInput = false;
                                correctChoice = false;
                                comeback = false;
                            }
                        }
                        break;
                    }

                    case 3: {
                        correctChoice = false;
                        correctInput = false;
                        System.out.print("Old match username: ");
                        username = scanner.nextLine();
                        comeback = true;
                        break;
                    }

                    default: {
                        System.out.println("Wrong input!");
                        correctChoice = false;
                        correctInput = false;
                    }
                }
            }
        }
        System.out.println("client thread has terminated");
    }
}