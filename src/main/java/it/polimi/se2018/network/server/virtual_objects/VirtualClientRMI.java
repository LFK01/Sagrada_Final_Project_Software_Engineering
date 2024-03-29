package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VirtualClientRMI  implements ServerRMIInterface, VirtualClientInterface{

    private VirtualViewRMI virtualView;
    private ClientRMIInterface remoteView;
    private Server server;
    private String username;

    public VirtualClientRMI(VirtualViewRMI virtualView, ClientRMIInterface remoteView, Server server) throws RemoteException{
        this.virtualView = virtualView;
        this.remoteView = remoteView;
        this.server = server;
        username = this.toString();
    }

    @Override
    public ServerRMIInterface addClient(ClientRMIInterface newClient) {
        /*method to be implemented in ClientGathererRMI*/
        return null;
    }

    @Override
    public void sendToServer(ChooseDiceMessage chooseDiceMessage) throws RemoteException{
        virtualView.updateServer(chooseDiceMessage);
    }

    @Override
    public void sendToServer(ComebackMessage comebackMessage) throws RemoteException {
        virtualView.updateServer(comebackMessage);
    }

    @Override
    public void sendToServer(CreatePlayerMessage createPlayerMessage) throws RemoteException {
        virtualView.updateServer(createPlayerMessage);
    }

    @Override
    public void sendToServer(DiePlacementMessage diePlacementMessage) throws RemoteException {
        virtualView.updateServer(diePlacementMessage);
    }

    @Override
    public void sendToServer(ErrorMessage errorMessage) throws RemoteException {
        if(errorMessage.toString().equalsIgnoreCase("quit") &&
                errorMessage.getSender().equalsIgnoreCase(username)){
            server.removeClient(virtualView);
        }
    }

    @Override
    public void sendToServer(NoActionMessage noActionMessage){
        virtualView.updateServer(noActionMessage);
    }

    @Override
    public void sendToServer(RequestMessage requestMessage) throws RemoteException {
        /*should never be called here*/
    }

    @Override
    public void sendToServer(ToolCardActivationMessage toolCardActivationMessage) throws RemoteException {
        virtualView.updateServer(toolCardActivationMessage);
    }

    @Override
    public void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException {
        virtualView.updateServer(selectedSchemaMessage);
    }

    @Override
    public void sendToServer(ToolCardErrorMessage toolCardErrorMessage) throws RemoteException {
        virtualView.updateServer(toolCardErrorMessage);
    }

    @Override
    public void sendToServer(ChooseToolCardMessage chooseToolCardMessage) throws RemoteException {
        virtualView.updateServer(chooseToolCardMessage);
    }

    public void notifyClient(ChooseSchemaMessage chooseSchemaMessage){
        try{
            remoteView.updateClient(chooseSchemaMessage);
        } catch (RemoteException e) {
            System.out.println("calling removeClient from virtualClientRmi 1");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(ErrorMessage errorMessage){
        try{
            remoteView.updateClient(errorMessage);
        } catch (RemoteException e) {
            System.out.println("calling removeClient from virtualClientRmi 5");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(RequestMessage requestMessage){
        try{
            remoteView.updateClient(requestMessage);
        } catch (RemoteException e) {
            System.out.println("calling removeClient from virtualClientRmi 5");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(SendGameboardMessage sendGameboardMessage){
        try{
            remoteView.updateClient(sendGameboardMessage);
        } catch (RemoteException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "remoteExcpetion class: {0}", e.getClass());
            System.out.println("calling removeClient from virtualClientRmi 7");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(SendWinnerMessage sendWinnerMessage){
        try{
            remoteView.updateClient(sendWinnerMessage);
        } catch (RemoteException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "remoteExcpetion class: {0}", e.getClass());
            System.out.println("calling removeClient from virtualClientRmi 8");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        try{
            remoteView.updateClient(showPrivateObjectiveCardsMessage);
        } catch (RemoteException e) {
            System.out.println("calling removeClient from virtualClientRmi 9");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        try{
            remoteView.updateClient(successCreatePlayerMessage);
        } catch (RemoteException e) {
            System.out.println("calling removeClient from virtualClientRmi 10");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(ToolCardActivationMessage toolCardActivationMessage) {
        try{
            remoteView.updateClient(toolCardActivationMessage);
        } catch (RemoteException e) {
            System.out.println("calling removeClient from virtualClientRmi 12");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(ToolCardErrorMessage toolCardErrorMessage) {
        try{
            remoteView.updateClient(toolCardErrorMessage);
        } catch (RemoteException e) {
            System.out.println("calling removeClient from virtualClientRmi 13");
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(ChooseToolCardMessage chooseToolCardMessage) {
        try{
            remoteView.updateClient(chooseToolCardMessage);
        } catch (RemoteException e) {
            System.out.println("calling removeClient from virtualClientRmi 14");
            server.removeClient(virtualView);
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
