package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Luciano
 */
public class VirtualClientSocket extends Thread implements VirtualClientInterface{

    private Server server;
    private Socket clientConnection;
    private ObjectOutputStream writer;
    private ObjectInputStream inputStream;
    private VirtualViewSocket virtualViewSocket;
    private boolean isConnected;
    private String username;

    public VirtualClientSocket(Server server, Socket clientConnection){
        this.server = server;
        this.clientConnection = clientConnection;
        this.isConnected = true;
        try{
            writer = new ObjectOutputStream(clientConnection.getOutputStream());
            inputStream = new ObjectInputStream(clientConnection.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        virtualViewSocket = new VirtualViewSocket(this);
        virtualViewSocket.addObserver(server.getController());
        server.getController().addObserver(virtualViewSocket);
        server.getController().addObserverToModel(virtualViewSocket);
        username = this.toString();
    }

    public void resetOldPlayer(ComebackSocketMessage message) throws PlayerNotFoundException{
        for (VirtualViewInterface client: server.getPlayers()) {
            if(client.getUsername().equals((message).getUsername())){
                server.getPlayers().remove(client);
                server.addClient(this.virtualViewSocket);
            }
        }
        throw new PlayerNotFoundException();
    }

    @Override
    public void run(){
        Message message = null;
        try {
            isConnected = true;
            Object o;
            while (isConnected){
                try{
                    message = (Message) inputStream.readObject();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e){
                    System.out.println("Player: " + username +" disconnected");
                    this.isConnected = false;
                }
                if(message == null){
                    this.isConnected = false;
                }else {
                    System.out.println("VCSocket -> VWSocket: " + message.toString());
                    try {
                        Method updateServer = virtualViewSocket.getClass().getMethod("updateServer", message.getClass());
                        updateServer.invoke(virtualViewSocket, message);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            clientConnection.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void notifyClient(Message message){
        System.out.println("VirtualClientSocket -> Client: " + message.toString());
        if(isConnected){
            try{
                writer.writeObject(message);
            } catch (IOException e) {
                isConnected = false;
                System.out.println("Player #" + server.getPlayers().indexOf(virtualViewSocket) + " " + username + " disconnected");
            }
        }
    }

    public void notifyClient(ChooseSchemaMessage chooseSchemaMessage){
        System.out.println("VirtualClientSocket -> Client: " + chooseSchemaMessage.toString());
        if(isConnected){
            try{
                writer.writeObject(chooseSchemaMessage);
            } catch (IOException e) {
                isConnected = false;
                System.out.println("Player #" + server.getPlayers().indexOf(virtualViewSocket) + " " + username + " disconnected");
            }
        }
    }
    public void notifyClient(RequestMessage requestMessage){
        System.out.println("VirtualClientSocket -> Client: " + requestMessage.toString());
        if(isConnected){
            try{
                writer.writeObject(requestMessage);
            } catch (IOException e) {
                isConnected = false;
                System.out.println("Player #" + server.getPlayers().indexOf(virtualViewSocket) + " " + username + " disconnected");
            }
        }
    }

    public void notifyClient(ErrorMessage errorMessage){
        System.out.println("VirtualClientSocket -> Client: " + errorMessage.toString());
        if(isConnected){
            try{
                writer.writeObject(errorMessage);
            } catch (IOException e) {
                System.out.println("Player #" + server.getPlayers().indexOf(virtualViewSocket) + " " + username + " disconnected");
                isConnected = false;
            }
        }
    }

    public void notifyClient(GameInitializationMessage gameInitializationMessage){
        System.out.println("VirtualClientSocket -> Client: " + gameInitializationMessage.toString());
        if(isConnected){
            try{
                writer.writeObject(gameInitializationMessage);
            } catch (IOException e) {
                System.out.println("Player #" + server.getPlayers().indexOf(virtualViewSocket) + " " + username + " disconnected");
                isConnected = false;
            }
        }
    }

    public void notifyClient(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        System.out.println("VirtualClientSocket -> Client: " + showPrivateObjectiveCardsMessage.toString());
        if(isConnected){
            try{
                writer.writeObject(showPrivateObjectiveCardsMessage);
            } catch (IOException e) {
                isConnected = false;
                System.out.println("Player #" + server.getPlayers().indexOf(virtualViewSocket) + " " + username + " disconnected");
            }
        }
    }

    public void notifyClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        System.out.println("VirtualClientSocket -> Client: " + successCreatePlayerMessage.toString());
        if(isConnected){
            try{
                System.out.println("VirtualClientSocket sends message");
                writer.writeObject(successCreatePlayerMessage);
            } catch (IOException e) {
                isConnected = false;
                System.out.println("Player #" + server.getPlayers().indexOf(virtualViewSocket) + " " + username + " disconnected");
            }
        }
    }

    public VirtualViewSocket getVirtualViewSocket() {
        return virtualViewSocket;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public void setClientConnection(Socket clientConnection) {
        this.clientConnection = clientConnection;
        this.isConnected = true;
    }
}