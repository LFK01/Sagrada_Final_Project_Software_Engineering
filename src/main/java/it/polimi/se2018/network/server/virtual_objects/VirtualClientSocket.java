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
    private Message lastSentMessage = new ErrorMessage("defaultMessage",
            "defaultMessage", "defaultMessage");
    private boolean messageIsValid;

    private boolean isConnected;

    private String username;

    public VirtualClientSocket(Server server, Socket clientConnection){
        this.server = server;
        this.clientConnection = clientConnection;
        this.isConnected = true;
        this.messageIsValid = false;
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
                } catch (IOException | ClassNotFoundException e){
                    this.isConnected = false;
                }
                if(message == null){
                    this.isConnected = false;
                } else {
                    System.out.println("message class: " + message.getClass().toString());
                    if(message.getClass().equals(ErrorMessage.class) &&
                            message.toString().equalsIgnoreCase("quit") &&
                            message.getSender().equalsIgnoreCase(username)){
                        messageIsValid = false;
                    }
                    if (lastSentMessage.getSender().equalsIgnoreCase("defaultMessage")) {
                        messageIsValid = true;
                    }
                    if (lastSentMessage.hashCode() == message.hashCode()){
                        messageIsValid = false;
                    }
                    if (messageIsValid) {
                        try {
                            lastSentMessage = message;
                            Method updateServer = virtualViewSocket.getClass().getMethod("updateServer", message.getClass());
                            updateServer.invoke(virtualViewSocket, message);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            System.out.println("Virtual View Socket miss a method");
                        }
                    } else {
                        this.isConnected = false;
                    }
                }
            }
            clientConnection.close();
        } catch (IOException e){
            System.out.println("unable to close connection");
        } finally {
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
            System.out.println("calling removeClient from virtualClientSocket 1");
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(RequestMessage requestMessage){
        try{
            writer.writeObject(requestMessage);
        } catch (IOException e) {
            System.out.println("calling removeClient from virtualClientRmi 2");
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(ErrorMessage errorMessage){
        try{
            writer.writeObject(errorMessage);
        } catch (IOException e) {
            System.out.println("calling removeClient from virtualClientRmi 3");
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(SendGameboardMessage sendGameboardMessage){
        try{
            writer.writeObject(sendGameboardMessage);
        } catch (IOException e) {
            System.out.println("calling removeClient from virtualClientRmi 4");
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage){
        try{
            writer.writeObject(showPrivateObjectiveCardsMessage);
        } catch (IOException e) {
            System.out.println("calling removeClient from virtualClientRmi 5");
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(SuccessCreatePlayerMessage successCreatePlayerMessage){
        try{
            writer.writeObject(successCreatePlayerMessage);
        } catch (IOException e) {
            System.out.println("calling removeClient from virtualClientRmi 6");
            server.removeClient(virtualViewSocket);
        }
    }

    public void notifyClient(SendWinnerMessage sendWinnerMessage){
        try{
            writer.writeObject(sendWinnerMessage);
        } catch (IOException e) {
            System.out.println("calling removeClient from virtualClientRmi 7");
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

    public boolean isConnected() {
        return isConnected;
    }
}