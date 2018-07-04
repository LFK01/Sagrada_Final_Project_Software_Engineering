package it.polimi.se2018.network.server.client_gatherer;

import it.polimi.se2018.network.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGathererSocket extends Thread{
    private final Server server;
    private ServerSocket serverSocket;

    public ClientGathererSocket(Server server, int PORT){
        this.server = server;
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
                server.addClient(newClientConnection);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
