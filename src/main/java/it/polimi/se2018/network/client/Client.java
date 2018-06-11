package it.polimi.se2018.network.client;

import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.client.rmi.RemoteViewRMI;
import it.polimi.se2018.network.client.socket.RemoteViewSocket;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;
import it.polimi.se2018.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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

    private static RemoteViewRMI remoteViewRMI;
    private static int portSocket = 1111;
    private static final String host = "localhost";
    private static String serverIP;
    private static Scanner scanner = new Scanner(new InputStreamReader(System.in));

    public static void main(String args[]){
        boolean correctChoice = false;
        boolean correctInput = false;
        boolean comeback = false;

        RemoteViewSocket remoteViewSocket;
        ClientRMIInterface remoteRef;
        ServerRMIInterface serverRMIInterface;

        Scanner inputFile = null;

        try{
            inputFile = new Scanner(new FileInputStream("src\\main\\java\\it\\polimi\\se2018\\in.txt"));
            String line = "";
            boolean hasNextLine = true;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equals("ServerIP:")){
                        serverIP = words[i+1];
                    }
                    if(words[i].trim().equals("PortSocket:")){
                        portSocket = Integer.parseInt(words[i+1]);
                    }
                    i++;
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputFile.close();
        }

        int choice = 0;
        String username = null;
        View view = new View();
        while(!correctChoice && !correctInput){
            if(!comeback){
                System.out.print("Type 1 to choose RMI connection\nType 2 to choose Socket connection\nType 3 to connect to a previous match\nchoice: ");
            }else{
                System.out.print("Type 1 to choose RMI connection\nType 2 to choose Socket connection\nchoice: ");
            }
            try{
                String dump = scanner.nextLine();
                choice = Integer.parseInt(dump);
                correctInput = true;
            } catch (InputMismatchException e){
                System.out.println("Wrong input!");
                correctInput = false;
                scanner.next();
                //TODO handle exception
            }
            if(correctInput){
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
                                System.out.println("RMI connection estabilished");
                                view.createPlayer();
                            } catch (RemoteException | NotBoundException | MalformedURLException e) {
                                e.printStackTrace();
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
                                e.printStackTrace();
                            }
                        }
                        break;
                    }

                    case 2: {
                        correctChoice = true;
                        if (!comeback) {
                            remoteViewSocket = new RemoteViewSocket(host, portSocket);
                            view.addObserver(remoteViewSocket);
                            remoteViewSocket.addObserver(view);
                            System.out.println("Socket connection estabilished");
                            view.createPlayer();
                        } else {
                            System.out.println("Comeback with socket, old username: " + username);
                            remoteViewSocket = new RemoteViewSocket(serverIP, portSocket, username);
                            view.addObserver(remoteViewSocket);
                            remoteViewSocket.addObserver(view);
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
    }
}
