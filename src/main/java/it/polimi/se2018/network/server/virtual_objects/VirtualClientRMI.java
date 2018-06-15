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
    public void sendToServer(ChooseDiceMove chooseDiceMove) throws RemoteException{
        System.out.println("VirtualClientRMI -> Server: " + chooseDiceMove.toString());
        virtualView.updateServer(chooseDiceMove);
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
    public void sendToServer(DiePlacementMessage diePlacementMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + diePlacementMessage.toString());
        virtualView.updateServer(diePlacementMessage);
    }

    @Override
    public void sendToServer(ErrorMessage errorMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + errorMessage.toString());
        virtualView.updateServer(errorMessage);
    }

    @Override
    public void sendToServer(GameInitializationMessage gameInitializationMessage) throws RemoteException{
        System.out.println("VirtualClientRMI -> Server: " + gameInitializationMessage.toString());
        virtualView.updateServer(gameInitializationMessage);
    }

    @Override
    public void sendToServer(NewRoundMessage newRoundMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + newRoundMessage.toString());
        virtualView.updateServer(newRoundMessage);
    }

    @Override
    public void sendToServer(NoActionMove noActionMove){
        System.out.println("VirtualClientRMI -> Server: " + noActionMove.toString());
        virtualView.updateServer(noActionMove);
    }

    @Override
    public void sendToServer(RequestMessage requestMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + requestMessage.toString());
        virtualView.updateServer(requestMessage);
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
    public void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException {
        virtualView.updateServer(selectedSchemaMessage);
    }

    @Override
    public void sendToServer(ToolCardErrorMessage toolCardErrorMessage) throws RemoteException {
        System.out.println("VirtualClientRMI -> Server: " + toolCardErrorMessage.toString());
        virtualView.updateServer(toolCardErrorMessage);
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

    public void notifyClient(GameInitializationMessage gameInitializationMessage){
        if(isConnected){
            try{
                System.out.println("VCRMI -> RemoteVRMI: " + gameInitializationMessage.toString());
                remoteView.updateClient(gameInitializationMessage);
            } catch (RemoteException e) {
                System.out.println("Giocatore #" + server.getPlayers().indexOf(virtualView) + " " + username + "disconnesso");
                isConnected = false;
                e.printStackTrace();
            }
        }
    }

    public void notifyClient(RequestMessage requestMessage){
        if(isConnected){
            try{
                System.out.println("VCRMI -> RemoteVRMI: " + requestMessage.toString());
                remoteView.updateClient(requestMessage);
            } catch (RemoteException e) {
                System.out.println("Giocatore #" + server.getPlayers().indexOf(virtualView) + " " + username + "disconnesso");
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
