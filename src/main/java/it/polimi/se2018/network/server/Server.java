package it.polimi.se2018.network.server;
/**
 * @author Luciano
 */

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.network.server.client_gatherer.ClientGathererRMI;
import it.polimi.se2018.network.server.client_gatherer.ClientGathererSocket;
import it.polimi.se2018.network.server.virtual_objects.VirtualClientSocket;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewInterface;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    public static final String SERVER_INPUT_FILE_ADDRESS = "src/main/input.txt";
    private ArrayList<VirtualViewInterface> playersVirtualView = new ArrayList<>();
    private final Controller controller;

    private Server() {
        FileParser fileParser = new FileParser();

        int timer = fileParser.readTimer(SERVER_INPUT_FILE_ADDRESS);
        int portSocket = fileParser.readPortSocket(SERVER_INPUT_FILE_ADDRESS);
        int portRMI = fileParser.readPortRMI(SERVER_INPUT_FILE_ADDRESS);

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
        System.out.println("Server is up...");
    }

    public synchronized void addClient(Socket newClient){
        if(playersVirtualView.size()<4){
            VirtualClientSocket virtualClientSocket = new VirtualClientSocket(this, newClient);
            virtualClientSocket.start();
            this.addClient(virtualClientSocket.getVirtualViewSocket());
        }else{
            try{
                ObjectOutputStream temporaryWriter = new ObjectOutputStream(newClient.getOutputStream());
                temporaryWriter.writeObject(new ErrorMessage("server",
                        newClient.getRemoteSocketAddress().toString(), "PlayerNumberExceeded"));
                System.out.println("Send Message to :" + newClient.getRemoteSocketAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void addClient(VirtualViewInterface newClient){
        playersVirtualView.add(newClient);
    }

    public synchronized void removeClient(VirtualViewInterface oldClient){
        playersVirtualView.remove(oldClient);
        System.out.println("removed client " + oldClient.getUsername() + " from list");
        controller.removeObserver(oldClient);
        System.out.println("removed controller observer " + oldClient.getUsername());
        controller.removeObserverFromModel(oldClient);
        System.out.println("removed model observer " + oldClient.getUsername());
        controller.blockPlayer(oldClient.getUsername());
        System.out.println("called blockPlayer from Server.removeClient");
    }

    public List<VirtualViewInterface> getVirtualViewInterfacesList(){
        return playersVirtualView;
    }

    public Controller getController() {
        return controller;
    }

    public static void main(String args[]){
        new Server();
    }

}