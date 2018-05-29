package it.polimi.se2018.view;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.messages.MoveMessage;
import it.polimi.se2018.network.client.rmi.RemoteViewRMI;
import it.polimi.se2018.network.client.socket.ClientSocketInterface;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.ServerSocketInterface;

import java.io.InputStreamReader;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Scanner;

/**
 * @author giovanni
 */

public class View extends Observable implements Observer{

    private Scanner scanner;
    private ServerRMIInterface serverRMIInterface;
    private ServerSocketInterface serverSocketInterface;

    /**
     * Initializes view
     */
    public View(){
        scanner = new Scanner(new InputStreamReader(System.in));
    }

    /**
     *
     * @return a referece to player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *method that throws a message to controller with the description of the move
     * @param draftPoolPos  a class that contains dice in the game
     * @param row   it serves for the position of the die
     * @param col   it serves for the position of the die
     */
    public void handleDiceMove(int draftPoolPos ,int row,int col){
        notifyObservers(new ChooseDiceMove(draftPoolPos,row,col,player));
    }

    //metodo per inizializzare un giocatore
    public void createPlayer(String name){
        setChanged();   //dico che è cambiato
        notifyObservers(new CreatePlayerMessage(name));
    }

    public void handleChooseCardMove(int index){
        //notifyObservers(new );
    }

    public void handleToolCardMove(){
        //switch

    }
    public void showMessage(String message){

    }

    public void reportError(String message){

    }

    public void update(MoveMessage message){

    }

    public int demandConnectionType() {
        return scanner.nextInt();
    }

    public void startRMIConnection() {

    }

    public void startSocketConnection() {
    }

    @Override
    public void update(Observable model, Object message){

    }
}



