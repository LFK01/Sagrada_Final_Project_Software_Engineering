package it.polimi.se2018.network.server.client_gatherer;

import it.polimi.se2018.model.events.messages.ComebackSocketMessage;
import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.Server;
import it.polimi.se2018.network.server.virtual_objects.VirtualClientInterface;
import it.polimi.se2018.network.server.virtual_objects.VirtualViewInterface;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGathererSocket extends Thread{
    private final Server server;
    private final int PORT;
    private ServerSocket serverSocket;

    public ClientGathererSocket(Server server, int PORT){
        this.server = server;
        this.PORT = PORT;
        try{
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){
            Socket newClientConnection;
            ComebackSocketMessage message = null;
            try{
                newClientConnection = serverSocket.accept();
                /*System.out.println("ClientGathererSocket found new connection.");
                try{
                    ObjectOutputStream temporaryOutputStream = new ObjectOutputStream(newClientConnection.getOutputStream());
                    temporaryOutputStream.writeObject("ConnectionEnabled");
                    System.out.println("handshake sent.");
                } catch (IOException e){
                    e.printStackTrace();
                }
                try{
                    ObjectInputStream temporaryInputStream = new ObjectInputStream(newClientConnection.getInputStream());
                    message = (ComebackSocketMessage) temporaryInputStream.readObject();
                    System.out.println("New connection is a comeback connection.");
                    server.resetOldClientSocket(newClientConnection, message.getUsername());
                } catch (ClassNotFoundException | ClassCastException e ) {
                    System.out.println("New connection is a new game connection.");
                    server.addClient(newClientConnection);
                }*/
                server.addClient(newClientConnection);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
