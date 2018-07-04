package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.network.server.ServerSocketInterface;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;

import java.io.IOException;

/**
 * @author Luciano
 */
public class RemoteViewSocket extends ProjectObservable implements ProjectObserver {

    private ServerSocketInterface server;
    private String username;
    private boolean isConnected;

    public RemoteViewSocket(String localhost, int port) throws java.net.ConnectException {
        try {
            server = new NetworkHandler(localhost, port, this);
        } catch (java.net.ConnectException e) {
            throw e;
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
    public void update(ChooseDiceMove chooseDiceMove) {
        try {
            server.sendToServer(chooseDiceMove);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        try {
            server.sendToServer(chooseSchemaMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        this.username = comebackMessage.getUsername();
        try {
            server.sendToServer(comebackMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        username = createPlayerMessage.getPlayerName();
        try {
            server.sendToServer(createPlayerMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        try {
            server.sendToServer(diePlacementMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ErrorMessage errorMessage) {
        try {
            server.sendToServer(errorMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        try {
            server.sendToServer(sendGameboardMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(NoActionMove noActionMove){
        try {
            server.sendToServer(noActionMove);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        try {
            server.sendToServer(selectedSchemaMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        try {
            server.sendToServer(showPrivateObjectiveCardsMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        try {
            server.sendToServer(successCreatePlayerMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(RequestMessage requestMessage) {
        try {
            server.sendToServer(requestMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        try {
            server.sendToServer(toolCardActivationMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        try {
            server.sendToServer(toolCardErrorMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        try {
            server.sendToServer(useToolCardMove);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        try {
            server.sendToServer(sendWinnerMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    public String getUsername() {
        return username;
    }
}