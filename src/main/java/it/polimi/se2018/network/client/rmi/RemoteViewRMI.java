package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.server.ServerRMIInterface;
import sun.dc.pr.PRError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public class RemoteViewRMI extends Observable implements ClientRMIInterface, Observer {

    private ServerRMIInterface server;
    private String username;
    private boolean serverIsUp;

    public RemoteViewRMI() throws RemoteException{
    }

    @Override
    public void updateClient(Message message) throws RemoteException {
        try{
            Method notifyView = this.getClass().getMethod("notifyView", message.getClass());
            notifyView.invoke(this, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void notifyView(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            setChanged();
            notifyObservers(successCreatePlayerMessage);
        }
    }
    public void notifyView(ErrorMessage errorMessage){
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
    public void notifyView(ChooseSchemaMessage chooseSchemaMessage){
        setChanged();
        notifyObservers(chooseSchemaMessage);
    }



    /**
     * Override of Observer update method
     * @param o sender
     * @param message notified object
     */
    @Override
    public void update(Observable o, Object message) {
        try{
            Method sendToServer = getClass().getDeclaredMethod("sendToServer", message.getClass());
            sendToServer.invoke(this, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void sendToServer(CreatePlayerMessage createPlayerMessage){
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

    public void setServer(ServerRMIInterface server) {
        this.server = server;
        serverIsUp = true;
    }

    private void sendToServer(SelectedSchemaMessage selectedSchemaMessage){
        if(serverIsUp){
            try{
                System.out.println("RemoteVRMI -> Server: " + selectedSchemaMessage.toString());
                server.sendToServer(selectedSchemaMessage);
            } catch (RemoteException e){
                serverIsUp = false;
            }
        }
    }



}
