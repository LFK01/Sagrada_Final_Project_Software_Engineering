package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.messages.ComebackMessage;
import it.polimi.se2018.model.events.messages.ComebackSocketMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.ServerSocketInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author Luciano
 */
public class NetworkHandler extends Thread implements ServerSocketInterface {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private RemoteViewSocket client;
    private boolean serverIsUp;

    public NetworkHandler(String localhost, int port, RemoteViewSocket remoteViewSocket) {
        socketSetUp(localhost, port, remoteViewSocket);
    }

    public NetworkHandler(String localhost, int port, RemoteViewSocket remoteViewSocket, String oldUsername){
        socketSetUp(localhost, port, remoteViewSocket);
        try{
            outputStream.writeObject(new ComebackSocketMessage(oldUsername, "server", oldUsername));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void socketSetUp(String localhost, int port, RemoteViewSocket remoteViewSocket){
        try{
            socket = new Socket(localhost, port);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.client = remoteViewSocket;
            this.serverIsUp = true;
            this.start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        boolean loop = true;
        while (loop){
            if(serverIsUp){
                try{
                    Message message = (Message) inputStream.readObject();
                    if(message == null){
                        loop=false;
                    }else {
                        try{
                            Method updateClient = client.getClass().getMethod("updateClient", message.getClass());
                            updateClient.invoke(client, message);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (ClassNotFoundException | IOException e){
                    System.out.println("Server disconnected.");
                    serverIsUp = false;
                }
            }
        }
        stopConnection();
    }

    @Override
    public void sendToServer(Message message) {
        try {
            System.out.println("NetworkHandler -> Server");
            outputStream.writeObject((Object) message);
        } catch (IOException e){
            serverIsUp = false;
            e.printStackTrace();
        }
    }

    @Override
    public String getAddress() {
        return socket.getLocalSocketAddress().toString();
    }

    public void stopConnection(){
        if(!this.socket.isClosed()){
            try{
                this.socket.close();
                System.out.println("Connection closed.");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
