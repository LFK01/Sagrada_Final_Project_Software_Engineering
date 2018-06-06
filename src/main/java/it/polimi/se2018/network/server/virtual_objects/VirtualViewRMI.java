package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.client.Client;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Observable;

public class VirtualViewRMI extends Observable implements VirtualViewInterface {

    private VirtualClientRMI virtualClientRMI;

    /**
     * contructor method called when a new user wants to connect with a new username
     * username field will be setted when client and server will exchange the first CreatePlayerMessage
     * @param clientRMIInterface used to link the VirtualView with the Client
     */
    public VirtualViewRMI(ClientRMIInterface clientRMIInterface, Server server){
        try{
            virtualClientRMI = new VirtualClientRMI(this, clientRMIInterface, server);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }


    /**
     * constructor method called when a new username wants to connect to an existing game
     * with a username already used
     * @param clientRMIInterface used to link the VirtualView with the Client
     * @param username used to set the username field
     */
    public VirtualViewRMI(ClientRMIInterface clientRMIInterface, String username, Server server){
        try{
            virtualClientRMI = new VirtualClientRMI(this, clientRMIInterface, server);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object message) {
        System.out.println("VirtualViewRMI -> Client");
        try{
            Method notifyClient = virtualClientRMI.getClass().getMethod("notifyClient", message.getClass());
            notifyClient.invoke(virtualClientRMI, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void updateServer(Message message){
        System.out.println("VirtualViewRMI -> Controller: " + message.toString());
        setChanged();
        notifyObservers(message);
    }

    public void updateServer(CreatePlayerMessage message) {
        boolean correctUsername = true;
        if(virtualClientRMI.getServer().getPlayers().size()>1){
            for(VirtualViewInterface client: virtualClientRMI.getServer().getPlayers()){
                if(client!=this){
                    if(client.getUsername().equals(message.getPlayerName())){
                        update(this, new ErrorMessage("server", message.getPlayerName(), "NotValidUsername"));
                        correctUsername = false;
                    }
                }
            }
        } else {
            correctUsername = true;
        }
        if(correctUsername){
            virtualClientRMI.setUsername(message.getPlayerName());
            System.out.println("VirtualViewRMI -> Controller: " + message.toString());
            setChanged();
            notifyObservers(message);
        }
    }

    public void updateServer(ComebackSocketMessage message){
        System.out.println("VirtualViewRMI -> Controller comeback doesn't notify");
    }

    public void updateServer(SelectedSchemaMessage selectedSchemaMessage){
        setChanged();
        notifyObservers(selectedSchemaMessage);
    }


    @Override
    public String getUsername() {
        return virtualClientRMI.getUsername();
    }

    public VirtualClientRMI getVirtualClientRMI() {
        return virtualClientRMI;
    }

    @Override
    public void setClientConnection(Socket clientConnection) {
        /*method to be implemented in VirtualViewSocket*/
    }

    public void setVirtualClientRMI(VirtualClientRMI virtualClientRMI) {
        this.virtualClientRMI = virtualClientRMI;
    }
}
