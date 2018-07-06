package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;
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

    public RemoteViewSocket(String localhost, int port) throws java.net.ConnectException {
        server = new NetworkHandler(localhost, port, this);
        username = this.toString();
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
    public void update(ChooseDiceMessage chooseDiceMessage) {
        try {
            server.sendToServer(chooseDiceMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        try {
            server.sendToServer(chooseSchemaMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        this.username = comebackMessage.getUsername();
        try {
            server.sendToServer(comebackMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        username = createPlayerMessage.getPlayerName();
        try {
            server.sendToServer(createPlayerMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        try {
            server.sendToServer(diePlacementMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ErrorMessage errorMessage) {
        try {
            server.sendToServer(errorMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        try {
            server.sendToServer(sendGameboardMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(NoActionMessage noActionMessage){
        try {
            server.sendToServer(noActionMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
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
        }
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        try {
            server.sendToServer(successCreatePlayerMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(RequestMessage requestMessage) {
        try {
            server.sendToServer(requestMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        try {
            server.sendToServer(toolCardActivationMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        try {
            server.sendToServer(toolCardErrorMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ChooseToolCardMessage chooseToolCardMessage) {
        try {
            server.sendToServer(chooseToolCardMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        try {
            server.sendToServer(sendWinnerMessage);
        } catch (IOException e) {
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("RemoteViewSocket(").append(username).append(")");
        return builder.toString();
    }
}