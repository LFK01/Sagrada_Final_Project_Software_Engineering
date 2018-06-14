package it.polimi.se2018.network.server;
/**
 * @author Luciano
 */

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.network.server.client_gatherer.ClientGathererRMI;
import it.polimi.se2018.network.server.client_gatherer.ClientGathererSocket;
import it.polimi.se2018.network.server.virtual_objects.VirtualClientSocket;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class    Server {

    private static int portSocket = 1111;
    private static int portRMI = 1099;
    private static int timer;
    private ArrayList<VirtualViewInterface> players = new ArrayList<>();
    private final Controller controller;

    public Server() {

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
                    if(words[i].trim().equals("Timer:")){
                        timer = Integer.parseInt(words[i+1]);
                    }
                    if(words[i].trim().equals("PortRMI:")){
                        portRMI = Integer.parseInt(words[i+1]);
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

        controller = new Controller();
        controller.setTimer(timer);
        ClientGathererSocket clientGathererSocket = new ClientGathererSocket(this, portSocket);
        clientGathererSocket.start();
        try{
            LocateRegistry.createRegistry(portRMI);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        try{
            ClientGathererRMI clientGathererRMI = new ClientGathererRMI(this);
            Naming.rebind("//localhost/ClientGathererRMI", clientGathererRMI);
        } catch (RemoteException | MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void addClient(Socket newClient){
        if(players.size()<4){
            System.out.println("New socket client connection...");
            VirtualClientSocket virtualClientSocket = new VirtualClientSocket(this, newClient);
            virtualClientSocket.start();
            this.addClient(virtualClientSocket.getVirtualViewSocket());
        }else{
            try{
                ObjectOutputStream temporaryWriter = new ObjectOutputStream(newClient.getOutputStream());
                temporaryWriter.writeObject(new ErrorMessage("server", newClient.getRemoteSocketAddress().toString(), "PlayerNumberExceeded"));
                System.out.println("Mando messaggio di errore a :" + newClient.getRemoteSocketAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(VirtualViewInterface newClient){
        players.add(newClient);
    }

    public void removeClient(VirtualViewInterface oldClient){
        players.remove(oldClient);
    }

    public List<VirtualViewInterface> getPlayers(){
        return players;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public String toString(){
        return "server";
    }

    public static void main(String args[]){
        new Server();
    }

    public void resetOldClientSocket(Socket newClientConnection, String username) {
        System.out.println("Comeback procedure started.");
        boolean clientFound = false;
        for(VirtualViewInterface client: players){
            if(client.getUsername().equals(username)){
                System.out.println("Found old VirtualClient.");
                clientFound = true;
                client.setClientConnection(newClientConnection);
            }
        }
        if(!clientFound){
            System.out.println("Old VirtualClient not found");
            ObjectOutputStream temporaryOutput;
            try {
                temporaryOutput = new ObjectOutputStream(newClientConnection.getOutputStream());
                temporaryOutput.writeObject(new ErrorMessage("server", newClientConnection.getRemoteSocketAddress().toString(), "UsernameNotFound"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}