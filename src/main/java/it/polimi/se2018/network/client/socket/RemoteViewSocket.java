package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.server.ServerSocketInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Luciano
 */
public class RemoteViewSocket extends Observable implements Observer{

    private ServerSocketInterface server;
    private String username;

    public RemoteViewSocket(String localhost, int port){
        server = new NetworkHandler(localhost, port, this);
    }

    public RemoteViewSocket(String localhost, int port, String oldUsername){
        server = new NetworkHandler(localhost, port, this, oldUsername);
    }

    @Override
    public void update(Observable o, Object message) {
        try{
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
        username = createPlayerMessage.getPlayerName();
        System.out.println("RemoteWSocket -> Server: create player message");
        server.sendToServer(createPlayerMessage);
    }

    public void updateClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            System.out.println("RemoteWSocket -> Server: success create message");
            setChanged();
            notifyObservers(successCreatePlayerMessage);
        }
    }

    public void updateClient(SuccessMessage successMessage){
        if(successMessage.getRecipient().equals(username)){
            System.out.println("RemoteWSocket -> Server: success message");
            setChanged();
            notifyObservers(successMessage);
        }
    }

    public void updateClient(ErrorMessage errorMessage){
        if(errorMessage.getRecipient().equals(username) || server.getAddress().equals(errorMessage.getRecipient())){
            if(errorMessage.toString().equals("NotValidUsername")){
                System.out.println("RemoteWSocket -> Server: error message not valid username");
                username = "";
                setChanged();
                notifyObservers(errorMessage);
            }
            if(errorMessage.toString().equals("PlayerNumberExceeded")){
                System.out.println("RemoteWSocket -> Server: error message player number exceeded");
                setChanged();
                notifyObservers(errorMessage);
            }
        }
    }
}