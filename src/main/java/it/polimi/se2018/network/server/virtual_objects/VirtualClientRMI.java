package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.ServerRMIInterface;

import java.rmi.RemoteException;

public class VirtualClientRMI  implements ServerRMIInterface{

    private VirtualViewRMI virtualView;
    private ClientRMIInterface remoteView;

    public VirtualClientRMI(VirtualViewRMI virtualView, ClientRMIInterface remoteView) throws RemoteException{
        this.virtualView = virtualView;
        this.remoteView = remoteView;
    }

    @Override
    public ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException {
        /*method to be implemented in ClientGathereRMI*/
        return this;
    }

    @Override
    public void sendToServer(Message message) throws RemoteException {
        System.out.println("VirtualClientRMI 1");
        virtualView.updateServer(message);
    }

    @Override
    public void updateClient(Message message) throws RemoteException{
        System.out.println("VirtualClientRMI 2");
        remoteView.updateClient(message);
    }
}
