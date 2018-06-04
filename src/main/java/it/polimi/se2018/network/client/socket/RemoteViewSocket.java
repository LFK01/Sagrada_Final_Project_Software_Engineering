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
        System.out.println("Socket comeback: RemoteView created");
        server = new NetworkHandler(localhost, port, this, oldUsername);
    }

    @Override
    public void update(Observable o, Object message) {
        try{
            Method sendToServer = this.getClass().getDeclaredMethod("sendToServer", message.getClass());
            sendToServer.invoke(this, message);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    private void sendToServer(ComebackSocketMessage comebackSocketMessage){
        System.out.println("RemoteWSocket -> Server: comeback player message");
        server.sendToServer(comebackSocketMessage);
    }

    private void sendToServer(CreatePlayerMessage createPlayerMessage){
        username = createPlayerMessage.getPlayerName();
        System.out.println("RemoteWSocket -> Server: create player message");
        server.sendToServer(createPlayerMessage);
    }

    public void notifyView(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            System.out.println("RemoteWSocket -> Server: success create message");
            setChanged();
            notifyObservers(successCreatePlayerMessage);
        }
    }

    public void notifyView(SuccessMessage successMessage){
        if(successMessage.getRecipient().equals(username)){
            System.out.println("RemoteWSocket -> Client: success message");
            setChanged();
            notifyObservers(successMessage);
        }
    }

    public void notifyView(ChooseSchemaMessage chooseSchemaMessage){
        if(chooseSchemaMessage.getRecipient().equals(username)){
            System.out.println("RemoteWSocket -> Client: success message");
            setChanged();
            notifyObservers(chooseSchemaMessage);
        }
    }

    public void notifyView(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        if(showPrivateObjectiveCardsMessage.getRecipient().equals(username)){
            System.out.println("RemoteWSocket -> Client: success message");
            setChanged();
            notifyObservers(showPrivateObjectiveCardsMessage);
        }
    }

    public void notifyView(ErrorMessage errorMessage){
        if(errorMessage.getRecipient().equals(username) || server.getAddress().equals(errorMessage.getRecipient())){
            if(errorMessage.toString().equals("NotValidUsername")){
                System.out.println("RemoteWSocket -> Client: error message not valid username");
                username = "";
                setChanged();
                notifyObservers(errorMessage);
            }
            if(errorMessage.toString().equals("PlayerNumberExceeded")){
                System.out.println("RemoteWSocket -> Client: error message player number exceeded");
                setChanged();
                notifyObservers(errorMessage);
            }
            if(errorMessage.toString().equals("UsernameNotFound")){
                System.out.println("RemoteWSocket -> Client: error message username not found");
                setChanged();
                notifyObservers();
            }
        }
    }
}