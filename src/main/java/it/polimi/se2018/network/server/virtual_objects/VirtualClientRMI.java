package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.model.events.messages.SuccessCreatePlayerMessage;
import it.polimi.se2018.model.events.messages.SuccessMessage;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

public class VirtualClientRMI  implements ServerRMIInterface{

    private VirtualViewRMI virtualView;
    private ClientRMIInterface remoteView;
    private boolean isConnected;

    private String username;

    public VirtualClientRMI(VirtualViewRMI virtualView, ClientRMIInterface remoteView, String username) throws RemoteException{
        this.virtualView = virtualView;
        this.remoteView = remoteView;
        this.username = username;
        isConnected = true;
    }

    @Override
    public ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException {
        /*method to be implemented in ClientGathererRMI*/
        return null;
    }

    @Override
    public void sendToServer(Message message) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server");
        try{
            Method updateServer = virtualView.getClass().getMethod("updateServer", message.getClass());
            updateServer.invoke(virtualView, message);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    @Override
    public ServerRMIInterface retrieveOldClient(ClientRMIInterface oldClient, String username) throws RemoteException, PlayerNotFoundException {
        /*method to be implemented in ClientGathererRMI*/
        return null;
    }

    public void updateClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        try{
            System.out.println("VirtualClientRMI -> RemoteVRMI");
            remoteView.updateClient(successCreatePlayerMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }
}
