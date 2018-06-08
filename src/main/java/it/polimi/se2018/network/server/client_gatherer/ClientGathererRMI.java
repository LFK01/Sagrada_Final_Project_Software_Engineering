package it.polimi.se2018.network.server.client_gatherer;

import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.network.client.rmi.ClientRMIInterface;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.excpetions.PlayerNotFoundException;
import it.polimi.se2018.network.server.excpetions.PlayerNumberExceededException;
import it.polimi.se2018.network.server.virtual_objects.VirtualClientRMI;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewInterface;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewRMI;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewSocket;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientGathererRMI extends UnicastRemoteObject implements ServerRMIInterface {

    private Server server;

    public ClientGathererRMI(Server server) throws RemoteException {
        this.server = server;
    }

    @Override
    public ServerRMIInterface addClient(ClientRMIInterface newClient) throws RemoteException, PlayerNumberExceededException {
        if (server.getPlayers().size() < 4) {
            VirtualViewRMI newVirtualView = new VirtualViewRMI(newClient, server);
            server.getController().addObserver(newVirtualView);
            newVirtualView.addObserver(server.getController());
            server.getController().getModel().addObserver(newVirtualView);
            ServerRMIInterface remoteServerRef = null;
            try {
                remoteServerRef = (ServerRMIInterface) UnicastRemoteObject.exportObject(newVirtualView.getVirtualClientRMI(), 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            server.addClient(newVirtualView);
            System.out.println("new client: " + newVirtualView.toString() + "added to serverList.");
            return remoteServerRef;
        } else {
            throw new PlayerNumberExceededException("Player number limit already reached.");
        }
    }

    @Override
    public void sendToServer(Message message) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(ChooseSchemaMessage chooseSchemaMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(ComebackMessage comebackMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(ComebackSocketMessage comebackSocketMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(CreatePlayerMessage createPlayerMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(DemandSchemaCardMessage demandSchemaCardMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(ErrorMessage errorMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(NewRoundMessage newRoundMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(SelectedSchemaMessage selectedSchemaMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(SuccessCreatePlayerMessage successCreatePlayerMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(SuccessMessage successMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(SuccessMoveMessage successMoveMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(UpdateTurnMessage updateTurnMessage) throws RemoteException {
        /*method to be called in VirtualClientRMI*/
    }

    @Override
    public void sendToServer(GameInitializationMessage gameInitializationMessage) throws RemoteException {

    }

    @Override
    public ServerRMIInterface retrieveOldClient(ClientRMIInterface newClient, String username) throws RemoteException, PlayerNotFoundException {
        boolean playerFound = false;
        ServerRMIInterface remoteRef = null;
        for (VirtualViewInterface client : server.getPlayers()) {
            if (client.getUsername().equals(username)) {
                playerFound = true;
                client = new VirtualViewRMI(newClient, username, server);
                ((VirtualViewRMI) client).addObserver(server.getController());
                remoteRef = (ServerRMIInterface) UnicastRemoteObject.exportObject(((VirtualViewRMI) client).getVirtualClientRMI(), 0);
            }
        }
        if (!playerFound) {
            throw new PlayerNotFoundException();
        } else {
            return remoteRef;
        }
    }
}