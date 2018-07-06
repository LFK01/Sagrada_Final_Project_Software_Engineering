package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMIInterface extends Remote{

    ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException;

    void sendToServer(ChooseDiceMove chooseDiceMove) throws RemoteException;

    void sendToServer(ChooseSchemaMessage chooseSchemaMessage) throws RemoteException;

    void sendToServer(ComebackMessage comebackMessage) throws RemoteException;

    void sendToServer(CreatePlayerMessage createPlayerMessage) throws RemoteException;

    void sendToServer(DiePlacementMessage diePlacementMessage) throws  RemoteException;

    void sendToServer(ErrorMessage errorMessage) throws RemoteException;

    void sendToServer(SendGameboardMessage sendGameboardMessage) throws RemoteException;

    void sendToServer(NoActionMove noActionMove) throws  RemoteException;

    void sendToServer(RequestMessage requestMessage) throws RemoteException;

    void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException;

    void sendToServer(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) throws RemoteException;

    void sendToServer(SuccessCreatePlayerMessage successCreatePlayerMessage) throws RemoteException;

    void sendToServer(ToolCardActivationMessage toolCardActivationMessage) throws RemoteException;

    void sendToServer(ToolCardErrorMessage toolCardErrorMessage) throws RemoteException;

    void sendToServer(UseToolCardMove useToolCardMove) throws RemoteException;

    void sendToServer(SendWinnerMessage sendWinnerMessage) throws RemoteException;
}
