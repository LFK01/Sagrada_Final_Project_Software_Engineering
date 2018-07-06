package it.polimi.se2018.network.server.client_gatherer;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.exceptions.PlayerNumberExceededException;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientGathererRMI extends UnicastRemoteObject implements ServerRMIInterface {

    private Server server;

    public ClientGathererRMI(Server server) throws RemoteException {
        this.server = server;
    }

    @Override
    public ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException {
        if (server.getVirtualViewInterfacesList().size() < 4) {
            VirtualViewRMI newVirtualView = new VirtualViewRMI(newClient, server);
            server.getController().addObserver(newVirtualView);
            newVirtualView.addObserver(server.getController());
            server.getController().addObserverToModel(newVirtualView);
            ServerRMIInterface remoteServerRef = (ServerRMIInterface)
                    UnicastRemoteObject.exportObject(newVirtualView.getVirtualClientRMI(), 0);
            server.addClient(newVirtualView);
            return remoteServerRef;
        } else {
            throw new PlayerNumberExceededException();
        }
    }

    @Override
    public void sendToServer(ComebackMessage comebackMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(CreatePlayerMessage createPlayerMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(DiePlacementMessage diePlacementMessage) throws RemoteException {
        /*should never be called here*/
    }

    @Override
    public void sendToServer(ErrorMessage errorMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(ToolCardActivationMessage toolCardActivationMessage) throws RemoteException {
        /*should be handled by RemoteViewRMI*/
    }

    @Override
    public void sendToServer(ToolCardErrorMessage toolCardErrorMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(ChooseToolCardMessage chooseToolCardMessage) throws RemoteException {
        /*should be handled by RemoteViewRMI*/
    }

    @Override
    public void sendToServer(NoActionMessage noActionMessage) {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(RequestMessage requestMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(ChooseDiceMessage chooseDiceMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

}