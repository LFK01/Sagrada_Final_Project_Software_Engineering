package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteViewRMI extends ProjectObservable implements ClientRMIInterface, ProjectObserver {

    private ServerRMIInterface server;
    private String username;
    boolean isConnected;

    public RemoteViewRMI() throws RemoteException{}

    @Override
    public void updateClient(Message message) throws RemoteException {
        try{
            Method notifyView = this.getClass().getDeclaredMethod("notifyView", message.getClass());
            notifyView.invoke(this, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "cant find method on remoteViewRmi class: {0}", message.getClass());
        }
    }

    private void notifyView(ChooseSchemaMessage chooseSchemaMessage){
        if(chooseSchemaMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(chooseSchemaMessage);
        }
    }

    private void notifyView(ErrorMessage errorMessage){
        if(errorMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(errorMessage);
        }
    }

    private void notifyView(RequestMessage requestMessage){
        if(requestMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(requestMessage);
        }

    }

    private void notifyView(SendGameboardMessage sendGameboardMessage){
        if(sendGameboardMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(sendGameboardMessage);
        }
    }

    public void notifyView(SendWinnerMessage sendWinnerMessage){
        if(sendWinnerMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(sendWinnerMessage);
        }
    }

    private void notifyView(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        if(showPrivateObjectiveCardsMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(showPrivateObjectiveCardsMessage);
        }
    }

    public void notifyView(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(successCreatePlayerMessage);
        }
    }

    private void notifyView(ToolCardErrorMessage toolCardErrorMessage){
        if(toolCardErrorMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(toolCardErrorMessage);
        }
    }
    
    public void setServer(ServerRMIInterface server) {
        this.server = server;
    }

    @Override
    public void update(ChooseDiceMessage chooseDiceMessage) {
        try{
            server.sendToServer(chooseDiceMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        this.username = comebackMessage.getUsername();
        try{
            server.sendToServer(comebackMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        username = createPlayerMessage.getPlayerName();
        try{
            server.sendToServer(createPlayerMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        try{
            server.sendToServer(diePlacementMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ErrorMessage errorMessage) {
        try{
            server.sendToServer(errorMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        /*should never be called*/
    }

    @Override
    public void update(NoActionMessage noActionMessage){
        try{
            server.sendToServer(noActionMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(RequestMessage requestMessage) {
        try{
            server.sendToServer(requestMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        try{
            server.sendToServer(selectedSchemaMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        /*should never be called*/
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        try{
            server.sendToServer(toolCardActivationMessage);
        } catch (RemoteException e){

            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        try{
            server.sendToServer(toolCardErrorMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(ChooseToolCardMessage chooseToolCardMessage) {
        try{
            server.sendToServer(chooseToolCardMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
            isConnected = false;
        }
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        /*should never be called*/
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("RemoteViewRMI(").append(username).append(")");
        return builder.toString();
    }

}
