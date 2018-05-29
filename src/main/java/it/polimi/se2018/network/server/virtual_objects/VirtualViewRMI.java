package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;

import java.rmi.RemoteException;
import java.util.Observable;

public class VirtualViewRMI extends Observable implements VirtualViewInterface {

    private VirtualClientRMI virtualClientRMI;
    private Server server;

    public VirtualViewRMI(Server server, ClientRMIInterface clientRMIInterface){
        this.server = server;
        try{
            virtualClientRMI = new VirtualClientRMI(this, clientRMIInterface);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public VirtualClientRMI getVirtualClientRMI() {
        return virtualClientRMI;
    }

    @Override
    public void notifyClient(Message message) {
        try{
            virtualClientRMI.sendToServer(message);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateServer(Message message) {
        server.getController().update(this, message);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
