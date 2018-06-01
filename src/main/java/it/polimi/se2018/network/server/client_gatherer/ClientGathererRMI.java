package it.polimi.se2018.network.server.client_gatherer;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;
import it.polimi.se2018.network.server.virtual_objects.VirtualClientRMI;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewInterface;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewRMI;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewSocket;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientGathererRMI extends UnicastRemoteObject implements ServerRMIInterface {

    private Server server;

    public ClientGathererRMI(Server server) throws RemoteException{
        this.server = server;
        Dimension d = new Dimension();
    }

    @Override
    public ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException {
        if(server.getPlayers().size()<4){
            VirtualViewRMI newVirtualView = new VirtualViewRMI(newClient);
            server.getController().addObserver(newVirtualView);
            newVirtualView.addObserver(server.getController());
            server.getController().getModel().addObserver(newVirtualView);
            ServerRMIInterface remoteServerRef = null;
            try{
                remoteServerRef = (ServerRMIInterface) UnicastRemoteObject.exportObject(newVirtualView.getVirtualClientRMI(), 0);
            }catch (RemoteException e){
                e.printStackTrace();
            }
            server.addClient(newVirtualView);
            return remoteServerRef;
        } else{
            throw new PlayerNumberExceededException("Player number limit already reached.");
        }
    }

    @Override
    public void sendToServer(Message message) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public ServerRMIInterface retrieveOldClient(ClientRMIInterface newClient, String username) throws RemoteException, PlayerNotFoundException {
        for (VirtualViewInterface client : server.getPlayers()) {
            if(client.getUsername().equals(username)){
                client = new VirtualViewRMI(newClient, username);
                ((VirtualViewRMI) client).addObserver(server.getController());
            }
        }
        throw new PlayerNotFoundException();
    }
}