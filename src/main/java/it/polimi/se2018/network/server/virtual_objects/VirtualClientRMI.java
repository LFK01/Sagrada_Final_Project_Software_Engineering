package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;

import java.rmi.RemoteException;

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
    public void sendToServer(ChooseDiceMove chooseDiceMove) throws RemoteException{
        virtualView.updateServer(chooseDiceMove);
    }

    @Override
    public void sendToServer(ChooseSchemaMessage chooseSchemaMessage) throws RemoteException {
        virtualView.updateServer(chooseSchemaMessage);
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
        /*should never be called here*/
    }

    @Override
    public void sendToServer(SendGameboardMessage sendGameboardMessage) throws RemoteException{
        /*should never be called here*/
    }

    @Override
    public void sendToServer(NoActionMove noActionMove){
        virtualView.updateServer(noActionMove);
    }

    @Override
    public void sendToServer(RequestMessage requestMessage) throws RemoteException {
        /*should never be called here*/
    }

    @Override
    public void sendToServer(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) throws RemoteException {
        /*should never be called here*/
    }

    @Override
    public void sendToServer(SuccessCreatePlayerMessage successCreatePlayerMessage) throws RemoteException {
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
    public void sendToServer(UseToolCardMove useToolCardMove) throws RemoteException {
        virtualView.update(useToolCardMove);
    }

    public void notifyClient(Message message){
        try{
            remoteView.updateClient(message);
        } catch (RemoteException e) {
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(ChooseSchemaMessage chooseSchemaMessage){
        try{
            remoteView.updateClient(chooseSchemaMessage);
        } catch (RemoteException e) {
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(SendGameboardMessage sendGameboardMessage){
        try{
            remoteView.updateClient(sendGameboardMessage);
        } catch (RemoteException e) {
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(RequestMessage requestMessage){
        try{
            remoteView.updateClient(requestMessage);
        } catch (RemoteException e) {
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(SendWinnerMessage sendWinnerMessage){
        try{
            remoteView.updateClient(sendWinnerMessage);
        } catch (RemoteException e) {
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(ErrorMessage errorMessage){
        try{
            remoteView.updateClient(errorMessage);
        } catch (RemoteException e) {
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        try{
            remoteView.updateClient(showPrivateObjectiveCardsMessage);
        } catch (RemoteException e) {
            server.removeClient(virtualView);
        }
    }

    public void notifyClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        try{
            remoteView.updateClient(successCreatePlayerMessage);
        } catch (RemoteException e) {
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
