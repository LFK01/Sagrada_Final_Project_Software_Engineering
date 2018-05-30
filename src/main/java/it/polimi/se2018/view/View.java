package it.polimi.se2018.view;
import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.SuccessMessage;
import it.polimi.se2018.model.events.messages.SuccessMoveMessage;
import it.polimi.se2018.model.events.messages.UpdateTurnMessage;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.network.server.ServerRMIInterface;
import it.polimi.se2018.network.server.ServerSocketInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author giovanni
 */

public class View extends Observable implements Observer{

    private Scanner scanner;
    private ServerRMIInterface serverRMIInterface;
    private ServerSocketInterface serverSocketInterface;
    private String username;
    private boolean isPlayerTurn;
    /**
     * Initializes view
     */
    public View(){
        scanner = new Scanner(new InputStreamReader(System.in));
    }

    public boolean IsPlayerTurn(){
        return this.isPlayerTurn;
    }

    public void setFalsePlayerTurn() {
        isPlayerTurn =false;
    }

    /**
     *method that throws a message to controller with the description of the move
     * @param draftPoolPos  a class that contains dice in the game
     * @param row   it serves for the position of the die
     * @param col   it serves for the position of the die
     */
    public void handleDiceMove(int draftPoolPos ,int row,int col){
        notifyObservers(new ChooseDiceMove(username, "server", draftPoolPos, row, col));
    }

    //metodo per inizializzare un giocatore
    public void createPlayer(){
        JFrame frameCreatePlayer = new JFrame("New Player");
        Container containerCreatePlayer = new Container();
        GridLayout layourCreatePlayer = new GridLayout(3,1);
        frameCreatePlayer.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameCreatePlayer.setSize(300, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frameCreatePlayer.setLocation(dim.width/2-frameCreatePlayer.getSize().width/2, dim.height/2-frameCreatePlayer.getSize().height/2);
        frameCreatePlayer.add(containerCreatePlayer);
        containerCreatePlayer.setLayout(layourCreatePlayer);
        JLabel instructionLabel = new JLabel("Username:", JLabel.LEFT);
        JTextField usernameTextField = new JTextField("username", JTextField.CENTER);
        usernameTextField.setEnabled(true);
        JButton confirmButton = new JButton("OK");
        confirmButton.setEnabled(true);
        confirmButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                username = usernameTextField.getText();
                setChanged();
                notifyObservers(new CreatePlayerMessage(username, "server", username));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        containerCreatePlayer.add(instructionLabel);
        containerCreatePlayer.add(usernameTextField);
        containerCreatePlayer.add(confirmButton);
        frameCreatePlayer.setVisible(true);
        //frameCreatePlayer.pack();
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

    public int demandConnectionType() {
        JFrame frameDemandConnection = new JFrame("Parent Window");
        Object[] options = {"RMI", "Socket"};
        int n = JOptionPane.showOptionDialog(frameDemandConnection, "Scegliere tipo di connessione desiderata:", "Scelta connessione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        if(n == JOptionPane.YES_OPTION){
            frameDemandConnection.dispose();
            return 1;
        }else{
            frameDemandConnection.dispose();
            return 2;
        }
    }

    @Override
    public void update(Observable model, Object message){

        if(message instanceof SuccessMoveMessage) {
            System.out.println("Giocatore creato" + ((CreatePlayerMessage) message).getPlayerName());
            //fai vedere la schemacard aggiornata con i dati della schemacard fornita dal messaggio show gameboard

        }

        if(message instanceof SuccessMessage){
            setFalsePlayerTurn();
        }

        if(message instanceof UpdateTurnMessage){
            //tocca al prossimo giocatore
        }

    }

    public void playerNumberExceededDialog() {
        JFrame framePlayerNumberExceeded = new JFrame();
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(framePlayerNumberExceeded, "Connection Failed", "Player number limit already reached, unable to add another player", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
    }
}



