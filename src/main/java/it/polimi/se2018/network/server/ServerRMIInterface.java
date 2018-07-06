package it.polimi.se2018.network.server;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.exceptions.PlayerNumberExceededException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMIInterface extends Remote{

    ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException;

    void sendToServer(ChooseDiceMessage chooseDiceMessage) throws RemoteException;

    void sendToServer(ComebackMessage comebackMessage) throws RemoteException;

    void sendToServer(CreatePlayerMessage createPlayerMessage) throws RemoteException;

    void sendToServer(DiePlacementMessage diePlacementMessage) throws  RemoteException;

    void sendToServer(ErrorMessage errorMessage) throws RemoteException;

    void sendToServer(NoActionMessage noActionMessage) throws  RemoteException;

    void sendToServer(RequestMessage requestMessage) throws RemoteException;

    void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException;

    void sendToServer(ToolCardActivationMessage toolCardActivationMessage) throws RemoteException;

    void sendToServer(ToolCardErrorMessage toolCardErrorMessage) throws RemoteException;

    void sendToServer(ChooseToolCardMessage chooseToolCardMessage) throws RemoteException;
}
