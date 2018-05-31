package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.ServerRMIInterface;
import sun.dc.pr.PRError;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public class RemoteViewRMI extends Observable implements ClientRMIInterface, Observer {

    private ServerRMIInterface server;
    private String username;

    public RemoteViewRMI() throws RemoteException{
    }

    @Override
    public void updateClient(Message message) throws RemoteException {
        System.out.println("RemoteViewRMI 3");
        if(message.getRecipient().equals(username)){
            System.out.println("RemoteViewRMI 4");
            setChanged();
            notifyObservers(message);
        }
    }

    /**
     * Override of Observer update method
     * @param o sender
     * @param message notified object, has to be of Message type. Used to
     *                know which kind of action is being performed
     */

    @Override
    public void update(Observable o, Object message) {
        if(message instanceof CreatePlayerMessage){
            System.out.println("RemoteView 1");
            username = ((CreatePlayerMessage)message).getPlayerName();
        }
        try{
            System.out.println("RemoteView 2");
            server.sendToServer((Message)message);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void setServer(ServerRMIInterface server) {
        this.server = server;
    }
}
