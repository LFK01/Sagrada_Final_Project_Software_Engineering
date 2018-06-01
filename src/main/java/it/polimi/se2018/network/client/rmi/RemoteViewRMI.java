package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.Message;
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
        if(message.getRecipient().equals(username)){
            System.out.println("RemoteViewRMI -> View");
            setChanged();
            notifyObservers(message);
        }
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
}
