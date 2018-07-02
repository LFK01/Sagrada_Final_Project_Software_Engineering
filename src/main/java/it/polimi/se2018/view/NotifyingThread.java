package it.polimi.se2018.view;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotifyingThread extends Thread{

    private static final String QUIT_QUOTE = "Type \"quit\" to interrupt the move\n";
    private static final String ERROR_QUOTE = "Wrong input!";
    private static final String STANDARD_CHOICE_QUOTE = "Choice: ";
    private static final String CHANGE_VALUE_QUOTE = "Choose a die from the draftpool to change its values:";
    private static volatile String inputMemoryString = "null\n";
    private static volatile boolean playerIsActive = false;
    private static volatile boolean playerDisabledThreadHasEnded = true;
    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<>();
    private final InputManager inputManager;
    private Scanner scanner;
    private final String username;
    private final String toolCardUsageID;
    private final int draftPoolDiceNumber;
    private String[] schemaNames;
    private String[] toolCardIDs;

    public NotifyingThread(InputManager inputManager, String username) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = null;
        this.toolCardIDs = null;
        this.toolCardUsageID = "";
        this.draftPoolDiceNumber = -1;
    }

    public NotifyingThread(InputManager inputManager, String username, String[] names) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = names;
        this.toolCardIDs = names;
        this.toolCardUsageID = "";
        this.draftPoolDiceNumber = -1;
    }

    public NotifyingThread(InputManager inputManager, String username, String toolCardUsageID) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = null;
        this.toolCardIDs = null;
        this.toolCardUsageID = toolCardUsageID;
        this.draftPoolDiceNumber = -1;
    }

    public NotifyingThread(InputManager inputManager, String username, String toolCardUsageID,
                           int draftPoolDiceNumber) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = null;
        this.toolCardIDs = null;
        this.toolCardUsageID = toolCardUsageID;
        this.draftPoolDiceNumber = draftPoolDiceNumber;
    }

    public void addListener(final ThreadCompleteListener listener){
        listeners.add(listener);
    }

    private final void notifyListeners(Message message){
        for(ThreadCompleteListener listener: listeners){
            listener.notifyOfThreadComplete(this, message);
        }
    }

    @Override
    public final void run(){
        System.out.println("starting w/" + inputManager.toString());
        Message inputMessage = null;
        try{
            switch (inputManager){
                case INPUT_NEW_CONNECTION:{
                    /*lets the player decide whether or not to reconnect*/
                    inputMessage = readNewConnectionChoice();
                    break;
                }
                case INPUT_PLAYER_NAME:{
                    /*user chooses his name*/
                    inputMessage = readPlayerName();
                    break;
                }
                case INPUT_SCHEMA_CARD:{
                    /*player chooses the schema card*/
                    inputMessage = readSchemaCard();
                    break;
                }
                case INPUT_CHOOSE_MOVE:{
                    /*player chooses which move to do
                    * place a die, use a tool card or forfeit*/
                    inputMessage = readMove();
                    break;
                }
                case INPUT_CHOOSE_DIE:{
                    /*player picks a die from the draftPool
                    * this case generates a ToolCardActivationMessage*/
                    inputMessage = readChosenDie();
                    break;
                }
                case INPUT_CHOOSE_DIE_PLACE_DIE:{
                    /*player drafts a die from the pool and
                    * places it on his schema card.
                    * this case generates a ToolCardActivationMessage*/
                    inputMessage = readChosenDieToPlace();
                    break;
                }
                case INPUT_PLACE_DIE:{
                    /*player drafts a die from the pool and
                     * places it on his schema card.
                     * this case generates a DiePlacementMessage*/
                    inputMessage = readPositions();
                    break;
                }
                case INPUT_TOOL_PLACE_DIE:{
                    /*player choose where to place a die
                    * which may have been modified.
                    * this case generates a ToolCardActivationMessage*/
                    inputMessage = readToolPositions();
                    break;
                }
                case INPUT_MODIFY_DIE_VALUE:{
                    /*player picks a die to modifiy his value
                    * this case generates a ToolCardActivationMessage*/
                    inputMessage = readDieToModify();
                    break;
                }
                case INPUT_INCREASE_DIE_VALUE:{
                    /*player picks a die from draftPool and chooses whether or not to
                    * increase its value
                    * this case generates a ToolCardActivationMessage*/
                    inputMessage = readDieToModifyAndIncreaseChoice();
                    break;
                }
                case INPUT_MOVE_DIE_ON_WINDOW:{
                    /*player picks a die from his window and decides
                    * where to place it
                    * this case generates a ToolCardActivationMessage*/
                    inputMessage = readMultiplePositions();
                    break;
                }
                case INPUT_CHOOSE_VALUE:{
                    /*player chooses a value for a die
                    * this case generates a ToolCardActivationMessage*/
                    inputMessage = readValue();
                    break;
                }
                case INPUT_POSITION_DRAFTPOOL_POSITION_ROUNDTRACK:{
                    /*player chooses a die on the draftPool and a die on the RoundTrack
                    * this case generates a ToolCardActivationMessage*/
                    inputMessage = readDraftPoolRoundTrackPositions();
                    break;
                }
                case INPUT_VOID_TOOL_CARD:{
                    /*player has nothing to do, a message is
                    * sent automatically with empty values*/
                    inputMessage = new ToolCardActivationMessage(username, "server",
                            toolCardUsageID, "");
                    break;
                }
                case INPUT_PLAYER_DISABLED:{
                    /*player cannot choose any move, this method is implemented to synchronize
                    * between disabled state and active state and to handle the
                    * scanner input*/
                    inputMessage = readPlayerDisabledInput();
                    break;
                }
            }
        } finally {
            if(!inputMessage.getRecipient().equals("doNotSend")) {
                notifyListeners(inputMessage);
            }
        }
    }

    /*done thread safe, not required quit*/
    private Message readNewConnectionChoice() {
        int choice;
        System.out.print("You've been banned from the match due to inactivity, choose what to do:\n" +
                "1 Reconnect to the game\n" +
                "2 Quit\n" +
                STANDARD_CHOICE_QUOTE);
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesIncluded(1,2, STANDARD_CHOICE_QUOTE);
        } else {
            if (choice < 1 || choice > 2) {
                choice = readChoiceBetweenValuesIncluded(1, 2, STANDARD_CHOICE_QUOTE);
            }
        }
        if(choice==1){
            return new ComebackMessage(username, "server", username);
        } else {
            for(ThreadCompleteListener listener: listeners){
                listener.quit();
            }
            return new ErrorMessage("quit", "quit", "quit");
        }
    }

    /*already thread safe, not required quit*/
    private Message readPlayerName() {
        boolean wrongInput = true;
        String username = "";
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
        return new CreatePlayerMessage(username, "server", username);
    }

    /*done thread safe, not required quit*/
    private Message readSchemaCard(){
        int choice;
        System.out.println("Choose schema card number: ");
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARDS_EXTRACT_NUMBER*2,
                    "Schema card number: ");
        } else {
            if(choice<1 || choice>Model.SCHEMA_CARDS_EXTRACT_NUMBER*2){
                choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARDS_EXTRACT_NUMBER*2,
                        "Schema card number: ");
            }
        }
        System.out.println("schemaName: " + schemaNames[choice-1]);
        return new SelectedSchemaMessage(username,"server", schemaNames[choice -1]);
    }

    /*done thread safe, not required quit*/
    private Message readMove() {
        Message message = null;
        int choice;
        int toolCardNumber;
        System.out.print("It's your Turn! Type the number of your choice:\n" +
                "1 Place a die\n" +
                "2 Use a ToolCard\n" +
                "3 Do nothing\n" +
                STANDARD_CHOICE_QUOTE);
        waitThreadEnd();
        System.out.println("player has become active");
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesIncluded(1, 3, STANDARD_CHOICE_QUOTE);
        } else {
            if(choice<1 || choice>3){
                System.out.println(ERROR_QUOTE);
                choice = readChoiceBetweenValuesIncluded(1, 3, STANDARD_CHOICE_QUOTE);
            }
        }
        switch (choice){
            case 1:{
                int diceOnRoundDice;
                System.out.println("Take a die from the draftPool:");
                diceOnRoundDice = readChoiceBetweenValuesIncluded(1,
                        Model.MAXIMUM_PLAYER_NUMBER*2 +1, "Die position: ");
                message = new ChooseDiceMove(username,"server", diceOnRoundDice-1);
                break;
            }
            case 2:{
                toolCardNumber = readChoiceBetweenValuesIncluded(1, Model.TOOL_CARDS_EXTRACT_NUMBER,
                        "Tool card number: ");
                message = new UseToolCardMove(username, "server",
                        toolCardIDs[toolCardNumber-1]);
                break;
            }
            case 3:{
                System.out.println("You have chose to forfeit this turn");
                message = new NoActionMove(username,"server");
                break;
            }
        }
        return message;
    }

    /*done thread safe, not required quit*/
    private Message readChosenDie(){
        int choice;
        System.out.println("Choose a die from the draft pool:");
        StringBuilder builder = new StringBuilder();
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesIncluded(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                    "Position: ");
        } else {
            if (choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                choice = readChoiceBetweenValuesIncluded(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                        "Position: ");
            }
        }
        builder.append("draftPoolDiePosition: ").append(choice-1).append(" ");
        return new ToolCardActivationMessage(username, "server", toolCardUsageID,
                builder.toString());
    }

    /*done thread safe, quit safe*/
    private Message readChosenDieToPlace(){
        int choice;
        System.out.println("Choose a die from the draft pool:");
        waitThreadEnd();
        choice = readFromMemory();
        StringBuilder builder = new StringBuilder();
        if(choice == -1){
            choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                    "Position: ");
        } else {
            if (choice <1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                        "Position: ");
            }
        }
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("draftPoolDiePosition: ").append(choice-1).append(" ");

        }
        System.out.println("Choose where to place the selected die:");
        choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                "Row: ");
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("row: ").append(choice-1).append(" ");
        }
        choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("col: ").append(choice-1).append(" ");
        }
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    /*done, not required quit*/
    private Message readPositions(){
        StringBuilder builder = new StringBuilder();
        int choice;
        System.out.println("Choose die position:");
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                    "Row: ");
        } else{
            if (choice<1 || choice >Model.SCHEMA_CARD_ROWS_NUMBER){
                choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                        "Row: ");
            }
        }
        builder.append("row: ").append(choice-1).append(" ");
        choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        builder.append("col: ").append(choice-1).append(" ");
        builder.append("DraftPoolDiePosition: ")
                .append(draftPoolDiceNumber)
                .append(" ");
        System.out.println(builder.toString());
        return new DiePlacementMessage(username,"server", builder.toString());
    }

    /*done, quit safe*/
    private Message readToolPositions(){
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose die position:");
        int choice;
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                    "Row: ");
        } else {
            if (choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER) {
                choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                        "Row: ");
            }
        }
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("row: ").append(choice-1).append(" ");
        }
        choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("col: ").append(choice-1).append(" ");
        }
        builder.append("DraftPoolDiePosition: ");
        builder.append(draftPoolDiceNumber);
        System.out.println(builder.toString());
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    /*done, quit safe*/
    private Message readDieToModify() {
        int choice;
        System.out.println(CHANGE_VALUE_QUOTE);
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                    "Die position number: ");

        } else {
            if (choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                        "Die position number: ");
            }
        }
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("draftPoolDiePosition: ").append(choice-1).append(" ");
            return new ToolCardActivationMessage(username, "server",
                    toolCardUsageID, builder.toString());
        }
    }

    /*done, quit safe*/
    private Message readDieToModifyAndIncreaseChoice(){
        int choice;
        StringBuilder builder = new StringBuilder();
        System.out.println(CHANGE_VALUE_QUOTE);
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                    "Die position number: ");
        } else {
            if(choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                        "Die position number: ");
            }
        }
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        }
        builder.append("draftPoolDiePosition: ").append(choice-1).append(" ");
        System.out.println("Do you want to increase the die value? ");
        String stringChoice = readYesOrNoWithQuit("( Y / N ) : ");
        if(stringChoice.equals("Error")){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        }
        builder.append("IncreaseValue: ")
                .append(stringChoice)
                .append(" ");
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    /*done, quit safe*/
    private Message readMultiplePositions(){
        StringBuilder builder = new StringBuilder();
        int choice;
        System.out.println("Choose a die to move from your window:");
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                    "Row: ");
        } else {
            if (choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                        "Row: ");
            }
        }
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("OldDieRow: ").append(choice-1).append(" ");
        }
        choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("OldDieCol: ").append(choice-1).append(" ");
        }
        System.out.println("Choose a new position where to place the selected die:");
        choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                "Row: ");
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("newDieRow: ").append(choice-1).append(" ");
        }
        choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("newDieCol: ").append(choice-1).append(" ");
        }
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    /*done, quit safe*/
    private Message readValue(){
        int choice;
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose a new value for the drafted die:");
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_DIE_NUMBER,
                    "Value: ");
        } else {
            if (choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_DIE_NUMBER,
                        "Value: ");
            }
        }
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("NewValue: ").append(choice).append(" ");
        }
        builder.append("draftPoolDiePosition: ").append(draftPoolDiceNumber).append(" ");
        return new ToolCardActivationMessage(username, "server", toolCardUsageID, builder.toString());
    }

    /*done, quit safe*/
    private Message readDraftPoolRoundTrackPositions(){
        int choice;
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose a die from the draft pool: ");
        waitThreadEnd();
        choice = readFromMemory();
        if(choice == -1){
            choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                    "Position: ");
        } else {
            if (choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                        "Position: ");
            }
        }
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("DraftPoolPosition: ")
                    .append(choice-1)
                    .append(" ");
        }
        System.out.println("Choose a round of the round track: ");
        choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_ROUND_NUMBER, "Round: ");
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("RoundNumber: ")
                    .append(choice-1)
                    .append("");
        }
        System.out.println("Choose a die from the Round Track:");
        choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                "Position: ");
        if(choice == -1){
            return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                    "InputQuit", null);
        } else {
            builder.append("RoundNumber: ")
                    .append(choice-1)
                    .append(" ");
        }
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    private Message readPlayerDisabledInput(){
        NotifyingThread.setPlayerDisabledThreadHasEnded(false);
        System.out.println("player disabled thread started");
        while (!playerIsActive){
            StringBuilder builder = new StringBuilder();
            builder.append(inputMemoryString);
            String playerDisableInput = scanner.nextLine();
            builder.append(playerDisableInput)
                    .append("\n");
            NotifyingThread.setInputMemoryString(builder.toString());
        }
        NotifyingThread.setPlayerDisabledThreadHasEnded(true);
        System.out.println("input_player_disabled while loop ended");
        return new ErrorMessage(username, "doNotSend", "doNotSend");
    }

    private int readChoiceBetweenValuesIncluded(int firstValue, int secondValue, String inputInstructions){
        boolean wrongInput = true;
        String input;
        int choice;
        while (wrongInput) {
            System.out.print(inputInstructions);
            try {
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if(choice<firstValue || choice>secondValue){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    return choice;
                }
            } catch (NumberFormatException e) {
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        return -1;
    }

    /**
     *
     * @param firstValue
     * @param secondValue
     * @param inputInstructions
     * @return -1 if the player decides to quit
     */
    private int readChoiceBetweenValuesWithQuit(int firstValue, int secondValue, String inputInstructions){
        String input;
        int choice;
        boolean wrongInput = true;
        while (wrongInput) {
            try {
                System.out.println(QUIT_QUOTE + inputInstructions);
                input = scanner.nextLine();
                input.replace("\"", "")
                        .trim();
                if (input.equalsIgnoreCase("quit")) {
                    return -1;
                }
                choice = Integer.parseInt(input);
                if (choice < firstValue || choice > secondValue) {
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    return choice;
                }
            } catch (NumberFormatException e) {
                wrongInput = true;
                System.out.println(ERROR_QUOTE);
            }
        }
        return -2;
    }

    /**
     *
     * @param inputInstructions
     * @return "Error" if the player decides to quit
     */
    private String readYesOrNoWithQuit(String inputInstructions){
        boolean wrongInput = true;
        String input;
        while (wrongInput){
            System.out.print(QUIT_QUOTE + inputInstructions);
            input = scanner.nextLine();
            input.replace("\"", "");
            input.trim();
            if(input.equalsIgnoreCase("quit")){
                return "Error";
            }
            if(input.equalsIgnoreCase("y")||input.equalsIgnoreCase("n")){
                return input.toLowerCase();
            } else {
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        return "n";
    }

    /**
     *
     * @return -1 if doesn't find anything to read from the other thread,
     *      else returns every values it reads
     */
    private  int readFromMemory(){
        int choice;
        String[] inputLines = inputMemoryString.split("\n");
        if(inputLines[inputLines.length-1].equals("null")){
            System.out.println("player has typed nothing");
            /*player has typed nothing before his turn*/
            setInputMemoryString("null\n");
            return -1;
        } else {
            /*player has typed something before his turn*/
            System.out.println("player has typed something: " + inputMemoryString);
            System.out.println("reading: " + inputLines[inputLines.length - 1]);
            try {
                choice = Integer.parseInt(inputLines[inputLines.length - 1]);
                setInputMemoryString("null\n");
                return choice;
            } catch (NumberFormatException e) {
                /*other thread didn't have any readable value*/
                System.out.println(ERROR_QUOTE);
                setInputMemoryString("null\n");
                return -1;
            }
        }

    }

    public static void setInputMemoryString(String newMemory){
        inputMemoryString = newMemory;
    }

    private static void setPlayerDisabledThreadHasEnded(boolean hasEnded){
        playerDisabledThreadHasEnded = hasEnded;
    }

    public static void setPlayerIsActive(boolean isActive){
        playerIsActive = isActive;
    }

    private void waitThreadEnd(){
        while (!playerDisabledThreadHasEnded){
            try {
                /*waiting for the player inactive thread to end*/
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
    }
}