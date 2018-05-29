package it.polimi.se2018.network.server.client_gatherer;

import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientGathererRMI extends UnicastRemoteObject implements ServerRMIInterface {

    private Server server;

    public ClientGathererRMI(Server server) throws RemoteException{
        this.server = server;
    }

    @Override
    public ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException {
        VirtualViewRMI newVirtualView = new VirtualViewRMI(server, newClient);
        server.getController().addObserver(newVirtualView);
        newVirtualView.addObserver(server.getController());
        server.getController().getModel().addObserver(newVirtualView);
        ServerRMIInterface remoteServerRef = null;
        try{
            remoteServerRef = (ServerRMIInterface) UnicastRemoteObject.exportObject(newVirtualView.getVirtualClientRMI(), 0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return remoteServerRef;
    }

    @Override
    public void sendToServer(Message message) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }
}
