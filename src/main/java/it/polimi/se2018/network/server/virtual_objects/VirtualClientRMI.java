package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;

import java.rmi.RemoteException;

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
        username = this.toString();
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
        virtualView.updateServer(message);
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
    public void sendToServer(ComebackSocketMessage comebackSocketMessage) throws RemoteException {
        virtualView.updateServer(comebackSocketMessage);
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
        virtualView.updateServer(errorMessage);
    }

    @Override
    public void sendToServer(SendGameboardMessage sendGameboardMessage) throws RemoteException{
        virtualView.updateServer(sendGameboardMessage);
    }

    @Override
    public void sendToServer(NewRoundMessage newRoundMessage) throws RemoteException {
        virtualView.updateServer(newRoundMessage);
    }

    @Override
    public void sendToServer(NoActionMove noActionMove){
        virtualView.updateServer(noActionMove);
    }

    @Override
    public void sendToServer(RequestMessage requestMessage) throws RemoteException {
        virtualView.updateServer(requestMessage);
    }

    @Override
    public void sendToServer(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) throws RemoteException {
        virtualView.updateServer(showPrivateObjectiveCardsMessage);
    }

    @Override
    public void sendToServer(SuccessCreatePlayerMessage successCreatePlayerMessage) throws RemoteException {
        virtualView.updateServer(successCreatePlayerMessage);
    }

    @Override
    public void sendToServer(SuccessMessage successMessage) throws RemoteException {
        virtualView.updateServer(successMessage);
    }

    @Override
    public void sendToServer(SuccessMoveMessage successMoveMessage) throws RemoteException {
        virtualView.updateServer(successMoveMessage);
    }
    @Override
    public void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException {
        virtualView.updateServer(selectedSchemaMessage);
    }

    @Override
    public void sendToServer(ToolCardErrorMessage toolCardErrorMessage) throws RemoteException {
        virtualView.updateServer(toolCardErrorMessage);
    }

    public void notifyClient(Message message){
        if(isConnected){
            try{
                remoteView.updateClient(message);
            } catch (RemoteException e) {
                System.out.println("Giocatore #" + server.getPlayers().indexOf(virtualView) + " " + username + "disconnesso");
                isConnected = false;
            }
        }
    }

    public void notifyClient(ChooseSchemaMessage chooseSchemaMessage){
        if(isConnected){
            try{
                remoteView.updateClient(chooseSchemaMessage);
            } catch (RemoteException e) {
                System.out.println("Giocatore #" + server.getPlayers().indexOf(virtualView) + " " + username + "disconnesso");
                isConnected = false;
            }
        }
    }

    public void notifyClient(SendGameboardMessage sendGameboardMessage){
        if(isConnected){
            try{
                remoteView.updateClient(sendGameboardMessage);
            } catch (RemoteException e) {
                System.out.println("Giocatore #" + server.getPlayers().indexOf(virtualView) + " " + username + "disconnesso");
                isConnected = false;
            }
        }
    }

    public void notifyClient(RequestMessage requestMessage){
        if(isConnected){
            try{
                remoteView.updateClient(requestMessage);
            } catch (RemoteException e) {
                System.out.println("Player #" + server.getPlayers().indexOf(virtualView) + " " + username + "offline.");
                isConnected = false;
            }
        }
    }
    public void notifyClient(SendWinnerMessage sendWinnerMessage){
        if(isConnected){
            try{
                remoteView.updateClient(sendWinnerMessage);
            } catch (RemoteException e) {
                System.out.println("Player #" + server.getPlayers().indexOf(virtualView) + " " + username + "offline.");
                isConnected = false;
            }
        }
    }

    public void notifyClient(ErrorMessage errorMessage){
        if(isConnected){
            try{
                remoteView.updateClient(errorMessage);
            } catch (RemoteException e) {
                System.out.println("Player #" + server.getPlayers().indexOf(virtualView) + " " + username + "offline");
                isConnected = false;
            }
        }
    }

    public void notifyClient(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        if(isConnected){
            try{
                remoteView.updateClient(showPrivateObjectiveCardsMessage);
            } catch (RemoteException e) {
                System.out.println("Player #" + server.getPlayers().indexOf(virtualView) + " " + username + "offline");
                isConnected = false;
            }
        }
    }

    public void notifyClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(isConnected){
            try{
                remoteView.updateClient(successCreatePlayerMessage);
            } catch (RemoteException e) {
                System.out.println("Player #" + server.getPlayers().indexOf(virtualView) + " " + username + "offline");
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
