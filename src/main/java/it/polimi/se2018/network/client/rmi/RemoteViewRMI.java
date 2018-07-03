package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

public class RemoteViewRMI extends ProjectObservable implements ClientRMIInterface, ProjectObserver {

    private ServerRMIInterface server;
    private String username;

    public RemoteViewRMI() throws RemoteException{}

    @Override
    public void updateClient(Message message) throws RemoteException {
        try{
            Method notifyView = this.getClass().getDeclaredMethod("notifyView", message.getClass());
            notifyView.invoke(this, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void notifyView(Message message){
        if(message.getRecipient().equals(username)){
            System.out.println("RemoteVRMI -> View: " + message.toString());
            setChanged();
            notifyObservers(message);
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

    private void notifyView(SendGameboardMessage sendGameboardMessage){
        if(sendGameboardMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(sendGameboardMessage);
        }
    }

    private void notifyView(RequestMessage requestMessage){
        if(requestMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(requestMessage);
        }

    }

    private void notifyView(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        if(showPrivateObjectiveCardsMessage.getRecipient().equals(username) || showPrivateObjectiveCardsMessage.getRecipient().equals("all")){
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

    public void notifyView(SendWinnerMessage sendWinnerMessage){
        System.out.println("rmView -> view: " + sendWinnerMessage);
        if(sendWinnerMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(sendWinnerMessage);
        }
    }

    private void notifyView(ToolCardErrorMessage toolCardErrorMessage){
        if(toolCardErrorMessage.getRecipient().equals(username)){
            System.out.println("RemoteVRMI -> View: " + toolCardErrorMessage.toString());
            setChanged();
            notifyObservers(toolCardErrorMessage);
        }
    }
    
    public void setServer(ServerRMIInterface server) {
        this.server = server;
    }

    @Override
    public void update(ChooseDiceMove chooseDiceMove) {
        try{
            server.sendToServer(chooseDiceMove);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        try{
            server.sendToServer(chooseSchemaMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        try{
            server.sendToServer(comebackMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        username = createPlayerMessage.getPlayerName();
        try{
            server.sendToServer(createPlayerMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        try{
            server.sendToServer(diePlacementMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ErrorMessage errorMessage) {
        try{
            server.sendToServer(errorMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        try{
            server.sendToServer(sendGameboardMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(NoActionMove noActionMove){
        try{
            server.sendToServer(noActionMove);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(RequestMessage requestMessage) {
        try{
            server.sendToServer(requestMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        try{
            server.sendToServer(selectedSchemaMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        try{
            server.sendToServer(showPrivateObjectiveCardsMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        try{
            server.sendToServer(successCreatePlayerMessage);
        } catch (RemoteException e){

            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        try{
            server.sendToServer(toolCardActivationMessage);
        } catch (RemoteException e){

            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        try{
            server.sendToServer(toolCardErrorMessage);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        try{
            server.sendToServer(useToolCardMove);
        } catch (RemoteException e){
            notifyView(new ErrorMessage("remoteView", username, "ServerIsDown"));
        }
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        /*should never be called here*/
    }

}
