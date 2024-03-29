package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.utils.ProjectObservable;

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

    public void updateServer(ChooseSchemaMessage chooseSchemaMessage){
        setChanged();
        notifyObservers(chooseSchemaMessage);
    }

    public void updateServer(ComebackMessage comebackMessage){
        setChanged();
        notifyObservers(comebackMessage);
    }

    public void updateServer(CreatePlayerMessage createPlayerMessage) {
        boolean correctUsername = true;
        if(virtualClientRMI.getServer().getVirtualViewInterfacesList().size()>1){
            for(VirtualViewInterface client: virtualClientRMI.getServer().getVirtualViewInterfacesList()){
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

    public void updateServer(SelectedSchemaMessage selectedSchemaMessage){
        setChanged();
        notifyObservers(selectedSchemaMessage);
    }

    public void updateServer(ChooseDiceMessage chooseDiceMessage){
        setChanged();
        notifyObservers(chooseDiceMessage);
    }

    public void updateServer(DiePlacementMessage diePlacementMessage){
        setChanged();
        notifyObservers(diePlacementMessage);
    }

    public void updateServer(NoActionMessage noActionMessage){
        setChanged();
        notifyObservers(noActionMessage);
    }

    public void updateServer(ToolCardActivationMessage toolCardActivationMessage){
        setChanged();
        notifyObservers(toolCardActivationMessage);
    }

    public void updateServer(ToolCardErrorMessage toolCardErrorMessage){
        setChanged();
        notifyObservers(toolCardErrorMessage);
    }

    public void updateServer(ChooseToolCardMessage chooseToolCardMessage) {
        setChanged();
        notifyObservers(chooseToolCardMessage);
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        virtualClientRMI.notifyClient(chooseSchemaMessage);
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        /*should never be called*/
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        /*should never be called*/
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
        /*should never be called*/
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        virtualClientRMI.notifyClient(showPrivateObjectiveCardsMessage);
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        virtualClientRMI.notifyClient(successCreatePlayerMessage);
    }

    @Override
    public void update(ChooseDiceMessage chooseDiceMessage){
        /*should never be called*/
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
    public void update(ChooseToolCardMessage chooseToolCardMessage) {
        virtualClientRMI.notifyClient(chooseToolCardMessage);
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        virtualClientRMI.notifyClient(sendWinnerMessage);
    }

    @Override
    public void update(NoActionMessage noActionMessage){/*should never be called*/}

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
}
