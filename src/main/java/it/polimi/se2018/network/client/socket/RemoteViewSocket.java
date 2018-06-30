package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.network.server.ServerSocketInterface;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;

import java.rmi.ConnectException;

/**
 * @author Luciano
 */
public class RemoteViewSocket extends ProjectObservable implements ProjectObserver {

    private ServerSocketInterface server;
    private String username;
    private boolean serverIsUp;

    public RemoteViewSocket(String localhost, int port) throws java.net.ConnectException {
        try {
            server = new NetworkHandler(localhost, port, this);
        } catch (java.net.ConnectException e) {
            throw e;
        }
    }

    public RemoteViewSocket(String localhost, int port, String oldUsername)throws java.net.ConnectException{
        System.out.println("Socket comeback: RemoteView created");
        server = new NetworkHandler(localhost, port, this, oldUsername);
    }

    public void notifyView(Message message){
        if(message.getRecipient().equals(username)){
            setChanged();
            notifyObservers(message);
        }
    }

    public void notifyView(ChooseSchemaMessage chooseSchemaMessage){
        if(chooseSchemaMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(chooseSchemaMessage);
        }
    }

    public void notifyView(ErrorMessage errorMessage){
        if(errorMessage.getRecipient().equals(username)){
            if(errorMessage.toString().equals("NotValidUsername")){
                username = this.toString();
                setChanged();
                notifyObservers(errorMessage);
            }
            else{
                setChanged();
                notifyObservers(errorMessage);
            }
        }
    }

    public void notifyView(SendGameboardMessage sendGameboardMessage){
        if(sendGameboardMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(sendGameboardMessage);
        }
    }

    public void notifyView(SelectedSchemaMessage selectedSchemaMessage){
        if(selectedSchemaMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(selectedSchemaMessage);
        }
    }

    public void notifyView(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        if(showPrivateObjectiveCardsMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(showPrivateObjectiveCardsMessage);
        }
    }

    public void notifyView(SuccessMessage successMessage){
        if(successMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(successMessage);
        }
    }

    public void notifyView(RequestMessage requestMessage){
        if(requestMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(requestMessage);
        }
    }

    public void notifyView(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(successCreatePlayerMessage);
        }
    }
    public void notifyView(SendWinnerMessage sendWinnerMessage){
        if(sendWinnerMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(sendWinnerMessage);
        }
    }

    public void notifyView(ToolCardErrorMessage toolCardErrorMessage){
        if(toolCardErrorMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(toolCardErrorMessage);
        }
    }

    @Override
    public void update(Message message) {
        server.sendToServer(message);
    }

    @Override
    public void update(ChooseDiceMove chooseDiceMove) {
        server.sendToServer(chooseDiceMove);
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        server.sendToServer(chooseSchemaMessage);
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        server.sendToServer(comebackMessage);
    }

    @Override
    public void update(ComebackSocketMessage comebackSocketMessage) {
        server.sendToServer(comebackSocketMessage);
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        username = createPlayerMessage.getPlayerName();
        server.sendToServer(createPlayerMessage);
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        server.sendToServer(diePlacementMessage);
    }

    @Override
    public void update(ErrorMessage errorMessage) {
        server.sendToServer(errorMessage);
    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        server.sendToServer(sendGameboardMessage);
    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {
        server.sendToServer(newRoundMessage);
    }

    @Override
    public void update(NoActionMove noActionMove){
        server.sendToServer(noActionMove);
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        server.sendToServer(selectedSchemaMessage);
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        server.sendToServer(showPrivateObjectiveCardsMessage);
    }

    @Override
    public void update(SuccessMessage successMessage) {
        server.sendToServer(successMessage);
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        server.sendToServer(successCreatePlayerMessage);
    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {
        server.sendToServer(successMoveMessage);
    }

    @Override
    public void update(RequestMessage requestMessage) {
        server.sendToServer(requestMessage);
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        server.sendToServer(toolCardActivationMessage);
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        server.sendToServer(toolCardErrorMessage);
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        server.sendToServer(useToolCardMove);
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {

    }
}