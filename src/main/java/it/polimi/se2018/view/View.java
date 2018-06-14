package it.polimi.se2018.view;
import it.polimi.se2018.model.events.ToolCardActivationMessage;
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
    private int changedDiePosition;
    private int draftPoolPosition = -1;

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
     * shows up a login window where the user can choose her/his name
     */
    public void createPlayer(){
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
    public void update(DiePlacementMessage diePlacementMessage) {

    }

    @Override
    public void update(ErrorMessage errorMessage) {
        System.out.println("sono entrato nell'update" + errorMessage.getRecipient());
        if(errorMessage.getRecipient().equals(username)){
            if(errorMessage.toString().equalsIgnoreCase("NotValidUsername")){
                System.out.println("Username not available!");
                this.createPlayer();
            }
            if(errorMessage.toString().equalsIgnoreCase("PlayerNumberExceeded")){
                //TODO waiting lobby
                System.out.print("Impossibile connettersi");
            }
            if(errorMessage.toString().equalsIgnoreCase("NotEnoughPlayer")){
                System.out.println("Minimum players number not reached.");
            }
            if(errorMessage.toString().equalsIgnoreCase("UsernameNotFound")){

            }
            if(errorMessage.toString().equals("La posizione &eacute; gi&aacute; occupata")){
                System.out.println("Posizione già occupata");

                inputManager =InputManager.INPUT_CHOOSE_MOVE;
                new Thread(this).start();

            }
            if(errorMessage.toString().equals("La posizione del dado non &eacute; valida")){
                System.out.println("Posizione non valida");
                inputManager =InputManager.INPUT_CHOOSE_MOVE;
                new Thread(this).start();

            }
            if(errorMessage.toString().equals("You have already used all your moves in this round")){
                System.out.println("You have already used all your moves in this round");
                inputManager = InputManager.INPUT_CHOOSE_MOVE;
                new Thread(this).start();
            }
        }
    }

    @Override
    public void update(GameInitializationMessage gameInitializationMessage) {
        String playingPLayer=null;
        boolean alreadyRead = false;
        System.out.println("Private objective card: " + privateObjectiveCardsDescription);
        //System.out.println(gameInitializationMessage.getGameboardInformation());
        String[] words = gameInitializationMessage.getGameboardInformation().split("/");
        for(int i =0; i<words.length;i++){
            int cardNumber=1;
            if (words[i].equalsIgnoreCase("PublicObjectiveCards:")) {
                System.out.println("ciao1");
                i++;
                while (!alreadyRead) {
                    if (words[i].equalsIgnoreCase("Name:")) {
                        System.out.println("Public Objective Card #" + cardNumber + ": " + words[i + 1]);
                        cardNumber++;
                        i+=2;
                    }
                    if (words[i].equalsIgnoreCase("Description:")) {
                        System.out.println("Description: " + words[i + 1] + "\n");
                        i+=2;
                    }
                    if (words[i].equalsIgnoreCase("ToolCards:")) {
                        alreadyRead = true;
                    }
                }
            }
            if(words[i].equalsIgnoreCase("ToolCards:")) {
                cardNumber = 1;
                alreadyRead=false;
                i++;
                while (!alreadyRead) {
                    if (words[i].equalsIgnoreCase("Name:")) {
                        System.out.println("ToolCards #" + cardNumber + ": " + words[i + 1]);
                        cardNumber++;
                        i+=2;
                    }
                    if (words[i].equalsIgnoreCase("Description:")) {
                        System.out.println("Description: : " + words[i + 1] + "\n");
                        i+=2;
                    }
                    if(words[i].equalsIgnoreCase("DiceList:")){
                        alreadyRead = true;
                    }
                }
            }
            alreadyRead=false;
            if(words[i].equalsIgnoreCase("DiceList:")){
                System.out.println("Draft pool: ");
                while (!alreadyRead) {
                    System.out.print(words[i + 1] + " ");
                    i++;
                    if(words[i+1].equalsIgnoreCase("SchemaCard:")){
                        alreadyRead = true;
                    }
                }
            }
            if(words[i].equalsIgnoreCase("SchemaCard:")){
                System.out.println("\n");
                while(!words[i].equalsIgnoreCase("schemaStop:")){

                    System.out.println(words[i]);
                    i++;
                }
            }
            if(words[i].equalsIgnoreCase("playingPlayer:")){
                playingPLayer = words[i+1];
            }
        }
        if(playingPLayer.equals(username)){
            inputManager = InputManager.INPUT_CHOOSE_MOVE;
            new Thread(this).start();
        }else{
            System.out.println("It's not your turn!");
        }
    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {

    }

    @Override
    public void update(NoActionMove noActionMove) {

    }

    @Override
    public void update(RequestMessage requestMessage) {
        if(requestMessage.getValues().split(" ")[0].equalsIgnoreCase("DraftPoolDiePosition:")){
            draftPoolPosition = Integer.parseInt(requestMessage.getValues().split(" ")[1]);
        }
        if(requestMessage.getValues().split(" ")[0].equalsIgnoreCase("ToolCardName:")){
            toolCardUsageName = requestMessage.getValues().split(" ")[1];
        }
        inputManager = requestMessage.getInputManager();

        new Thread(this).start();
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {

    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        playersNumber = showPrivateObjectiveCardsMessage.getPlayerNumber();
        if(username.equals(showPrivateObjectiveCardsMessage.getRecipient())){
            showPrivateObjectiveCard(showPrivateObjectiveCardsMessage.getPrivateObjectiveCardColor());
        }
    }

    @Override
    public void update(SuccessMessage successMessage) {
        String whichSuccess = successMessage.getSuccessMessage().split("\n")[0];
        if(whichSuccess.equals("ChangeDieValueToolCard")){
            changedDiePosition = Integer.parseInt(successMessage.getSuccessMessage().split("\n")[1]);
        }
        System.out.println(successMessage.getSuccessMessage());
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
    public void update(ToolCardActivationMessage toolCardActivationMessage) {

    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        inputManager = toolCardErrorMessage.getInputManager();
        new Thread(this).start();
    }

    @Override
    public void run() {
        //TODO add stop tool card activation option
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
                            System.out.println("choose a valid number");
                        } else {
                            wrongInput = false;
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Wrong input!");
                        wrongInput = true;
                    }
                }
                System.out.println("SelectedSchemaMessage");
                setChanged();
                notifyObservers(new SelectedSchemaMessage(username,"server", schemaName[choice -1]));
                break;
            }
            case INPUT_CHOOSE_MOVE:{
                System.out.println("It's your Turn! Type the number of your choice:" +
                        "\n1 Place a die" +
                        "\n2 Use a ToolCard" +
                        "\n3 Do nothing");
                boolean wrongInput = true;
                while(wrongInput){
                    try{
                        input = scanner.nextLine();
                        choice = Integer.parseInt(input);
                        System.out.println("choice = " + choice);
                        if(choice<1 || choice>3){
                            wrongInput = true;
                            System.out.println("choose a valid number");
                        } else {
                            wrongInput = false;
                            if(choice == 1){
                                int diceOnRoundDice =-1;
                                System.out.println("Take a die from the draftPool:");
                                wrongInput = true;
                                while(wrongInput) {
                                    System.out.print("Die position: ");
                                    try {
                                        input = scanner.nextLine();
                                        diceOnRoundDice = Integer.parseInt(input);
                                        System.out.println("NUMERO_GIOCATORI " + playersNumber);
                                        if (diceOnRoundDice < 1 || diceOnRoundDice > playersNumber *2 +1) {
                                            System.out.println("Wrong Input");
                                            wrongInput = true;
                                        } else {
                                            diceOnRoundDice = diceOnRoundDice-1;
                                            wrongInput = false;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Wrong Input");
                                        wrongInput = true;
                                    }
                                }
                                setChanged();
                                notifyObservers(new ChooseDiceMove(username,"server",diceOnRoundDice));
                            }
                            if(choice == 2){
                                System.out.println("Tool card number: ");
                                try{
                                    input = scanner.nextLine();
                                    choice = Integer.parseInt(input);
                                    if(choice<1 || choice >3){
                                        System.out.println("Wrong input!");
                                        wrongInput = true;
                                    } else {
                                        wrongInput = false;
                                        if(toolCardDescription[choice].split(" ")[0].equals("Name:")){
                                            setChanged();
                                            notifyObservers(new UseToolCardMove(username, "server", toolCardDescription[choice].split(" ")[1]));
                                        }
                                    }
                                } catch (NumberFormatException e){
                                    System.out.println("Wrong input!");
                                    wrongInput = true;
                                }
                            }
                            if (choice ==3){
                                System.out.println("Ho deciso di passare il turno");
                                setChanged();
                                notifyObservers(new NoActionMove(username,"server"));

                            }
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Wrong input!");
                        wrongInput = true;
                    }
                }

                //setChanged();
                //notifyObservers();
                break;
            }
            case INPUT_MODIFY_DIE_VALUE:{
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
                wrongInput = true;
                StringBuilder builder = new StringBuilder();
                builder.append("DiePosition: " + choice + " ");
                if(toolCardUsageName.equalsIgnoreCase("Pinza Sgrossatrice")){
                    System.out.println("Do you want to increase the die value? (Y/N)");
                    while (wrongInput){
                        input = scanner.nextLine();
                        if(input.equalsIgnoreCase("y")||input.equalsIgnoreCase("n")){
                           wrongInput = false;
                           builder.append("IncreaseValue: " + input);
                        } else {
                            wrongInput = true;
                        }
                    }
                }
                setChanged();
                notifyObservers(new ToolCardActivationMessage(username, "server", toolCardUsageName, builder.toString()));
                break;
            }
            case INPUT_MOVE_DIE_ON_WINDOW:{
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
            case INPUT_PLACE_DIE: {
                StringBuilder builder = new StringBuilder();
                boolean wrongInput = true;
                int row = -1;
                System.out.println("Choose die position:");
                while (wrongInput) {
                    System.out.print("row:");
                    try {
                        input = scanner.nextLine();
                        row = Integer.parseInt(input);
                        if (row < 1 || row > SCHEMA_CARD_ROWS_NUMBER) {
                            wrongInput = true;
                            System.out.println("Wrong Input!");
                        } else {
                            row = row-1;
                            wrongInput = false;
                            builder.append("row: " + row + " ");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong Input!");
                        wrongInput = true;
                    }
                }
                wrongInput = true;
                int col = -1;
                while (wrongInput) {
                    System.out.println("col: ");
                    try {
                        input = scanner.nextLine();
                        col = Integer.parseInt(input);
                        if (col < 1 || col > SCHEMA_CARD_COLUMNS_NUMBER) {
                            System.out.println("Wrong Input!");
                            wrongInput = true;
                        } else {
                            col = col-1;
                            wrongInput = false;
                            builder.append("col: " + col + " ");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong Input!");
                        wrongInput = true;
                    }
                }
                builder.append("DraftPoolDiePosition: ");
                builder.append(draftPoolPosition);
                System.out.println("Riga: "+ row + " " + "col: " +col );
                System.out.println(builder.toString());
                setChanged();
                notifyObservers(new DiePlacementMessage(username,"server",builder.toString()));
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