package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.server.ServerSocketInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Luciano
 */
public class RemoteViewSocket extends Observable implements ClientSocketInterface, Observer{

    private ServerSocketInterface server;
    private String username;

    public RemoteViewSocket(String localhost, int port){
        server = new NetworkHandler(localhost, port, this);
    }

    public RemoteViewSocket(String localhost, int port, String oldUsername){
        server = new NetworkHandler(localhost, port, this, oldUsername);
    }

    @Override
    public void updateClient(Message message) {
        System.out.println("VWSocket -> View");
        if(message.getRecipient().equals(username)){
            setChanged();
            notifyObservers(message);
        }
    }

    @Override
    public void update(Observable o, Object message) {
        try{
            System.out.println("RemoteVSocket tries to invoke a method " + message.getClass().toString());
            Method sendToServer = this.getClass().getMethod("sendToServer", message.getClass());
            sendToServer.invoke(this, message);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    public void sendToServer(ComebackSocketMessage comebackSocketMessage){
        System.out.println("RemoteWSocket -> Server: comeback player message");
        server.sendToServer(comebackSocketMessage);
    }

    public void sendToServer(CreatePlayerMessage createPlayerMessage){
        System.out.println("RemoteWSocket -> Server: create player message");
        server.sendToServer(createPlayerMessage);
    }

    public void updateClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        System.out.println("RemoteWSocket -> Server: success create message");
        setChanged();
        notifyObservers(successCreatePlayerMessage);
    }

    public void updateClient(SuccessMessage successMessage){
        System.out.println("RemoteWSocket -> Server: success message");
        setChanged();
        notifyObservers(successMessage);
    }

}