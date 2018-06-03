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

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {

    private int PORTSocket = 1111;
    private int PORTRMI = 1099;
    private ClientGathererRMI clientGathererRMI;
    private ClientGathererSocket clientGathererSocket;
    private ArrayList<VirtualViewInterface> players = new ArrayList<>();
    private final Controller controller;

    public Server() {
        controller = new Controller();
        clientGathererSocket = new ClientGathererSocket(this, PORTSocket);
        clientGathererSocket.start();
        try{
            LocateRegistry.createRegistry(PORTRMI);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        try{
            this.clientGathererRMI = new ClientGathererRMI(this);
            Naming.rebind("//localhost/ClientGathererRMI", clientGathererRMI);
            System.out.println("ho creato il ClientGathererRMI ");
        } catch (RemoteException e){
            e.printStackTrace();
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void addClient(Socket newClient){
        if(players.size()<4){
            VirtualClientSocket virtualClientSocket = new VirtualClientSocket(this, newClient);
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

    public ArrayList<VirtualViewInterface> getPlayers(){
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
}
