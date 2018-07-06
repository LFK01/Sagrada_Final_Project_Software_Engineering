package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;
import it.polimi.se2018.utils.ProjectObservable;

public class VirtualViewSocket extends ProjectObservable implements VirtualViewInterface {

    private VirtualClientSocket virtualClientSocket;

    public VirtualViewSocket(VirtualClientSocket virtualClientSocket){
        this.virtualClientSocket = virtualClientSocket;
    }

    public void updateServer(CreatePlayerMessage createPlayerMessage){
        boolean correctUsername = true;
        if(virtualClientSocket.getServer().getVirtualViewInterfacesList().size()>1){
            for(VirtualViewInterface client: virtualClientSocket.getServer().getVirtualViewInterfacesList()){
                if(client!=this){
                    if(client.getUsername().equals(createPlayerMessage.getPlayerName())){
                        update(new ErrorMessage(virtualClientSocket.getServer().toString(), createPlayerMessage.getPlayerName(), "NotValidUsername"));
                        correctUsername = false;
                    }
                }
            }
        } else {
            correctUsername = true;
        }
        if(correctUsername){
            virtualClientSocket.setUsername(createPlayerMessage.getPlayerName());
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

    public void updateServer(ChooseToolCardMessage chooseToolCardMessage){
        setChanged();
        notifyObservers(chooseToolCardMessage);
    }

    public void updateServer(ToolCardActivationMessage toolCardActivationMessage){
        setChanged();
        notifyObservers(toolCardActivationMessage);
    }

    public void updateServer(ToolCardErrorMessage toolCardErrorMessage){
        setChanged();
        notifyObservers(toolCardErrorMessage);
    }

    public void updateServer(ComebackMessage comebackMessage){
        setChanged();
        notifyObservers(comebackMessage);
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        virtualClientSocket.notifyClient(chooseSchemaMessage);
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        //not used
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
        virtualClientSocket.notifyClient(errorMessage);
    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        virtualClientSocket.notifyClient(sendGameboardMessage);
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        virtualClientSocket.notifyClient(showPrivateObjectiveCardsMessage);
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        virtualClientSocket.notifyClient(successCreatePlayerMessage);
    }

    @Override
    public void update(ChooseToolCardMessage chooseToolCardMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ChooseDiceMessage chooseDiceMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        virtualClientSocket.notifyClient(toolCardErrorMessage);
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage){
        virtualClientSocket.notifyClient(sendWinnerMessage);
    }

    @Override
    public void update(NoActionMessage noActionMessage){
    }

    @Override
    public void update(RequestMessage requestMessage) {
        virtualClientSocket.notifyClient(requestMessage);
    }

    @Override
    public String getUsername() {
        return virtualClientSocket.getUsername();
    }
}
