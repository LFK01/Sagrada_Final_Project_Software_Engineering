package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.utils.ProjectObservable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Observable;

public class VirtualViewSocket extends ProjectObservable implements VirtualViewInterface {

    private VirtualClientSocket virtualClientSocket;

    public VirtualViewSocket(VirtualClientSocket virtualClientSocket){
        this.virtualClientSocket = virtualClientSocket;
    }

    public void updateServer(Message message){
        System.out.println("VWSocket -> Controller: " + message.toString());
        setChanged();
        notifyObservers(message);
    }

    public void updateServer(CreatePlayerMessage createPlayerMessage){
        boolean correctUsername = true;
        if(virtualClientSocket.getServer().getPlayers().size()>1){
            for(VirtualViewInterface client: virtualClientSocket.getServer().getPlayers()){
                if(client!=this){
                    if(client.getUsername().equals(createPlayerMessage.getPlayerName())){
                        update(new ErrorMessage(virtualClientSocket.getServer().toString(), createPlayerMessage.getPlayerName(), "NotValidUsername"));
                        correctUsername = false;
                    }
                }
            }
        } else {
            correctUsername = true;
        }
        if(correctUsername){
            virtualClientSocket.setUsername(createPlayerMessage.getPlayerName());
            System.out.println("(createPlayer) VWSocket -> Controller :" + createPlayerMessage.toString());
            setChanged();
            notifyObservers(createPlayerMessage);
        }
    }

    public void updateServer(ComebackSocketMessage message){
        try{
            virtualClientSocket.resetOldPlayer(message);
            setChanged();
            System.out.println("VWSocket -> Controller: " + message.toString());
            notifyObservers(message);
        } catch (PlayerNotFoundException e){
            virtualClientSocket.notifyClient(new ErrorMessage("server", virtualClientSocket.getUsername(), "Player not found"));
        }
    }

    public void updateServer(SelectedSchemaMessage selectedSchemaMessage){
        setChanged();
        notifyObservers(selectedSchemaMessage);
    }

    @Override
    public void update(Message message) {
        System.out.println("VWSocket -> VCSocket: " + message.toString());
        virtualClientSocket.notifyClient(message);
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        System.out.println("VWSocket -> VCSocket: " + chooseSchemaMessage.toString());
        virtualClientSocket.notifyClient(chooseSchemaMessage);
    }

    @Override
    public void update(ComebackSocketMessage comebackSocketMessage) {
        System.out.println("VWSocket -> VCSocket: " + comebackSocketMessage.toString());
        virtualClientSocket.notifyClient(comebackSocketMessage);
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        System.out.println("VWSocket -> VCSocket: " + createPlayerMessage.toString());
        virtualClientSocket.notifyClient(createPlayerMessage);
    }

    @Override
    public void update(ErrorMessage errorMessage) {
        System.out.println("VWSocket -> VCSocket: " + errorMessage.toString());
        virtualClientSocket.notifyClient(errorMessage);
    }

    @Override
    public void update(GameInitializationMessage gameInitializationMessage) {
        System.out.println("VWSocket -> VCSocket: " + gameInitializationMessage.toString());
        virtualClientSocket.notifyClient(gameInitializationMessage);
    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {
        System.out.println("VWSocket -> VCSocket: " + newRoundMessage.toString());
        virtualClientSocket.notifyClient(newRoundMessage);
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        System.out.println("VWSocket -> VCSocket: " + selectedSchemaMessage.toString());
        virtualClientSocket.notifyClient(selectedSchemaMessage);
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        System.out.println("VWSocket -> VCSocket: " + showPrivateObjectiveCardsMessage.toString());
        virtualClientSocket.notifyClient(showPrivateObjectiveCardsMessage);
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        System.out.println("VWSocket -> VCSocket: " + successCreatePlayerMessage.toString());
        virtualClientSocket.notifyClient(successCreatePlayerMessage);
    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {
        System.out.println("VWSocket -> VCSocket: " + successMoveMessage.toString());
        virtualClientSocket.notifyClient(successMoveMessage);
    }

    @Override
    public void update(UpdateTurnMessage updateTurnMessage) {
        System.out.println("VWSocket -> VCSocket: " + updateTurnMessage.toString());
        virtualClientSocket.notifyClient(updateTurnMessage);
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
