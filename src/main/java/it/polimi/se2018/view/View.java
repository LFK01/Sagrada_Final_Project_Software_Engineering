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
    private int draftPoolDiceNumber  = -1;

    /**
     * input variables
     */
    private Scanner scanner;
    private int choice = 0;
    private String input;

    private InputManager inputManager;

    private String[] schemaName = new String[4];

    private String[] toolCardNames;
    private String privateObjectiveCardsDescription;
    private boolean matchIsOn;
    private String toolCardUsageName;


    /**
     * Initializes view
     */
    public View(){
        inputManager = InputManager.INPUT_DISABLED;
        toolCardNames = new String[TOOL_CARD_NUMBER];
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

    public void showPrivateObjectiveCard(String description){
        System.out.println("Il tuo obiettivo privato è " + description);
        privateObjectiveCardsDescription = description;
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
            schemaName[i]= chooseSchemaMessage.getSchemaCards(i).split("\n")[0];
        }
        inputManager = InputManager.INPUT_SCHEMA_CARD;
        new Thread(this).start();
    }

    @Override
    public void update(ComebackMessage comebackMessage) {

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
            if(errorMessage.toString().equalsIgnoreCase("PlayerUnableToUseToolCard")){
                System.out.println("Professor Oak says that it's not the time to use that!");
                inputManager = InputManager.INPUT_CHOOSE_MOVE;
                new Thread(this).start();
            }
            if(errorMessage.toString().equalsIgnoreCase("UsernameNotFound")){
                System.out.println("Username not found");
            }
            if(errorMessage.toString().equalsIgnoreCase("NotEnoughFavorTokens")){
                System.out.println("You need more tokens to activate this card!");
                inputManager = InputManager.INPUT_CHOOSE_MOVE;
                new Thread(this).start();
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
        String[] words = gameInitializationMessage.getGameboardInformation().split("/");
        System.out.println(gameInitializationMessage.getGameboardInformation());
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
                        toolCardNames[cardNumber-1]= "Name: " + words[i+1].replace(" ", "/") + " ";
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
            draftPoolDiceNumber = Integer.parseInt(requestMessage.getValues().split(" ")[1]);
        }
        if(requestMessage.getValues().split(" ")[0].equalsIgnoreCase("ToolCardName:")){
            toolCardUsageName = requestMessage.getValues().split(" ")[1];
        }
        inputManager = requestMessage.getInputManager();
        System.out.println(inputManager.toString());
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
                if(input.equals("")){
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
                                    if(choice<1 || choice >TOOL_CARD_NUMBER){
                                        System.out.println("Wrong input!");
                                        wrongInput = true;
                                    } else {
                                        wrongInput = false;
                                        if(toolCardNames[choice-1].split(" ")[0].equals("Name:")){
                                            setChanged();
                                            notifyObservers(new UseToolCardMove(username, "server", toolCardNames[choice].split(" ")[1].replace("/", " ")));
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
                builder.append("DiePosition: ").append(choice).append(" ");
                if(toolCardUsageName.equalsIgnoreCase("Pinza Sgrossatrice")){
                    System.out.println("Do you want to increase the die value? (Y/N)");
                    while (wrongInput){
                        input = scanner.nextLine();
                        if(input.equalsIgnoreCase("y")||input.equalsIgnoreCase("n")){
                           wrongInput = false;
                           builder.append("IncreaseValue: ").append(input);
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
                            builder.append("row: ").append(row).append(" ");
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
                            builder.append("col: ").append(col).append(" ");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong Input!");
                        wrongInput = true;
                    }
                }
                builder.append("DraftPoolDiePosition: ");
                builder.append(draftPoolDiceNumber);
                System.out.println("Riga: "+ row + " " + "col: " +col );
                System.out.println(builder.toString());
                setChanged();
                notifyObservers(new DiePlacementMessage(username,"server",builder.toString()));
                break;
            }
            case INPUT_POSITION_DRAFTPOOL_POSITION_ROUNDTRACK:{
                boolean wrongInput = true;
                int draftPoolPosition = -1;
                int roundNumber = -1;
                int roundTrackPosition = -1;
                System.out.println("Choose a die from the draft pool: ");
                while (wrongInput){
                    System.out.print("Position: ");
                    try{
                        input = scanner.nextLine();
                        draftPoolPosition = Integer.parseInt(input);
                        if(draftPoolPosition<1 || draftPoolPosition>draftPoolDiceNumber){
                            wrongInput = true;
                            System.out.println("Wrong Input!");
                        } else {
                            wrongInput = false;
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Wrong Input!");
                        wrongInput = true;
                    }
                }
                wrongInput = true;
                System.out.println("Choose a round of the Round Track: ");
                while (wrongInput){
                    System.out.print("Round: ");
                    try {
                        input = scanner.nextLine();
                        roundNumber = Integer.parseInt(input);
                        if(draftPoolPosition<1 || draftPoolPosition>playersNumber*2+1){
                            wrongInput = true;
                            System.out.println("Wrong Input!");
                        } else {
                            wrongInput = false;
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Wrong Input!");
                        wrongInput = true;
                    }
                }
                wrongInput = true;
                System.out.println("Choose a die from the Round Track:");
                while (wrongInput){
                    System.out.print("Position: ");
                    try {
                        input = scanner.nextLine();
                        roundTrackPosition = Integer.parseInt(input);
                        if(roundTrackPosition<1 || roundTrackPosition>playersNumber*2+1){
                            wrongInput = true;
                            System.out.println("Wrong Input!");
                        } else {
                            wrongInput = false;
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Wrong Input!");
                        wrongInput = true;
                    }
                }
                StringBuilder builder = new StringBuilder();
                builder.append("DraftPoolPosition: ").append(draftPoolPosition).append(" ");
                builder.append("RoundNumber: ").append(roundNumber).append(" ");
                builder.append("RoundTrackPosition: ").append(roundTrackPosition).append(" ");
                setChanged();
                notifyObservers(new ToolCardActivationMessage(username, "server", toolCardUsageName, builder.toString()));
                break;
            }
            case INPUT_CHOOSE_DIE:{
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