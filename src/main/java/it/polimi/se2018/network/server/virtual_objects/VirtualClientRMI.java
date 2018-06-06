package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualClientRMI  implements ServerRMIInterface, VirtualClientInterface{

    private VirtualViewRMI virtualView;
    private ClientRMIInterface remoteView;
    private boolean isConnected;
    private Server server;
    private String username;

    public VirtualClientRMI(VirtualViewRMI virtualView, ClientRMIInterface remoteView, Server server) throws RemoteException{
        this.virtualView = virtualView;
        this.remoteView = remoteView;
        this.server = server;
        isConnected = true;
        username = "defaultUsername";
    }

    public VirtualClientRMI(VirtualViewRMI virtualView, ClientRMIInterface remoteView, String username, Server server) throws RemoteException{
        this.virtualView = virtualView;
        this.remoteView = remoteView;
        this.server = server;
        this.username = username;
        isConnected = true;
    }

    @Override
    public ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException {
        /*method to be implemented in ClientGathererRMI*/
        return null;
    }

    @Override
    public ServerRMIInterface retrieveOldClient(ClientRMIInterface newClient, String username) throws RemoteException, PlayerNotFoundException {
        /*method to be implemented in ClientGathererRMI*/
        return null;
    }

    @Override
    public void sendToServer(Message message) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + message.toString());
        virtualView.updateServer(message);
    }

    @Override
    public void sendToServer(ChooseSchemaMessage chooseSchemaMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + chooseSchemaMessage.toString());
        virtualView.updateServer(chooseSchemaMessage);
    }

    @Override
    public void sendToServer(ComebackMessage comebackMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + comebackMessage.toString());
        virtualView.updateServer(comebackMessage);
    }

    @Override
    public void sendToServer(ComebackSocketMessage comebackSocketMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + comebackSocketMessage.toString());
        virtualView.updateServer(comebackSocketMessage);
    }

    @Override
    public void sendToServer(CreatePlayerMessage createPlayerMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + createPlayerMessage.toString());
        virtualView.updateServer(createPlayerMessage);
    }

    @Override
    public void sendToServer(DemandSchemaCardMessage demandSchemaCardMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + demandSchemaCardMessage.toString());
        virtualView.updateServer(demandSchemaCardMessage);
    }

    @Override
    public void sendToServer(ErrorMessage errorMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + errorMessage.toString());
        virtualView.updateServer(errorMessage);
    }

    @Override
    public void sendToServer(NewRoundMessage newRoundMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + newRoundMessage.toString());
        virtualView.updateServer(newRoundMessage);
    }

    @Override
    public void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + selectedSchemaMessage.toString());
        virtualView.updateServer(selectedSchemaMessage);
    }

    @Override
    public void sendToServer(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + showPrivateObjectiveCardsMessage.toString());
        virtualView.updateServer(showPrivateObjectiveCardsMessage);
    }

    @Override
    public void sendToServer(SuccessCreatePlayerMessage successCreatePlayerMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + successCreatePlayerMessage.toString());
        virtualView.updateServer(successCreatePlayerMessage);
    }

    @Override
    public void sendToServer(SuccessMessage successMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + successMessage.toString());
        virtualView.updateServer(successMessage);
    }

    @Override
    public void sendToServer(SuccessMoveMessage successMoveMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + successMoveMessage.toString());
        virtualView.updateServer(successMoveMessage);
    }

    @Override
    public void sendToServer(UpdateTurnMessage updateTurnMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + updateTurnMessage.toString());
        virtualView.updateServer(updateTurnMessage);
    }

    public void notifyClient(Message message){
        if(isConnected){
            try{
                System.out.println("VirtualClientRMI -> RemoteVRMI: " + message.toString());
                remoteView.updateClient(message);
            } catch (RemoteException e) {
                isConnected = false;
                e.printStackTrace();
            }
        }
    }

    public void notifyClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(isConnected){
            try{
                System.out.println("VirtualClientRMI -> RemoteVRMI: " + successCreatePlayerMessage.toString());
                remoteView.updateClient(successCreatePlayerMessage);
            } catch (RemoteException e) {
                isConnected = false;
                e.printStackTrace();
            }
        }
    }

    public void notifyClient(ErrorMessage errorMessage){
        if(isConnected){
            try{
                System.out.println("VCRMI -> RemoteVRMI: " + errorMessage.toString());
                remoteView.updateClient(errorMessage);
            } catch (RemoteException e) {
                System.out.println("Giocatore #" + server.getPlayers().indexOf(virtualView) + " " + username + "disconnesso");
                isConnected = false;
                e.printStackTrace();
            }
        }
    }

    public void notifyClient(ChooseSchemaMessage chooseSchemaMessage){
        if(isConnected){
            try{
                System.out.println("VirtualClientRMI -> RemoteVRMI: " + chooseSchemaMessage.toString());
                remoteView.updateClient(chooseSchemaMessage);
            } catch (RemoteException e) {
                isConnected = false;
                e.printStackTrace();
            }
        }
    }

    public void notifyClient(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        if(isConnected){
            try{
                System.out.println("VirtualClientRMI -> RemoteVRMI: " + showPrivateObjectiveCardsMessage.toString());
                remoteView.updateClient(showPrivateObjectiveCardsMessage);
            } catch (RemoteException e) {
                isConnected = false;
                e.printStackTrace();
            }
        }
    }

    public void notifyClient(DemandSchemaCardMessage demandSchemaCardMessage){
        if(isConnected){
            try{
                System.out.println("VirtualClientRMI -> RemoteVRMI: " + demandSchemaCardMessage.toString());
                remoteView.updateClient(demandSchemaCardMessage);
            } catch (RemoteException e) {
                isConnected = false;
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
}
