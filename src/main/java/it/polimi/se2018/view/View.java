package it.polimi.se2018.view;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author giovanni
 */
public class View extends ProjectObservable implements ProjectObserver, Runnable{

    protected String username;
    private boolean isPlayerTurn;

    boolean windowCreated = true;

    /**
     * input variables
     */
    private Scanner scanner;
    //private boolean windowCreated = true;
    private int choice = 0;
    private String input;

    private InputManager inputManager;

    private String[] schemaName = new String[4];

    private Thread inputThread;

    private String[] publicObjectiveCardsDescription = new String[3];
    private String[] toolCardDescription = new String[3];
    private String privateObjectiveCardsDescription;
    private boolean matchIsOn;


    /**
     * Initializes view
     */
    public View(){
        inputManager = InputManager.INPUT_DISABLED;
        matchIsOn = true;
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
        inputManager = InputManager.INPUT_PLAYER_NAME;
        new Thread(this).start();
    }

    public int demandConnectionType() {
        JFrame frameDemandConnection = new JFrame("Parent Window");
        Object[] options = {"RMI", "Socket"};
        int n = JOptionPane.showOptionDialog(frameDemandConnection, "Choose connection:", "Connection", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        if(n == JOptionPane.YES_OPTION){
            frameDemandConnection.dispose();
            return 1;
        }else{
            frameDemandConnection.dispose();
            return 2;
        }
    }


    public void update(Observable o, Object message){
        try{
            Method updateView = this.getClass().getDeclaredMethod("updateView", message.getClass());
            updateView.invoke(this, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void updateView(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            System.out.println("Successful  login, new username: " + successCreatePlayerMessage.getRecipient());
        }
    }

    private void updateView(ErrorMessage errorMessage){
        if(errorMessage.getRecipient().equals(username)){
            if(errorMessage.toString().equals("NotValidUsername")){
                System.out.println("Username not available!");
                this.createPlayer();
            }
            if(errorMessage.toString().equals("PlayerNumberExceeded")){
                //TODO waiting lobby
                System.out.print("Impossibile connettersi");
            }
            if(errorMessage.toString().equals("NotEnoughPlayer")){
                System.out.println("Minimum players number not reached.");
            }
            if(errorMessage.toString().equals("UsernameNotFound")){

            }
        }
    }

    private void updateView(ChooseSchemaMessage message){

        for(int i=0;i<4;i++) {
            System.out.println("type" + " " + (i+1) + " "+ "to choose this schema");
            System.out.println(message.getSchemaCards(i));
            schemaName[i]= new String(message.getSchemaCards(i).split("\n")[0]);
        }

    }

    private void updateView(ShowPrivateObjectiveCardsMessage message){
        showPrivateObjectiveCard(message.getPrivateObjectiveCardColor());
    }

    private void updateView(GameInitializationMessage message) {
        /*publicObjectiveCardsDescription = message.getPublicObjectiveCardsDescription();
        toolCardDescription = message.getToolCardsDescription();
        for (int i = 0; i < publicObjectiveCardsDescription.length; i++) {
            System.out.print("Public Objective card #" + (i + 1) + " :\n" + publicObjectiveCardsDescription[i]);
        }
        for (int i = 0; i < publicObjectiveCardsDescription.length; i++) {
            System.out.print("Public Objective card #" + (i + 1) + " :\n" + publicObjectiveCardsDescription[i]);
        }
        System.out.println("\n");
        System.out.println(message.getRoundTrack().toString());
        System.out.println("\n");

        /*for (int i = 0; i < message.getSchemaCardInGame().length ; i++) {  //sarà nuemro di giocatori invece di 4
            System.out.println("Schema:");
            System.out.println(message.getSchemaCardInGame()[i]);
            //showGameboard();
        }*/
    }

    private void updateView(SendSchemaAndTurn message){
        for(int i =0;i < message.getschemaInGame().length-1; i++) {  //sarà nuemro di giocatori invece di 4
            if(message.getRecipient().equals(username)){
                System.out.println("Questo è il tuo SchemaCard");
            }

        }
        if(message.getRecipient().equals(username)){
            System.out.println("type 1 to set a die");
            System.out.println("type 2 to use a toolCard");
            System.out.println("Type 3 to pass the turn");

            if(choice ==1){
                setChanged();
                //notifyObservers(new ); lancio del dado
            }
            if(choice ==2){
                setChanged();
                notifyObservers();
            }
            if(choice ==3){
                setChanged();
                notifyObservers();
            }


        }
        else {          //un loop per richiedere input
            System.out.println("non è il tuo turno");
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

    public int chooseConnectionWindow() {

            if (windowCreated) {

                JFrame frame = new JFrame("Quale connessione vuoi scegliere ?");
                Container container = new Container();
                ImageIcon iconRMI = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\CatturaRMI.PNG");
                ImageIcon iconSocket = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\CatturaSocket.PNG");
                ImageIcon iconComeBack = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\comeBack2.jpg");
                JButton buttonRMI = new JButton(iconRMI);
                JButton buttonComeBack = new JButton(iconComeBack);
                JButton buttonSocket = new JButton(iconSocket);
                buttonRMI.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        setChoice();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        setChoice();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        setChoice();

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setChoice();

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setChoice();

                    }
                });

                container.setLayout(new GridLayout(1, 3));
                container.add(buttonRMI);
                container.add(buttonComeBack);
                container.add(buttonSocket);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
                frame.add(container);
                frame.setSize(550, 750);
                frame.setResizable(false);
                frame.setVisible(true);


            }
        return choice;
    }


    public void setChoice(){
        choice = 1;
    }




    public void chooseSchemaWindow(int schema1,int schema2) {
        /*JFrame frame = new JFrame("Scegli una delle carte schema");
        Container container = new Container();
        container.setLayout(new GridLayout(2,2));
        JButton buttonSchema1 = null;
        JButton buttonSchema2 = null;
        JButton buttonSchema3 = null;
        JButton buttonSchema4 = null;
        ImageIcon schemaIcon1;
        ImageIcon schemaIcon2;
        ImageIcon schemaIcon3;
        ImageIcon schemaIcon4;

        int selected1 = 0;
        int selected2 = 0;
        int selected3 = 0;
        int selected4 = 0;

         switch(schema1) {

            case 1:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Kaleidoscopic Dream.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Firmitas.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;

                break;
            case 2:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Fractal Drops.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Ripples Of Light.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 1;
                break;
            case 3:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Lux Mundi.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Lux Astram.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1=1;
                selected2 =2;
                break;
            case 4:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Gravitas.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Water Of Life.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 5:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Sun Catcher.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Shadow Thief.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 6:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurorae Mangnificus.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurora Sagradis.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 7:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Symphony of Light.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Virtus.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 8:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Firelight.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Sun's Glory.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 9:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Battlo.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Bellesguard.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                break;
            case 10:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Fulgor del Cielo.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Luz Celestial.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 11:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Chromatic Splendor.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Comitas.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                break;
            case 12:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Via Lux.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Industria.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
        }

        switch(schema2){
            case 1:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Kaleidoscopic Dream.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Firmitas.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);


                break;
            case 2:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Fractal Drops.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Ripples Of Light.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 3:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Lux Mundi.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Lux Astram.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 4:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Gravitas.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Water Of Life.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 5:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Sun Catcher.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Shadow Thief.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 6:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurorae Mangnificus.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurora Sagradis.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 7:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Symphony of Light.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Virtus.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 8:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Firelight.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Sun's Glory.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 9:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Battlo.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Bellesguard.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 10:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Fulgor del Cielo.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Luz Celestial.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 11:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Chromatic Splendor.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Comitas.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 12:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Via Lux.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Industria.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;


        }

        int finalSelected1 = selected1;
        int finalSelected2 = selected2;
        int finalSelected3 = selected3;
        int finalSelected4 = selected4;


        buttonSchema1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("ho premuto il tasto");
                boolean notSelected = false;
                setChanged();
                notifyObservers(new SelectedSchemaMessage("username","controller",finalSelected1));
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


        buttonSchema2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifyObservers(new SelectedSchemaMessage("username","controller",finalSelected2));

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

        buttonSchema3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifyObservers(new SelectedSchemaMessage("username","controller",finalSelected3));

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

        buttonSchema4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setChanged();
                notifyObservers(new SelectedSchemaMessage("username","controller",finalSelected4));

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

        container.setLayout(new GridLayout(2,2));
        container.add(buttonSchema1);
        container.add(buttonSchema2);
        container.add(buttonSchema3);
        container.add(buttonSchema4);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.add(container);
        frame.setSize(550,750);
        frame.setResizable(true);
        frame.setVisible(true);
*/
        //setChanged();
        //notifyObservers(new SelectedSchemaMessage(username,"server",1));
    }

    public void showPrivateObjectiveCard(String description){
        System.out.println("Il tuo obiettivo privato è " + description);
        privateObjectiveCardsDescription = new String(description);
    }


    public void showGameboard(){
        for(int i =0; i<3; i++){
            System.out.println("Objective number"+ " " +(i+1)+ " " + publicObjectiveCardsDescription[i]);
        }
        for(int j =0; j<3; j++){
            System.out.println("ToolCard number"+ " " +(j+1)+ " " + toolCardDescription[j]);
        }
        //notifyObservers(new StartGameMessage(username,"server"));

    }

    @Override
    public void update(Message message) {
        System.out.println(message.toString());
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        for(int i=0;i<4;i++) {
            System.out.println("type" + " " + (i+1) + " "+ "to choose this schema");
            System.out.println(chooseSchemaMessage.getSchemaCards(i));
            schemaName[i]= new String(chooseSchemaMessage.getSchemaCards(i).split("\n")[0]);
        }
        System.out.println("Schema card number: ");
        inputManager = InputManager.INPUT_SCHEMA_CARD;
        new Thread(this).start();
    }

    @Override
    public void update(ComebackSocketMessage comebackSocketMessage) {

    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {

    }

    @Override
    public void update(ErrorMessage errorMessage) {
        if(errorMessage.getRecipient().equals(username)){
            if(errorMessage.toString().equals("NotValidUsername")){
                System.out.println("Username not available!");
                this.createPlayer();
            }
            if(errorMessage.toString().equals("PlayerNumberExceeded")){
                //TODO waiting lobby
                System.out.print("Impossibile connettersi");
            }
            if(errorMessage.toString().equals("NotEnoughPlayer")){
                System.out.println("Minimum players number not reached.");
            }
            if(errorMessage.toString().equals("UsernameNotFound")){

            }
        }
    }

    @Override
    public void update(GameInitializationMessage gameInitializationMessage) {
        for(int i=0; i<gameInitializationMessage.getPublicObjectiveCardsDescription().length; i++){
            System.out.println("Public Objective Card #" + (i+1) +" :\n" + gameInitializationMessage.getPublicObjectiveCardsDescription()[i]);
        }
        for(int j=0; j<gameInitializationMessage.getToolCardsDescription().length; j++){
            System.out.println("Tool Card #" + (j+1) +" :\n" + gameInitializationMessage.getToolCardsDescription()[j]);
        }
        System.out.println("roundTrack"+ ":\n"+ gameInitializationMessage.getRoundTrack().toString());

        for(int z=0;z<gameInitializationMessage.getSchemaInGame().length;z++){
            System.out.println(gameInitializationMessage.getSchemaInGame()[z]);
        }
        if(gameInitializationMessage.getPlayingPlayer().equals(username)){
            System.out.println("It's your Turn! Type the number of your choice:" +
                                "\n1 Place a die" +
                                "\n2 Use a ToolCard" +
                                "\n3 Do nothing");
        }else{
            System.out.println("It's not your turn!");
        }
        inputManager = InputManager.INPUT_MOVE;
        new Thread(this).start();
    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {

    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {

    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        if(username.equals(showPrivateObjectiveCardsMessage.getRecipient())){
            showPrivateObjectiveCard(showPrivateObjectiveCardsMessage.getPrivateObjectiveCardColor());
        }
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            System.out.println("Successful  login, new username: " + successCreatePlayerMessage.getRecipient());
        }
    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {

    }

    @Override
    public void update(UpdateTurnMessage updateTurnMessage) {

    }

    @Override
    public void run() {
        scanner = new Scanner(new InputStreamReader(System.in));
        switch(inputManager){
            case INPUT_DISABLED:{
                input = scanner.nextLine();
                if(input!= ""){
                    System.out.println("Invalid input, wait for further instruction.");
                }
                break;
            }
            case INPUT_PLAYER_NAME:{
                boolean wrongInput = true;
                while(wrongInput){
                    System.out.print("New Username: ");
                    username = scanner.nextLine();
                    if(username.equals("") || username.equals("\n")){
                        System.out.println("Not valid username!");
                        wrongInput = true;
                    } else {
                        wrongInput = false;
                    }
                }
                setChanged();
                notifyObservers(new CreatePlayerMessage(username, "server", username));
                break;
            }
            case INPUT_SCHEMA_CARD:{
                boolean wrongInput = true;
                while(wrongInput){
                    try{
                        input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        System.out.println("choice = " + choice);
                        if(choice<1||choice>4){
                            wrongInput = true;
                        } else {
                            wrongInput = false;
                        }
                    } catch (NumberFormatException e){
                        wrongInput = true;
                    }
                }
                setChanged();
                notifyObservers(new SelectedSchemaMessage(username,"server", schemaName[choice -1]));
                break;
            }
            case INPUT_MOVE:{
                //TODO read input choose move
                //setChanged();
                //notifyObservers();
                break;
            }
        }

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
}






