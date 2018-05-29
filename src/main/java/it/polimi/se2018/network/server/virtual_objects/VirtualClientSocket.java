package it.polimi.se2018.network.server.virtual_objects;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class VirtualClientSocket extends Thread {

    private Server server;
    private Socket clientConnection;
    private ObjectOutputStream writer;
    private ObjectInputStream inputStream;
    private VirtualViewSocket virtualViewSocket;

    public VirtualClientSocket(Server server, Socket clientConnection){
        this.server = server;
        this.clientConnection = clientConnection;
        try{
            writer = new ObjectOutputStream(clientConnection.getOutputStream());
            inputStream = new ObjectInputStream(clientConnection.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        virtualViewSocket = new VirtualViewSocket(this);
        virtualViewSocket.addObserver(server.getController());
        server.getController().addObserver(virtualViewSocket);
        server.getController().getModel().addObserver(virtualViewSocket);
    }

    @Override
    public void run(){
        Message message = null;
        try {
            boolean loop = true;
            while (loop){
                try{
                    message = (Message) inputStream.readObject();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
                if(message == null){
                    loop = false;
                }else {
                    virtualViewSocket.updateServer(message);
                }
            }
            clientConnection.close();
            server.removeClient(virtualViewSocket);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void notifyClient(Message message){
        try {
            writer.writeObject(message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public VirtualViewSocket getVirtualViewSocket() {
        return virtualViewSocket;
    }
}
