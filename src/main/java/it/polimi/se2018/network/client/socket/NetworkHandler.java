package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.messages.ComebackSocketMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.ServerSocketInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Luciano
 */
public class NetworkHandler extends Thread implements ServerSocketInterface {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private RemoteViewSocket remoteViewSocket;
    private boolean serverIsUp;

    public NetworkHandler(String localhost, int port, RemoteViewSocket remoteViewSocket) {
        socketSetUp(localhost, port, remoteViewSocket);
        /*String message = "";
        System.out.println("NetworkHandler setup...");
        try{
            message = (String) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(message.equals("ConnectionEnabled")){
            serverIsUp = true;
            this.start();
        }*/
        this.start();
        serverIsUp = true;
        System.out.println("Network handler started.");
    }

    public NetworkHandler(String localhost, int port, RemoteViewSocket remoteViewSocket, String oldUsername){
        socketSetUp(localhost, port, remoteViewSocket);
        System.out.println("Socket comeback: network handler created, socket set up");
        String message = "";
        try{
            message = (String) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(message.equals("ConnectionEnabled")){
            System.out.println("handshake received.");
            serverIsUp = true;
            this.start();
            try{
                outputStream.writeObject(new ComebackSocketMessage(oldUsername, "server", oldUsername));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void socketSetUp(String localhost, int port, RemoteViewSocket remoteViewSocket){
        try{
            socket = new Socket(localhost, port);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.remoteViewSocket = remoteViewSocket;
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
                            Method notifyView = remoteViewSocket.getClass().getMethod("notifyView", message.getClass());
                            notifyView.invoke(remoteViewSocket, message);
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
            outputStream.writeObject(message);
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
