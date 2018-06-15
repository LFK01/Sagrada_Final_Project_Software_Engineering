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
        System.out.println("VirtualViewRMI -> Server: " + message.toString());
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
            System.out.println("VirtualViewRMI -> Controller: " + createPlayerMessage.toString());
            setChanged();
            notifyObservers(createPlayerMessage);
        }
    }

    public void updateServer(ComebackSocketMessage message){
        System.out.println("VirtualViewRMI -> Controller comeback doesn't notify");
    }

    public void updateServer(SelectedSchemaMessage selectedSchemaMessage){
        System.out.println("VirtualView -> Controller");
        setChanged();
        notifyObservers(selectedSchemaMessage);
    }
    public void updateServer(ChooseDiceMove chooseDiceMove){
        System.out.println("VirtualView -> Controller");
        setChanged();
        notifyObservers(chooseDiceMove);
    }
    public void updateServer(DiePlacementMessage diePlacementMessage){
        System.out.println("VirtualView -> Controller");
        setChanged();
        notifyObservers(diePlacementMessage);

    }
    public void updateServer(NoActionMove noActionMove){
        System.out.println("VirtualView -> Controller");
        setChanged();
        notifyObservers(noActionMove);
    }



    @Override
    public void update(Message message) {
        virtualClientRMI.notifyClient(message);
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        virtualClientRMI.notifyClient(chooseSchemaMessage);
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        
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

    }

    @Override
    public void update(ErrorMessage errorMessage) {
        virtualClientRMI.notifyClient(errorMessage);
    }

    @Override
    public void update(GameInitializationMessage gameInitializationMessage) {
        virtualClientRMI.notifyClient(gameInitializationMessage);
    }


    @Override
    public void update(NewRoundMessage newRoundMessage) {
        virtualClientRMI.notifyClient(newRoundMessage);
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        virtualClientRMI.notifyClient(showPrivateObjectiveCardsMessage);
    }

    @Override
    public void update(SuccessMessage successMessage) {

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
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {

    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {

    }

    @Override
    public void update(UpdateTurnMessage updateTurnMessage) {
        virtualClientRMI.notifyClient(updateTurnMessage);
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        virtualClientRMI.notifyClient(useToolCardMove);
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
