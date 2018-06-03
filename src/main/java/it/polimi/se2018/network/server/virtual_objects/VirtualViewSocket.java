package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Observable;

public class VirtualViewSocket extends Observable implements VirtualViewInterface {

    private VirtualClientSocket virtualClientSocket;

    public VirtualViewSocket(VirtualClientSocket virtualClientSocket){
        this.virtualClientSocket = virtualClientSocket;
    }


    @Override
    public void update(Observable o, Object message) {
        try {
            Method notifyClient = virtualClientSocket.getClass().getMethod("notifyClient", message.getClass());
            notifyClient.invoke(virtualClientSocket, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void updateServer(CreatePlayerMessage message){
        boolean correctUsername = true;
        System.out.println("check username usage on " + virtualClientSocket.getServer().getPlayers().size() + " clients");
        if(virtualClientSocket.getServer().getPlayers().size()>1){
            System.out.println("desidered username: " + message.getPlayerName());
            for(VirtualViewInterface client: virtualClientSocket.getServer().getPlayers()){
                System.out.println("client #" + (virtualClientSocket.getServer().getPlayers().indexOf(client)+1) + " username: " + client.getUsername());
                if(client!=this){
                    if(client.getUsername().equals(message.getPlayerName())){
                        update(this, new ErrorMessage(virtualClientSocket.getServer().toString(), message.getPlayerName(), "NotValidUsername"));
                        correctUsername = false;
                    }
                }
            }
        } else {
            correctUsername = true;
        }
        if(correctUsername){
            virtualClientSocket.setUsername(message.getPlayerName());
            System.out.println("VWSocket -> Controller: " + message.toString());
            setChanged();
            notifyObservers(message);
        }
    }

    public void updateServer(ComebackSocketMessage message){
        try{
            virtualClientSocket.resetOldPlayer(message);
            setChanged();
            notifyObservers(message);
        } catch (PlayerNotFoundException e){
            virtualClientSocket.notifyClient(new ErrorMessage("server", virtualClientSocket.getUsername(), "Player not found"));
        }
    }

    @Override
    public String getUsername() {
        return virtualClientSocket.getUsername();
    }

    @Override
    public void setClientConnection(Socket clientConnection) {
        virtualClientSocket.setClientConnection(clientConnection);
    }

    public VirtualClientSocket getClientSocket() {
        return virtualClientSocket;
    }

    public void setClientSocket(VirtualClientSocket clientSocket) {
        this.virtualClientSocket = clientSocket;
    }
}
