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
        System.out.println("New VirtualClientSocket...");
        this.server = server;
        this.clientConnection = clientConnection;
        this.isConnected = true;
        try{
            writer = new ObjectOutputStream(clientConnection.getOutputStream());
            inputStream = new ObjectInputStream(clientConnection.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Handshake...");
        virtualViewSocket = new VirtualViewSocket(this);
        virtualViewSocket.addObserver(server.getController());
        server.getController().addObserver(virtualViewSocket);
        server.getController().getModel().addObserver(virtualViewSocket);
    }

    @Override
    public void run(){
        Message message = null;
        try {
            isConnected = true;
            while (isConnected){
                try{
                    message = (Message) inputStream.readObject();
                    System.out.println("VCSocket -> Server: " + message.toString());
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e){
                    System.out.println("Player: " + username +" disconnected");
                    this.isConnected = false;
                }
                if(message == null){
                    this.isConnected = false;
                }else {
                    try{
                        Method updateServer = virtualViewSocket.getClass().getMethod("updateServer", message.getClass());
                        updateServer.invoke(virtualViewSocket, message);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
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


    public void resetOldPlayer(ComebackSocketMessage message) throws PlayerNotFoundException{
        for (VirtualViewInterface client: server.getPlayers()) {
            if(client.getUsername().equals((message).getUsername())){
                server.getPlayers().remove(client);
                server.addClient(this.virtualViewSocket);
            }
        }
        throw new PlayerNotFoundException();
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
