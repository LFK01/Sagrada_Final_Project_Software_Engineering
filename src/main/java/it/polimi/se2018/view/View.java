package it.polimi.se2018.view;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import javax.swing.*;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author giovanni
 */
public class View extends Observable implements Observer{

    protected String username;
    private boolean isPlayerTurn;
    private String localAddress;

    private Scanner scanner;

    /**
     * Initializes view
     */
    public View(){
        scanner = new Scanner(new InputStreamReader(System.in));
        try{
            localAddress = InetAddress.getLocalHost().getHostAddress();
        } catch(UnknownHostException e){
            e.printStackTrace();
        }
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

    /**
     * shows up a login window where the user can choose her/his name
     */
    public void createPlayer(){
        //TODO when called by socket connection we have to check player number is lower than 4
        /*JFrame frameCreatePlayer = new JFrame("New Player");
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
        usernameTextField.setFocusable(true);

        usernameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    username = usernameTextField.getText();
                    frameCreatePlayer.dispose();
                    setChanged();
                    notifyObservers(new CreatePlayerMessage(username, "server", username));
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {            }
            @Override
            public void keyReleased(KeyEvent e) {            }
        });

        usernameTextField.addMouseListener(new MouseListener() {
            boolean firstPressed = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!firstPressed){
                    firstPressed=true;
                    usernameTextField.setText("");
                }}
            @Override
            public void mousePressed(MouseEvent e) {            }
            @Override
            public void mouseReleased(MouseEvent e) {            }
            @Override
            public void mouseEntered(MouseEvent e) {            }
            @Override
            public void mouseExited(MouseEvent e) {            }
        });

        JButton confirmButton = new JButton("OK");
        confirmButton.setEnabled(true);

        confirmButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                username = usernameTextField.getText();
                frameCreatePlayer.dispose();
                setChanged();
                notifyObservers(new CreatePlayerMessage(username, "server", username));
            }
            @Override
            public void mousePressed(MouseEvent e) {           }
            @Override
            public void mouseReleased(MouseEvent e) {            }
            @Override
            public void mouseEntered(MouseEvent e) {            }
            @Override
            public void mouseExited(MouseEvent e) {            }
        });

        containerCreatePlayer.add(instructionLabel);
        containerCreatePlayer.add(usernameTextField);
        containerCreatePlayer.add(confirmButton);

        frameCreatePlayer.setVisible(true);*/
        System.out.print("New username: ");
        username = scanner.nextLine();
        setChanged();
        notifyObservers(new CreatePlayerMessage(localAddress, "server", username));
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
    public void update(Observable o, Object message){
        System.out.println("check3");
        try{
            Method updateView = this.getClass().getDeclaredMethod("updateView", message.getClass());
            updateView.invoke(this, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void updateClient(SuccessCreatePlayerMessage message){
        if(message.getRecipient().equals(username)){
            System.out.println("Giocatore creato " + message.getRecipient());
        }
    }

    private void updateView(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            System.out.println("Giocatore creato " + successCreatePlayerMessage.getRecipient());
        }
    }

    private void updateView(ErrorMessage errorMessage){
        System.out.println("check4");
        if(errorMessage.getRecipient().equals(username)){
            if(errorMessage.toString().equals("NotValidUsername")){
                this.createPlayer();
            }
            if(errorMessage.toString().equals("PlayerNumberExceeded")){
                //TODO waiting lobby
                System.out.print("Impossibile connettersi");
            }
        }
    }

    public void playerNumberExceededDialog() {
        JFrame framePlayerNumberExceeded = new JFrame();
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(framePlayerNumberExceeded, "Connection Failed", "Player number limit already reached, unable to add another player", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
    }

    public static void showSuccessMessage(JFrame parent){
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(parent, "Registration successfully completed!", "Success!", JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }
}




