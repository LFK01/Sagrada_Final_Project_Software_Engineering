package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Observable;

public class VirtualViewSocket extends Observable implements VirtualViewInterface {

    private VirtualClientSocket clientSocket;

    public VirtualViewSocket(VirtualClientSocket clientSocket){
        this.clientSocket = clientSocket;
    }


    @Override
    public void update(Observable o, Object message) {
        try {
            Method notifyClient = clientSocket.getClass().getMethod("notifyClient", message.getClass());
            notifyClient.invoke(clientSocket, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void updateServer(CreatePlayerMessage message){
        System.out.println("VWSocket -> Controller");
        setChanged();
        notifyObservers(message);
    }

    public void updateServer(ComebackSocketMessage message){
        try{
            clientSocket.resetOldPlayer(message);
            setChanged();
            notifyObservers(message);
        } catch (PlayerNotFoundException e){
            clientSocket.notifyClient(new ErrorMessage("server", clientSocket.getUsername(), "Player not found"));
        }
    }

    @Override
    public String getUsername() {
        return clientSocket.getUsername();
    }

    @Override
    public void setClientConnection(Socket clientConnection) {
        clientSocket.setClientConnection(clientConnection);
    }

    public VirtualClientSocket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(VirtualClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
