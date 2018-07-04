package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.network.server.ServerSocketInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
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

    public NetworkHandler(String localhost, int port, RemoteViewSocket remoteViewSocket) throws ConnectException{
        socketSetUp(localhost, port, remoteViewSocket);
        this.start();
        serverIsUp = true;
    }

    private void socketSetUp(String localhost, int port, RemoteViewSocket remoteViewSocket) throws ConnectException{
        try{
            socket = new Socket(localhost, port);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.remoteViewSocket = remoteViewSocket;
        } catch (IOException e){
            throw new ConnectException();
        }
    }

    @Override
    public void run(){
        while (serverIsUp){
            try{
                Message message = (Message) inputStream.readObject();
                if(message == null){
                    serverIsUp = false;
                }else {
                    try{
                        Method notifyView = remoteViewSocket.getClass().getMethod("notifyView", message.getClass());
                        notifyView.invoke(remoteViewSocket, message);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        System.out.println("A method is missing from remoteViewSocket");
                    }
                }
            } catch (IOException e){
                remoteViewSocket.notifyView(new ErrorMessage("remoteView",
                        remoteViewSocket.getUsername(), "ServerIsDown"));
                serverIsUp = false;
            } catch (ClassNotFoundException e){
                System.out.println("Someone has sent an object that's not a Message");
            }
        }
        stopConnection();
    }

    @Override
    public void sendToServer(Message message) throws IOException {
            outputStream.writeObject(message);
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
