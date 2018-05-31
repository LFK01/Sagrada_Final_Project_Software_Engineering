package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;

import java.rmi.RemoteException;
import java.util.Observable;

public class VirtualViewRMI extends Observable implements VirtualViewInterface {

    private VirtualClientRMI virtualClientRMI;

    public VirtualViewRMI(ClientRMIInterface clientRMIInterface){
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
            System.out.println("VirtualViewRMI 3");
            virtualClientRMI.updateClient(message);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateServer(Message message) {
        System.out.println("VirtualViewRMI 1");
        setChanged();
        notifyObservers(message);
    }

    @Override
    public void update(Observable o, Object message) {
        System.out.println("VirtualViewRMI 2");
        notifyClient((Message) message);
    }
}
