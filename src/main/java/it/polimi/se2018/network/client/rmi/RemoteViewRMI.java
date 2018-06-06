package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;
import sun.dc.pr.PRError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public class RemoteViewRMI extends ProjectObservable implements ClientRMIInterface,  ProjectObserver {

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
            setChanged();
            notifyObservers(message);
        }
    }

    public void notifyView(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(successCreatePlayerMessage);
        }
    }

    private void notifyView(ErrorMessage errorMessage){
        if(errorMessage.getRecipient().equals(username)){
            if(errorMessage.toString().equals("NotValidUsername")){
                setChanged();
                notifyObservers(errorMessage);
            }
            if(errorMessage.toString().equals("PlayerNumberExceeded")){
                /*should never be called here for RMI connection*/
            }
        }
    }

    private void notifyView(ChooseSchemaMessage chooseSchemaMessage){
        if(chooseSchemaMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(chooseSchemaMessage);
        }
    }

    private void notifyView(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        if(showPrivateObjectiveCardsMessage.getRecipient().equals(username) || showPrivateObjectiveCardsMessage.getRecipient().equals("all")){
            setChanged();
            notifyObservers(showPrivateObjectiveCardsMessage);
        }
    }

    private void notifyView(DemandSchemaCardMessage demandSchemaCardMessage){
        if(demandSchemaCardMessage.getRecipient().equals(username) || demandSchemaCardMessage.getRecipient().equals("all")){
            setChanged();
            notifyObservers(demandSchemaCardMessage);
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
    public void update(DemandSchemaCardMessage demandSchemaCardMessage) {
        if(serverIsUp){
            try{
                System.out.println("RemoteVRMI -> Server: " + demandSchemaCardMessage.toString());
                server.sendToServer(demandSchemaCardMessage);
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
    public void update(UpdateTurnMessage updateTurnMessage) {
        if(serverIsUp){
            try{
                System.out.println("RemoteVRMI -> Server: " + updateTurnMessage.toString());
                server.sendToServer(updateTurnMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }
}
