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
    private boolean serverIsUp;

    public RemoteViewRMI() throws RemoteException{
    }

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

    public void notifyView(SuccessMessage successMessage){
        System.out.println("rmView -> view: " + successMessage);
        if(successMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(successMessage);
        }
    }

    public void notifyView(SendWinnerMessage sendWinnerMessage){
        System.out.println("rmView -> view: " + sendWinnerMessage);
            setChanged();
            notifyObservers(sendWinnerMessage);
    }


    public void setServer(ServerRMIInterface server) {
        this.server = server;
        serverIsUp = true;
    }

    @Override
    public void update(Message message) {
        if(serverIsUp){
            try{
                server.sendToServer(message);
            } catch (RemoteException e){
                e.printStackTrace();
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(ChooseDiceMove chooseDiceMove) {
        if(serverIsUp){
            try{
                server.sendToServer(chooseDiceMove);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(chooseSchemaMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        //TODO handle user comeback
        /**/
    }

    @Override
    public void update(ComebackSocketMessage comebackSocketMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(comebackSocketMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        username = createPlayerMessage.getPlayerName();
        if(serverIsUp){
            try{
                server.sendToServer(createPlayerMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(diePlacementMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }

    }

    @Override
    public void update(ErrorMessage errorMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(errorMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }


    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(sendGameboardMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(newRoundMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(NoActionMove noActionMove){
        if(serverIsUp){
            try{
                server.sendToServer(noActionMove);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(RequestMessage requestMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(requestMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }


    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(selectedSchemaMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(showPrivateObjectiveCardsMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(SuccessMessage successMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(successMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(successCreatePlayerMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(successMoveMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(toolCardActivationMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        if(serverIsUp){
            try{
                server.sendToServer(toolCardErrorMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        if(serverIsUp){
            try{
                server.sendToServer(useToolCardMove);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {

    }

}
