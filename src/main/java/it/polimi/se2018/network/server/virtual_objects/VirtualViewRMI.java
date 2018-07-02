package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.utils.ProjectObservable;

import java.net.Socket;
import java.rmi.RemoteException;

public class VirtualViewRMI extends ProjectObservable implements VirtualViewInterface {

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

    public void updateServer(Message message){
        setChanged();
        notifyObservers(message);
    }

    public void updateServer(CreatePlayerMessage createPlayerMessage) {
        boolean correctUsername = true;
        if(virtualClientRMI.getServer().getPlayers().size()>1){
            for(VirtualViewInterface client: virtualClientRMI.getServer().getPlayers()){
                if(client!=this){
                    if(client.getUsername().equals(createPlayerMessage.getPlayerName())){
                        update(new ErrorMessage("server", createPlayerMessage.getPlayerName(), "NotValidUsername"));
                        correctUsername = false;
                    }
                }
            }
        } else {
                correctUsername = true;
        }
        if(correctUsername){
            virtualClientRMI.setUsername(createPlayerMessage.getPlayerName());
            setChanged();
            notifyObservers(createPlayerMessage);
        }
    }

    public void updateServer(ComebackSocketMessage message){
        /*to be implemented with comeback function*/
    }

    public void updateServer(SelectedSchemaMessage selectedSchemaMessage){
        setChanged();
        notifyObservers(selectedSchemaMessage);
    }

    public void updateServer(ChooseDiceMove chooseDiceMove){
        setChanged();
        notifyObservers(chooseDiceMove);
    }

    public void updateServer(DiePlacementMessage diePlacementMessage){
        setChanged();
        notifyObservers(diePlacementMessage);
    }

    public void updateServer(NoActionMove noActionMove){
        setChanged();
        notifyObservers(noActionMove);
    }
    
    public void updateServer(ToolCardErrorMessage toolCardErrorMessage){
        setChanged();
        notifyObservers(toolCardErrorMessage);
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        virtualClientRMI.notifyClient(chooseSchemaMessage);
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        virtualClientRMI.notifyClient(comebackMessage);
    }

    @Override
    public void update(ComebackSocketMessage comebackSocketMessage) {
        virtualClientRMI.notifyClient(comebackSocketMessage);
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        virtualClientRMI.notifyClient(createPlayerMessage);
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        virtualClientRMI.notifyClient(diePlacementMessage);
    }

    @Override
    public void update(ErrorMessage errorMessage) {
        virtualClientRMI.notifyClient(errorMessage);
    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        virtualClientRMI.notifyClient(sendGameboardMessage);
    }


    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        virtualClientRMI.notifyClient(selectedSchemaMessage);
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        virtualClientRMI.notifyClient(showPrivateObjectiveCardsMessage);
    }

    @Override
    public void update(SuccessMessage successMessage) {
        virtualClientRMI.notifyClient(successMessage);
    }


    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        virtualClientRMI.notifyClient(successCreatePlayerMessage);
    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {
        virtualClientRMI.notifyClient(successMoveMessage);
    }


    @Override
    public void update(ChooseDiceMove chooseDiceMove){
        virtualClientRMI.notifyClient(chooseDiceMove);
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        virtualClientRMI.notifyClient(toolCardActivationMessage);
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        virtualClientRMI.notifyClient(toolCardErrorMessage);
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        virtualClientRMI.notifyClient(useToolCardMove);
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        virtualClientRMI.notifyClient(sendWinnerMessage);
    }

    @Override
    public void update(NoActionMove noActionMove){virtualClientRMI.notifyClient(noActionMove);}

    @Override
    public void update(RequestMessage requestMessage) {
        virtualClientRMI.notifyClient(requestMessage);
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
