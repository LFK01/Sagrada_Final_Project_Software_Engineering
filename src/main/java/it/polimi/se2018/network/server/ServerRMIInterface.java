package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMIInterface extends Remote{

    ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException;

    void sendToServer(Message message) throws RemoteException;

    void sendToServer(ChooseSchemaMessage chooseSchemaMessage) throws RemoteException;

    void sendToServer(ComebackMessage comebackMessage) throws RemoteException;

    void sendToServer(ComebackSocketMessage comebackSocketMessage) throws RemoteException;

    void sendToServer(CreatePlayerMessage createPlayerMessage) throws RemoteException;

    void sendToServer(ErrorMessage errorMessage) throws RemoteException;

    void sendToServer(NewRoundMessage newRoundMessage) throws RemoteException;

    void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException;

    void sendToServer(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) throws RemoteException;

    void sendToServer(SuccessCreatePlayerMessage successCreatePlayerMessage) throws RemoteException;

    void sendToServer(SuccessMessage successMessage) throws RemoteException;

    void sendToServer(SuccessMoveMessage successMoveMessage) throws RemoteException;

    void sendToServer(UpdateTurnMessage updateTurnMessage) throws RemoteException;

    void sendToServer(GameInitializationMessage gameInitializationMessage) throws RemoteException;

    ServerRMIInterface retrieveOldClient(ClientRMIInterface newClient, String username) throws RemoteException, PlayerNotFoundException;
}
