package it.polimi.se2018.view;
import it.polimi.se2018.model.events.ChangeDieValueMessage;
import it.polimi.se2018.model.events.MovingDieMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;
import it.polimi.se2018.view.comand_line.InputManager;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author giovanni
 */
public class View extends ProjectObservable implements ProjectObserver, Runnable{

    private static final int TOOL_CARD_NUMBER = 3;
    private static final int TOOL_CARD_DECK_NUMBER = 12;
    private static final int PUBLIC_OBJECTIVE_CARD_NUMBER = 3;
    private static final int INT_VALUES_NUMBER_TOOL_CARD_MOVE_DICE = 4;
    private static final int SCHEMA_CARD_COLUMNS_NUMBER = 5;
    private static final int SCHEMA_CARD_ROWS_NUMBER = 4;
    private String username;
    private boolean isPlayerTurn;
    private int playersNumber;

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

    private String[] publicObjectiveCardsDescription;
    private String[] toolCardDescription;
    private String privateObjectiveCardsDescription;
    private boolean matchIsOn;
    private String toolCardUsageName;


    /**
     * Initializes view
     */
    public View(){
        inputManager = InputManager.INPUT_DISABLED;
        publicObjectiveCardsDescription = new String[PUBLIC_OBJECTIVE_CARD_NUMBER];
        toolCardDescription = new String[TOOL_CARD_NUMBER];
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
            if(errorMessage.toString().equals("La posizione del dado non &eacute; valida")){
                inputManager = InputManager.INPUT_CHOOSE_MOVE;
                new Thread(this).start();
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
        //deprecata
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
            toolCardDescription = gameInitializationMessage.getToolCardsDescription();
            System.out.println("Tool Card #" + (j+1) +" :\n" + gameInitializationMessage.getToolCardsDescription()[j]);
        }
        System.out.println("roundTrack"+ ":\n"+ gameInitializationMessage.getRoundTrack().toString());
        playersNumber = gameInitializationMessage.getSchemaInGame().length;
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
        inputManager = InputManager.INPUT_CHOOSE_MOVE;
        new Thread(this).start();
    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {

    }

    @Override
    public void update(NoActionMove noActionMove) {

    }

    @Override
    public void update(RequestMessage requestMessage) {
        toolCardUsageName = requestMessage.getToolCardName();
        inputManager = requestMessage.getInputManager();
        new Thread(this).start();
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
    public void update(UseToolCardMove useToolCardMove) {

    }

    @Override
    public void update(ChooseDiceMove chooseDiceMove) {

    }

    @Override
    public void update(ChangeDieValueMessage changeDieValueMessage) {

    }


    @Override
    public void run() {
        System.out.println("thread started");
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
                        System.out.println("Schema card number: ");
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
                System.out.println("SelectedSchemaMessage");
                setChanged();
                notifyObservers(new SelectedSchemaMessage(username,"server", schemaName[choice -1]));
                break;
            }
            case INPUT_CHOOSE_MOVE:{
                boolean wrongInput = true;
                while(wrongInput){
                    try{
                        input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        System.out.println("choice = " + choice);
                        if(choice<1 || choice>4){
                            wrongInput = true;
                        } else {
                            wrongInput = false;
                            if(choice == 2){
                                System.out.println("Tool card number: ");
                                try{
                                    input = scanner.nextLine();
                                    choice = Integer.parseInt(input);
                                    if(choice<1 || choice >3){
                                        wrongInput = true;
                                    } else {
                                        wrongInput = false;
                                        if(toolCardDescription[choice].split(" ")[0].equals("Name:")){
                                            setChanged();
                                            notifyObservers(new UseToolCardMove(username, "server", toolCardDescription[choice].split(" ")[1]));
                                        }
                                    }
                                } catch (NumberFormatException e){
                                    wrongInput = true;
                                }
                            }
                        }
                    } catch (NumberFormatException e){
                        wrongInput = true;
                    }
                }
                if(choice == 1){
                    Scanner in = new Scanner(System.in);
                    int diceOnRoundTrack, row, col;
                    System.out.println("Take a die from the roundTrack");
                    diceOnRoundTrack = in.nextInt()-1;
                    System.out.println("choose a row ");
                    row = in.nextInt()-1;
                    System.out.println("choose a column");
                    col = in.nextInt()-1;
                    System.out.println("ho i valori da inviare" + diceOnRoundTrack + row+ col);
                    setChanged();
                    notifyObservers(new ChooseDiceMove(username,"server",diceOnRoundTrack,row,col));
                }
                if (choice ==3){
                    System.out.println("Ho deciso di passare il turno");
                    setChanged();
                    notifyObservers(new NoActionMove(username,"server"));

                }
                //setChanged();
                //notifyObservers();
                break;
            }
            case INPUT_TOOL_CARD_MOVE_DICE_POSITIONS:{
                int positions[] = new int[INT_VALUES_NUMBER_TOOL_CARD_MOVE_DICE];
                int positionIndex = 0;
                boolean wrongInput = true;
                System.out.println("Choose a die to move from your window:");
                while(wrongInput){
                    System.out.print("Row: ");
                    try {
                        input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        if(choice<1 || choice>SCHEMA_CARD_ROWS_NUMBER){
                            wrongInput = true;
                        } else {
                            wrongInput = false;
                            positions[positionIndex] = choice;
                            positionIndex++;
                        }
                    } catch (NumberFormatException e){
                        wrongInput = true;
                    }
                }
                wrongInput = true;
                while (wrongInput){
                    System.out.print("Column: ");
                    try {
                        input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        if(choice<1 || choice>SCHEMA_CARD_COLUMNS_NUMBER){
                            wrongInput = true;
                        } else {
                            wrongInput = false;
                            positions[positionIndex] = choice;
                            positionIndex++;
                        }
                    } catch (NumberFormatException e){
                        wrongInput = true;
                    }
                }
                System.out.println("Choose a new position where to place the selected die:");
                while(wrongInput){
                    System.out.print("Row: ");
                    try {
                        input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        if(choice<1 || choice>SCHEMA_CARD_ROWS_NUMBER){
                            wrongInput = true;
                        } else {
                            wrongInput = false;
                        }
                    } catch (NumberFormatException e){
                        wrongInput = true;
                        positions[positionIndex] = choice;
                        positionIndex++;
                    }
                }
                wrongInput = true;
                while (wrongInput){
                    System.out.print("Column: ");
                    try {
                        input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        if(choice<1 || choice>SCHEMA_CARD_COLUMNS_NUMBER){
                            wrongInput = true;
                        } else {
                            wrongInput = false;
                            positions[positionIndex] = choice;
                            positionIndex++;
                        }
                    } catch (NumberFormatException e){
                        wrongInput = true;
                    }
                }
                setChanged();
                notifyObservers(new MovingDieMessage(username, "server", positions, toolCardUsageName));
                break;
            }
            case INPUT_TOOL_CARD_CHANGE_DICE_VALUE_POSITIONS:{
                boolean wrongInput = true;
                while (wrongInput){
                    System.out.print("Choose a die from the draft pool to change its values: " +
                            "\nDie position number: ");
                    try{
                        input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        if(choice<1 || choice>playersNumber*2+1){
                            wrongInput = true;
                            System.out.println("Wrong input!");
                        } else {
                            wrongInput = false;
                        }
                    } catch (NumberFormatException e){
                        wrongInput = true;
                        System.out.println("Wrong input!");
                    }
                }
                setChanged();
                notifyObservers(new ChangeDieValueMessage(username, "server", toolCardUsageName, choice));
            }
            case INPUT_PLACE_DIE:{

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