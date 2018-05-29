package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.ServerSocketInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandler extends Thread implements ServerSocketInterface {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private RemoteViewSocket client;

    public NetworkHandler(String localhost, int port, RemoteViewSocket remoteViewSocket) {
        try{
            socket = new Socket(localhost, port);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.client = remoteViewSocket;
            this.start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        boolean loop = true;
        while (loop){
            try{
                Message message = (Message) inputStream.readObject();
                if(message == null){
                    loop=false;
                }else {
                    client.updateClient(message);
                }
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendToServer(Message message) {
        try {
            outputStream.writeObject((Object) message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stopConnection(){
        if(!this.socket.isClosed()){
            try{
                this.socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
