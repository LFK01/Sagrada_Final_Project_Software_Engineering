package it.polimi.se2018.network.client;

import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.client.rmi.RemoteViewRMI;
import it.polimi.se2018.network.client.socket.RemoteViewSocket;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
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

    private static final String ERROR_QUOTE = "Wrong input!";
    private View view;
    private RemoteViewRMI remoteViewRMI;
    private RemoteViewSocket remoteViewSocket;
    private ClientRMIInterface remoteRef;
    private ServerRMIInterface serverRMIInterface;
    private int portSocket = 1111;
    private String serverIP;
    private Scanner scanner = new Scanner(new InputStreamReader(System.in));

    private boolean connectionEstablished;

    public Client() {
        connectionEstablished = false;
        boolean gameHasEnded = false;

        view = new View();
        view.setServerIsUp(false);

        while(!gameHasEnded){
            //System.out.println("gameHasNotEnded");
            if(view.isServerUp()){
                if(!view.isPlayerBanned()){
                    //System.out.println("PlayerIsNotBanned");
                    gameHasEnded = view.hasGameEnded();
                } else {
                    //System.out.println("PlayerHasBeenBanned");
                    if (view.playerWantsToContinue()){
                        //System.out.println("PlayerWantsToContinue");
                        doComeBackConnection();
                    } else {
                        //System.out.println("PlayerWantsToQuit");
                        gameHasEnded = true;
                    }
                }
            } else {
                //System.out.println("ServerIsNotUp");
                establishConnection();
                gameHasEnded = view.hasGameEnded();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ThreadClientHasTerminated");
        System.exit(0);
    }

    private void establishConnection(){
        int choice;
        connectionEstablished = false;
        while (!connectionEstablished){
            readPortsFromFile();
            System.out.println("Type 1 to choose RMI connection" +
                    "\nType 2 to choose Socket connection" +
                    "\nType 3 to connect to a previous match");
            choice = readChoiceBetweenValues(1, 3, "Choice: ");
            switch (choice){
                case 1:{
                    setupRMIConnection();
                    view.createPlayer();
                    break;
                }
                case 2:{
                    setupSocketConnection();
                    view.createPlayer();
                    break;
                }
                case 3:{
                    doComeBackConnection();
                    break;
                }
            }
        }
        view.setServerIsUp(true);
    }

    private void readPortsFromFile() {
        FileParser parser = new FileParser();
        serverIP = parser.readServerIP(Server.INPUT_FILE_ADDRESS);
        portSocket = parser.readPortSocket(Server.INPUT_FILE_ADDRESS);
    }

    private void setupRMIConnection() {
        try{
            remoteViewRMI = new RemoteViewRMI();
            view.addObserver(remoteViewRMI);
            remoteViewRMI.addObserver(view);
            serverRMIInterface = (ServerRMIInterface) Naming.lookup("//" + serverIP + "/ClientGathererRMI");
            remoteRef = (ClientRMIInterface) UnicastRemoteObject.exportObject(remoteViewRMI, 0);
            serverRMIInterface = serverRMIInterface.addClient(remoteRef);
            remoteViewRMI.setServer(serverRMIInterface);
            System.out.println("RMI connection established");
            connectionEstablished = true;
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            System.out.println("Impossible connection! Unable to connect to server!");
            connectionEstablished = false;
        } catch (PlayerNumberExceededException e) {
            System.out.println("Player number exceeded");
            connectionEstablished = false;
        }
    }

    private void setupSocketConnection() {
        try {
            remoteViewSocket = new RemoteViewSocket(serverIP, portSocket);
            view.addObserver(remoteViewSocket);
            remoteViewSocket.addObserver(view);
            System.out.println("Socket connection established");
            connectionEstablished = true;
        } catch (java.net.ConnectException e){
            System.out.println("Impossible connection! Unable to connect to server!");
            connectionEstablished = false;
        }
    }

    private void doComeBackConnection() {
        int choice;
        while (!connectionEstablished){
            readPortsFromFile();
            System.out.println("Type 1 to choose RMI connection\n" +
                    "Type 2 to choose Socket connection");
            choice = readChoiceBetweenValues(1, 2, "Choice: ");
            switch (choice){
                case 1:{
                    setupRMIConnection();
                    break;
                }
                case 2:{
                    setupSocketConnection();
                    break;
                }
            }
        }
        view.askOldUsername();
    }

    private int readChoiceBetweenValues(int firstValue, int secondValue, String inputInstruction){
        int choice = -1;
        String input;
        boolean wrongInput = true;
        while (wrongInput){
            System.out.print(inputInstruction);
            input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if(choice<firstValue || choice>secondValue){
                    System.out.println(ERROR_QUOTE);
                    wrongInput = true;
                } else {
                    return choice;
                }
            } catch (NumberFormatException e){
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        return choice;
    }

    public static void main(String args[]){
        new Client();
    }
}