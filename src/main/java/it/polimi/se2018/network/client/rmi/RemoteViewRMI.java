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
            System.out.println("RemoteVRMI -> View: " + chooseSchemaMessage.toString());
            setChanged();
            notifyObservers(chooseSchemaMessage);
        }
    }

    private void notifyView(ErrorMessage errorMessage){
        if(errorMessage.getRecipient().equals(username)){
            if(errorMessage.toString().equals("NotValidUsername")){
                System.out.println("RemoteVRMI -> View: " + errorMessage.toString());
                setChanged();
                notifyObservers(errorMessage);
            }
            else{
                setChanged();
                notifyObservers(errorMessage);
            }
        }
    }

    private void notifyView(GameInitializationMessage gameInitializationMessage){
        if(gameInitializationMessage.getRecipient().equals(username)){
            System.out.println("RemoteVRMI -> View: " + gameInitializationMessage.toString());
            setChanged();
            notifyObservers(gameInitializationMessage);
        }
    }

    private void notifyView(RequestMessage requestMessage){
        if(requestMessage.getRecipient().equals(username)){
            System.out.println("RemoteVRMI - > view: " + requestMessage.toString());
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
            System.out.println("RemoteVRMI -> View: " + successCreatePlayerMessage.toString());
            setChanged();
            notifyObservers(successCreatePlayerMessage);
        }
    }

    public void notifyView(SuccessMessage successMessage){
        if(successMessage.getRecipient().equals(username)){
            System.out.println("RemoteVRMI -> View: " + successMessage.toString());
            setChanged();
            notifyObservers(successMessage);
        }
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
                System.out.println("RemoteVRMI -> Server: " + chooseDiceMove.toString());
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
                System.out.println("RemoteVRMI -> Server: " + chooseSchemaMessage.toString());
                server.sendToServer(chooseSchemaMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(ComebackMessage comebackMessage) {

    }

    @Override
    public void update(ComebackSocketMessage comebackSocketMessage) {
        if(serverIsUp){
            try{
                System.out.println("RemoteVRMI -> Server: " + comebackSocketMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + createPlayerMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + diePlacementMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + errorMessage.toString());
                server.sendToServer(errorMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }


    @Override
    public void update(GameInitializationMessage gameInitializationMessage) {
        if(serverIsUp){
            try{
                System.out.println("RemoteVRMI -> Server: " + gameInitializationMessage.toString());
                server.sendToServer(gameInitializationMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {
        if(serverIsUp){
            try{
                System.out.println("RemoteVRMI -> Server: " + newRoundMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + noActionMove.toString());
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
                System.out.println("RemoteVRMI -> Server: " + requestMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + selectedSchemaMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + showPrivateObjectiveCardsMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + successMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + successCreatePlayerMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + successMoveMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + toolCardActivationMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + toolCardErrorMessage.toString());
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
                System.out.println("RemoteVRMI -> Server: " + useToolCardMove.toString());
                server.sendToServer(useToolCardMove);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }

}
