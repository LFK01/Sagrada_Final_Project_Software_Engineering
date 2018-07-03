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

    @Override
    public void run(){
        Message message = null;
        try {
            isConnected = true;
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
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(Message message){
        try{
            writer.writeObject(message);
        } catch (IOException e) {
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(ChooseSchemaMessage chooseSchemaMessage){
        try{
            writer.writeObject(chooseSchemaMessage);
        } catch (IOException e) {
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(RequestMessage requestMessage){
        try{
            writer.writeObject(requestMessage);
        } catch (IOException e) {
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(ErrorMessage errorMessage){
        try{
            writer.writeObject(errorMessage);
        } catch (IOException e) {
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(SendGameboardMessage sendGameboardMessage){
        try{
            writer.writeObject(sendGameboardMessage);
        } catch (IOException e) {
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        try{
            writer.writeObject(showPrivateObjectiveCardsMessage);
        } catch (IOException e) {
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        try{
            writer.writeObject(successCreatePlayerMessage);
        } catch (IOException e) {
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(SendWinnerMessage sendWinnerMessage){
        try{
            writer.writeObject(sendWinnerMessage);
        } catch (IOException e) {
            server.removeClient(virtualViewSocket);
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

}