package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.utils.ProjectObservable;

import java.net.Socket;

public class VirtualViewSocket extends ProjectObservable implements VirtualViewInterface {

    private VirtualClientSocket virtualClientSocket;

    public VirtualViewSocket(VirtualClientSocket virtualClientSocket){
        this.virtualClientSocket = virtualClientSocket;
    }

    public void updateServer(Message message){
        setChanged();
        notifyObservers(message);
    }

    public void updateServer(CreatePlayerMessage createPlayerMessage){
        boolean correctUsername = true;
        if(virtualClientSocket.getServer().getPlayers().size()>1){
            for(VirtualViewInterface client: virtualClientSocket.getServer().getPlayers()){
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

    public void updateServer(ComebackSocketMessage message){
        try{
            virtualClientSocket.resetOldPlayer(message);
            setChanged();
            notifyObservers(message);
        } catch (PlayerNotFoundException e){
            virtualClientSocket.notifyClient(new ErrorMessage("server", virtualClientSocket.getUsername(), "Player not found"));
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

    @Override
    public void update(Message message) {
        virtualClientSocket.notifyClient(message);
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        virtualClientSocket.notifyClient(chooseSchemaMessage);
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        virtualClientSocket.notifyClient(comebackMessage);
    }

    @Override
    public void update(ComebackSocketMessage comebackSocketMessage) {
        virtualClientSocket.notifyClient(comebackSocketMessage);
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
    public void update(NewRoundMessage newRoundMessage) {
        virtualClientSocket.notifyClient(newRoundMessage);
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
    public void update(SuccessMessage successMessage) {
        virtualClientSocket.notifyClient(successMessage);
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        virtualClientSocket.notifyClient(successCreatePlayerMessage);
    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {
        virtualClientSocket.notifyClient(successMoveMessage);
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

    @Override
    public void setClientConnection(Socket clientConnection) {
        virtualClientSocket.setClientConnection(clientConnection);
    }

    public VirtualClientSocket getClientSocket() {
        return virtualClientSocket;
    }

    public void setClientSocket(VirtualClientSocket clientSocket) {
        this.virtualClientSocket = clientSocket;
    }
}
