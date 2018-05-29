package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.network.server.client_gatherer.ClientGathererRMI;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {

    private int PORTSocket = 1111;
    private int PORTRMI = 1099;
    private ClientGathererRMI clientGathererRMI;


    private ArrayList<VirtualViewInterface> players = new ArrayList<>();
    private final Controller controller;

    public Server() {
        controller = new Controller();
        try{
            LocateRegistry.createRegistry(PORTRMI);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        try{
            this.clientGathererRMI = new ClientGathererRMI(this);
            Naming.rebind("//localhost/ClientGathereRMI", clientGathererRMI);

        } catch (RemoteException e){
            e.printStackTrace();
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void addClient(VirtualViewInterface newClient){
        players.add(newClient);
    }

    public void removeClient(VirtualViewInterface oldClient){
        players.remove(oldClient);
        /*controller.getModel().removePlayer();*/
    }

    private ArrayList<VirtualViewInterface> getPlayers(){
        return players;
    }

    public Controller getController() {
        return controller;
    }
}
