package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
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

    public void updateServer(UseToolCardMove useToolCardMove){
        setChanged();
        notifyObservers(useToolCardMove);
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
        virtualClientSocket.notifyClient(createPlayerMessage);
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        virtualClientSocket.notifyClient(diePlacementMessage);
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
        virtualClientSocket.notifyClient(selectedSchemaMessage);
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
    public void update(UseToolCardMove useToolCardMove) {
        virtualClientSocket.notifyClient(useToolCardMove);
    }

    @Override
    public void update(ChooseDiceMove chooseDiceMove) {
        virtualClientSocket.notifyClient(chooseDiceMove);
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        virtualClientSocket.notifyClient(toolCardActivationMessage);
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
    public void update(NoActionMove noActionMove){
        virtualClientSocket.notifyClient(noActionMove);
    }

    @Override
    public void update(RequestMessage requestMessage) {
        virtualClientSocket.notifyClient(requestMessage);
    }

    @Override
    public String getUsername() {
        return virtualClientSocket.getUsername();
    }

    public VirtualClientSocket getClientSocket() {
        return virtualClientSocket;
    }

    public void setClientSocket(VirtualClientSocket clientSocket) {
        this.virtualClientSocket = clientSocket;
    }
}
