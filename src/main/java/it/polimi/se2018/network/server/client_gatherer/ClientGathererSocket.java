package it.polimi.se2018.network.server.client_gatherer;

import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
            try{
                newClientConnection = serverSocket.accept();
                System.out.println("Socket address server side: " + newClientConnection.getLocalAddress().toString());
                server.addClient(newClientConnection);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
