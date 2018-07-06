package it.polimi.se2018.view;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class inputControllerThread extends Thread{

    private static final String QUIT_QUOTE = "Type \"quit\" to interrupt the move\n";
    private static final String ERROR_QUOTE = "Wrong input!";
    private static final String STANDARD_CHOICE_QUOTE = "Choice: ";
    private static final String CHANGE_VALUE_QUOTE = "Choose a die from the Draft Pool to change its values:";
    private static volatile String inputMemoryString = "null\n";
    private static volatile boolean playerIsActive = false;
    private static volatile boolean playerIsConnected = false;
    private static volatile boolean playerDisabledThreadHasEnded = true;
    private static volatile boolean playerEnableThreadHasEnded;
    private static volatile boolean playerHasBeenBanned = false;
    private static volatile boolean matchHasStarted = false;
    private Message inputMessage;
    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<>();
    private final InputManager inputManager;
    private Scanner scanner;
    private final String username;
    private final String toolCardUsageID;
    private final int draftPoolDiceNumber;
    private String[] schemaNames;
    private String[] toolCardIDs;

    inputControllerThread(InputManager inputManager, String username) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = null;
        this.toolCardIDs = null;
        this.toolCardUsageID = "";
        this.draftPoolDiceNumber = -1;
    }

    inputControllerThread(InputManager inputManager, String username, String[] names) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = names;
        this.toolCardIDs = names;
        this.toolCardUsageID = "";
        this.draftPoolDiceNumber = -1;
    }

    inputControllerThread(InputManager inputManager, String username, String toolCardUsageID,
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
        inputMessage = new ErrorMessage("doNotSend", "doNotSend", "doNotSend");
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
            case INPUT_OLD_PLAYER_NAME:{
                /*user inserts his old username to reconnect*/
                inputMessage = readOldPlayerName();
                break;
            }
            case INPUT_SCHEMA_CARD:{
                /*player chooses the schema card*/
                readSchemaCard();
                break;
            }
            case INPUT_CHOOSE_MOVE:{
                /*player chooses which move to do
                * place a die, use a tool card or forfeit*/
                readMove();
                break;
            }
            case INPUT_CHOOSE_DIE:{
                /*player picks a die from the draftPool
                * this case generates a ToolCardActivationMessage*/
                readChosenDie();
                break;
            }
            case INPUT_CHOOSE_DIE_PLACE_DIE:{
                /*player drafts a die from the pool and
                * places it on his schema card.
                * this case generates a ToolCardActivationMessage*/
                readChosenDieToPlace();
                break;
            }
            case INPUT_PLACE_DIE:{
                /*player drafts a die from the pool and
                 * places it on his schema card.
                 * this case generates a DiePlacementMessage*/
                readPositions();
                break;
            }
            case INPUT_TOOL_PLACE_DIE:{
                /*player choose where to place a die
                * which may have been modified.
                * this case generates a ToolCardActivationMessage*/
                readToolPositions();
                break;
            }
            case INPUT_MODIFY_DIE_VALUE:{
                /*player picks a die to modifiy his value
                * this case generates a ToolCardActivationMessage*/
                readDieToModify();
                break;
            }
            case INPUT_INCREASE_DIE_VALUE:{
                /*player picks a die from draftPool and chooses whether or not to
                * increase its value
                * this case generates a ToolCardActivationMessage*/
                readDieToModifyAndIncreaseChoice();
                break;
            }
            case INPUT_MOVE_DIE_ON_WINDOW:{
                /*player picks a die from his window and decides
                * where to place it
                * this case generates a ToolCardActivationMessage*/
                readMultiplePositions();
                break;
            }
            case INPUT_CHOOSE_VALUE:{
                /*player chooses a value for a die
                * this case generates a ToolCardActivationMessage*/
                readValue();
                break;
            }
            case INPUT_POSITION_DRAFTPOOL_POSITION_ROUNDTRACK:{
                /*player chooses a die on the draftPool and a die on the RoundTrack
                * this case generates a ToolCardActivationMessage*/
                readDraftPoolRoundTrackPositions();
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
                readPlayerDisabledInput();
                break;
            }
        }
        if(!inputMessage.getRecipient().equals("doNotSend") && playerIsConnected) {
            notifyListeners(inputMessage);
        }
    }

    private synchronized Message readNewConnectionChoice() {
        int choice = 0;
        System.out.print("You've been banned from the match due to inactivity, choose what to do:\n" +
                "1 Reconnect to the game\n" +
                "2 Quit\n" +
                STANDARD_CHOICE_QUOTE);
        threadDisabledInputJoin();
        threadEnabledInputJoin();
        choice = readFromMemory();
        if (choice == -1) {
            choice = readChoiceBetweenValuesIncluded(1, 2, STANDARD_CHOICE_QUOTE, "newCoonectionChoice1");
        } else {
            if (choice < 1 || choice > 2) {
                System.out.println(ERROR_QUOTE);
                choice = readChoiceBetweenValuesIncluded(1, 2, STANDARD_CHOICE_QUOTE, "newCoonectionChoice2");
            }
        }
        if(choice==1){
            return new ComebackMessage(username, "server", username);
        } else {
            return new ErrorMessage(username, "server", "quit");
        }
    }

    private synchronized Message readPlayerName() {
        boolean wrongInput = true;
        String username = "";
        while (wrongInput) {
            System.out.print("New Username: ");
            username = scanner.nextLine();
            if (username.equals("") || username.equals("\n")) {
                System.out.println("Not valid username!");
                wrongInput = true;
            } else {
                wrongInput = false;
            }
        }
        return new CreatePlayerMessage(username, "server", username);
    }

    private synchronized Message readOldPlayerName() {
        String oldUsername;
        Message message = null;
        boolean wrongInput;
        oldUsername = readUsernameFromMemory();
        if (oldUsername.equals("NoUsernameFound")) {
            wrongInput = true;
            while (wrongInput) {
                System.out.print("Old Username: ");
                oldUsername = scanner.nextLine();
                oldUsername = oldUsername.trim();
                if (!oldUsername.equals("") && !oldUsername.equals("\n")) {
                    message = new ComebackMessage(oldUsername, "server", oldUsername);
                    wrongInput = false;
                } else {
                    wrongInput = true;
                }
            }
        } else {
            message = new ComebackMessage(oldUsername, "server", oldUsername);
        }
        return message;
    }

    private synchronized void readSchemaCard() {
        int choice;
        System.out.println("Choose schema card number: ");
        playerEnableThreadHasEnded = false;
        threadDisabledInputJoin();
        choice = readFromMemory();
        if (choice == -1) {
            choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARDS_EXTRACT_NUMBER * 2,
                    "Schema card number: ", "readSchemaCard2");
            if (playerHasBeenBanned || matchHasStarted) {
                saveChoiceToMemory(choice);
            } else {
                System.out.println("Chosen schema name: " + schemaNames[choice - 1]);
                saveMessageToSend(new SelectedSchemaMessage(username, "server", schemaNames[choice - 1]));
            }
        } else {
            if (choice < 1 || choice > Model.SCHEMA_CARDS_EXTRACT_NUMBER * 2) {
                System.out.println(ERROR_QUOTE);
                choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARDS_EXTRACT_NUMBER * 2,
                        "Schema card number: ", "readSchemaCard1");
                if (playerHasBeenBanned || matchHasStarted) {
                    saveChoiceToMemory(choice);
                } else {
                    System.out.println("Chosen schema name: " + schemaNames[choice - 1]);
                    saveMessageToSend(new SelectedSchemaMessage(username, "server", schemaNames[choice - 1]));
                }
            } else {
                if (playerHasBeenBanned || matchHasStarted) {
                    saveChoiceToMemory(choice);
                } else {
                    System.out.println("Chosen schema: " + schemaNames[choice - 1]);
                    saveMessageToSend(new SelectedSchemaMessage(username, "server", schemaNames[choice - 1]));
                }
            }
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void readMove() {
        int choice;
        int toolCardNumber;
        System.out.println("It's your Turn! Type the number of your choice:\n" +
                "1 Place a die\n" +
                "2 Use a ToolCard\n" +
                "3 Do nothing");
        playerEnableThreadHasEnded = false;
        threadDisabledInputJoin();
        choice = readFromMemory();
        if (!playerHasBeenBanned) {
            if (choice == -1) {
                choice = readChoiceBetweenValuesIncluded(1, 3, STANDARD_CHOICE_QUOTE,
                        "readMove1");
            } else {
                if (choice < 1 || choice > 3) {
                    System.out.println(ERROR_QUOTE);
                    choice = readChoiceBetweenValuesIncluded(1, 3, STANDARD_CHOICE_QUOTE,
                            "readMove2");
                }
            }
            if(!playerHasBeenBanned) {
                switch (choice) {
                    case 1: {
                        int diceOnRoundDice;
                        System.out.println("Take a die from the draftPool:");
                        diceOnRoundDice = readChoiceBetweenValuesIncluded(1,
                                Model.MAXIMUM_PLAYER_NUMBER * 2 + 1, "Die position: ", "readMove3");
                        if (!playerHasBeenBanned) {
                            saveMessageToSend(new ChooseDiceMessage(username, "server", diceOnRoundDice - 1));
                        } else {
                            saveChoiceToMemory(diceOnRoundDice);
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Choose a tool card number:");
                        toolCardNumber = readChoiceBetweenValuesIncluded(1, Model.TOOL_CARDS_EXTRACT_NUMBER,
                                "Tool card number: ", "readMove4");
                        if (!playerHasBeenBanned) {
                            saveMessageToSend(new ChooseToolCardMessage(username, "server",
                                    toolCardIDs[toolCardNumber - 1]));
                        } else {
                            saveChoiceToMemory(toolCardNumber);
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("You have chose to forfeit this turn.");
                        saveMessageToSend(new NoActionMessage(username, "server"));
                        break;
                    }
                }
            }
            else {
                saveChoiceToMemory(choice);
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void readChosenDie(){
        int choice;
        System.out.println("Choose a die from the draft pool:");
        StringBuilder builder = new StringBuilder();
        threadDisabledInputJoin();
        playerEnableThreadHasEnded = false;
        choice = readFromMemory();
        if (!playerHasBeenBanned) {
            if (choice == -1) {
                choice = readChoiceBetweenValuesIncluded(1, Model.MAXIMUM_PLAYER_NUMBER * 2 + 1,
                        "Position: ", "readMove5");
            } else {
                if (choice < 1 || choice > Model.MAXIMUM_PLAYER_NUMBER * 2 + 1) {
                    choice = readChoiceBetweenValuesIncluded(1, Model.MAXIMUM_PLAYER_NUMBER * 2 + 1,
                            "Position: ", "readMove6");
                }
            }
            if (!playerHasBeenBanned) {
                builder.append("draftPoolDiePosition: ").append(choice - 1).append(" ");
                saveMessageToSend(new ToolCardActivationMessage(username, "server", toolCardUsageID,
                        builder.toString()));
            } else {
                saveChoiceToMemory(choice);
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void readChosenDieToPlace(){
        int choice;
        System.out.println("Choose a die from the draft pool:");
        playerEnableThreadHasEnded = false;
        choice = readFromMemory();
        StringBuilder builder = new StringBuilder();
        if (!playerHasBeenBanned) {
            if (choice == -1) {
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER * 2 + 1,
                        "Position: ");
            } else {
                if (choice < 1 || choice > Model.MAXIMUM_PLAYER_NUMBER * 2 + 1) {
                    choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER * 2 + 1,
                            "Position: ");
                }
            }
            if (playerHasBeenBanned) {
                saveChoiceToMemory(choice);
            } else {
                if (choice == -1) {
                    saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null));
                } else {
                    builder.append("draftPoolDiePosition: ").append(choice - 1).append(" ");
                    continueInput1(builder);
                }
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void continueInput1(StringBuilder builder){
        int choice;
        System.out.println("Choose where to place the selected die:");
        choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                "Row: ");
        if(!playerHasBeenBanned) {
            if (choice == -1) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("row: ").append(choice - 1).append(" ");
                continueInput2(builder);
            }
        } else {
            saveChoiceToMemory(choice);
        }
    }

    private synchronized void continueInput2(StringBuilder builder){
        int choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        if(!playerHasBeenBanned) {
            if (choice == -1) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("col: ").append(choice - 1).append(" ");
                saveMessageToSend(new ToolCardActivationMessage(username, "server",
                        toolCardUsageID, builder.toString()));
            }
        } else {
            saveChoiceToMemory(choice);
        }
    }

    private synchronized void readPositions(){
        StringBuilder builder = new StringBuilder();
        int choice;
        System.out.println("Choose die position:");
        playerEnableThreadHasEnded = false;
        choice = readFromMemory();
        if (!playerHasBeenBanned) {
            if (choice == -1) {
                choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                        "Row: ", "readPositions");
            } else {
                if (choice < 1 || choice > Model.SCHEMA_CARD_ROWS_NUMBER) {
                    choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                            "Row: ", "readPositions");
                }
            }
            if (playerHasBeenBanned) {
                saveChoiceToMemory(choice);
            } else {
                builder.append("row: ").append(choice - 1).append(" ");
                continueInput3(builder);
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void continueInput3(StringBuilder builder){
        int choice = readChoiceBetweenValuesIncluded(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ", "readPositions");
        if(!playerHasBeenBanned) {
            builder.append("col: ").append(choice - 1).append(" ");
            builder.append("DraftPoolDiePosition: ")
                    .append(draftPoolDiceNumber)
                    .append(" ");
            saveMessageToSend(new DiePlacementMessage(username, "server", builder.toString()));
        } else {
            saveChoiceToMemory(choice);
        }
    }

    private synchronized void readToolPositions(){
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose die position:");
        playerEnableThreadHasEnded = false;
        int choice;
        choice = readFromMemory();
        if(!playerHasBeenBanned) {
            if(choice == -1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                        "Row: ");
            } else {
                if (choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER) {
                    choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                            "Row: ");
                }
            }
            if (playerHasBeenBanned){
                saveChoiceToMemory(choice);
            } else {
                if (choice == -1) {
                    saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null));
                } else {
                    builder.append("row: ").append(choice - 1).append(" ");
                    continueInput4(builder);
                }
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void continueInput4(StringBuilder builder){
        int choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        if(!playerHasBeenBanned) {
            if (choice == -1) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("col: ").append(choice - 1).append(" ");
                builder.append("DraftPoolDiePosition: ");
                builder.append(draftPoolDiceNumber);
                saveMessageToSend(new ToolCardActivationMessage(username, "server",
                        toolCardUsageID, builder.toString()));
            }
        }
    }

    private synchronized void readDieToModify() {
        int choice;
        System.out.println(CHANGE_VALUE_QUOTE);
        playerEnableThreadHasEnded = false;
        choice = readFromMemory();
        if(!playerHasBeenBanned){
            if(choice == -1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                        "Die position number: ");

            } else {
                if (choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                    choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                            "Die position number: ");
                }
            }
            if(playerHasBeenBanned){
                saveChoiceToMemory(choice);
            } else {
                if (choice == -1) {
                    saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null));
                } else {
                    StringBuilder builder = new StringBuilder();
                    builder.append("draftPoolDiePosition: ").append(choice - 1).append(" ");
                    saveMessageToSend(new ToolCardActivationMessage(username, "server",
                            toolCardUsageID, builder.toString()));
                }
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void readDieToModifyAndIncreaseChoice(){
        int choice;
        StringBuilder builder = new StringBuilder();
        System.out.println(CHANGE_VALUE_QUOTE);
        playerEnableThreadHasEnded = false;
        threadDisabledInputJoin();
        choice = readFromMemory();
        if(!playerHasBeenBanned) {
            if(choice == -1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                        "Die position number: ");
            } else {
                if(choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                    choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                            "Die position number: ");
                }
            }
            if(playerHasBeenBanned){
                saveChoiceToMemory(choice);
            } else {
                if (choice == -1) {
                    saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null));
                } else {
                    builder.append("draftPoolDiePosition: ").append(choice - 1).append(" ");
                    continueInput7(builder);
                }
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void continueInput7(StringBuilder builder){
        int choice;
        System.out.println("Do you want to increase the die value? ");
        String stringChoice = readYesOrNoWithQuit("( Y / N ) : ");
        if(playerHasBeenBanned){
            try{
                choice = Integer.parseInt(stringChoice);
                saveChoiceToMemory(choice);
            } catch (NumberFormatException e){
                choice = readChoiceBetweenValuesWithQuit(1,2, STANDARD_CHOICE_QUOTE);
                saveChoiceToMemory(choice);
            }
        } else {
            if (stringChoice.equals("Error")) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("IncreaseValue: ")
                        .append(stringChoice)
                        .append(" ");
                saveMessageToSend(new ToolCardActivationMessage(username, "server",
                        toolCardUsageID, builder.toString()));
            }
        }
    }

    private synchronized void readMultiplePositions(){
        StringBuilder builder = new StringBuilder();
        int choice;
        System.out.println("Choose a die to move from your window:");
        playerEnableThreadHasEnded = false;
        choice = readFromMemory();
        if(!playerHasBeenBanned) {
            if(choice == -1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                        "Row: ");
            } else {
                if (choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                    choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                            "Row: ");
                }
            }
            if(playerHasBeenBanned){
                saveChoiceToMemory(choice);
            } else {
                if (choice == -1) {
                    saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null));
                } else {
                    builder.append("OldDieRow: ").append(choice - 1).append(" ");
                    continueInput8(builder);
                }
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void continueInput8(StringBuilder builder){
        int choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        if(!playerHasBeenBanned) {
            if (choice == -1) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("OldDieCol: ").append(choice - 1).append(" ");
                continueInput9(builder);
            }
        } else {
            saveChoiceToMemory(choice);
        }
    }

    private synchronized void continueInput9(StringBuilder builder){
        System.out.println("Choose a new position where to place the selected die:");
        int choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_ROWS_NUMBER,
                "Row: ");
        if(!playerHasBeenBanned) {
            if (choice == -1) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("newDieRow: ").append(choice - 1).append(" ");
                continueInput10(builder);
            }
        } else {
            saveChoiceToMemory(choice);
        }
    }

    private synchronized void continueInput10(StringBuilder builder){
        int choice = readChoiceBetweenValuesWithQuit(1, Model.SCHEMA_CARD_COLUMNS_NUMBER,
                "Column: ");
        if(!playerHasBeenBanned) {
            if (choice == -1) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("newDieCol: ").append(choice - 1).append(" ");
                saveMessageToSend(new ToolCardActivationMessage(username, "server",
                        toolCardUsageID, builder.toString()));
            }
        } else {
            saveChoiceToMemory(choice);
        }
    }

    private synchronized void readValue(){
        int choice;
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose a new value for the drafted die:");
        playerEnableThreadHasEnded = false;
        choice = readFromMemory();
        if(!playerHasBeenBanned) {
            if(choice == -1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_DIE_NUMBER,
                        "Value: ");
            } else {
                if (choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                    choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_DIE_NUMBER,
                            "Value: ");
                }
            }
            if(playerHasBeenBanned){
                saveChoiceToMemory(choice);
            } else {
                if (choice == -1) {
                    saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null));
                } else {
                    builder.append("NewValue: ").append(choice).append(" ");
                    builder.append("draftPoolDiePosition: ").append(draftPoolDiceNumber).append(" ");
                    saveMessageToSend(new ToolCardActivationMessage(username, "server",
                            toolCardUsageID, builder.toString()));
                }
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void readDraftPoolRoundTrackPositions(){
        int choice;
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose a die from the draft pool: ");
        playerEnableThreadHasEnded = false;
        choice = readFromMemory();
        if(!playerHasBeenBanned){
            if(choice == -1){
                choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                        "Position: ");
            } else {
                if (choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                    choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                            "Position: ");
                }
            }
            if(playerHasBeenBanned){
                saveChoiceToMemory(choice);
            } else {
                if (choice == -1) {
                    saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null));
                } else {
                    builder.append("DraftPoolPosition: ")
                            .append(choice - 1)
                            .append(" ");
                    continueInput5(builder);
                }
            }
        } else {
            saveChoiceToMemory(choice);
        }
        playerEnableThreadHasEnded = true;
    }

    private synchronized void continueInput5(StringBuilder builder){
        System.out.println("Choose a round of the round track: ");
        int choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_ROUND_NUMBER, "Round: ");
        if(!playerHasBeenBanned) {
            if (choice == -1) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("RoundNumber: ")
                        .append(choice - 1)
                        .append(" ");
                continueInput6(builder);
            }
        } else {
            saveChoiceToMemory(choice);
        }
    }

    private synchronized void continueInput6(StringBuilder builder){
        System.out.println("Choose a die from the Round Track:");
        int choice = readChoiceBetweenValuesWithQuit(1, Model.MAXIMUM_PLAYER_NUMBER*2+1,
                "Position: ");
        if(!playerHasBeenBanned) {
            if (choice == -1) {
                saveMessageToSend(new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null));
            } else {
                builder.append("RoundTrackPosition: ")
                        .append(choice - 1)
                        .append(" ");
                saveMessageToSend(new ToolCardActivationMessage(username, "server",
                        toolCardUsageID, builder.toString()));
            }
        } else {
            saveChoiceToMemory(choice);
        }
    }

    private synchronized void readPlayerDisabledInput(){
        inputControllerThread.setPlayerDisabledThreadHasEnded(false);
        while (!playerIsActive){
            StringBuilder builder = new StringBuilder();
            builder.append(inputMemoryString);
            String playerDisableInput = scanner.nextLine();
            builder.append(playerDisableInput)
                    .append("\n");
            inputControllerThread.setInputMemoryString(builder.toString());
        }
        inputControllerThread.setPlayerDisabledThreadHasEnded(true);
        saveMessageToSend(new ErrorMessage(username, "doNotSend", "doNotSend"));
    }

    private synchronized int readChoiceBetweenValuesIncluded(int firstValue, int secondValue, String inputInstructions, String methodName){
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
    private synchronized int readChoiceBetweenValuesWithQuit(int firstValue, int secondValue, String inputInstructions){
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
    private synchronized String readYesOrNoWithQuit(String inputInstructions){
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
    private int readFromMemory(){
        int choice;
        String[] inputLines = inputMemoryString.split("\n");
        if(inputLines[inputLines.length-1].equals("null")){
            /*player has not typed nothing before his turn*/
            setInputMemoryString("null\n");
            return -1;
        } else {
            /*player has typed something before his turn*/
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

    private String readUsernameFromMemory(){
        String[] inputLines = inputMemoryString.split("\n");
        if(inputLines[inputLines.length-1].equals("null")){
            /*player has typed nothing before his turn*/
            setInputMemoryString("null\n");
            return "NoUsernameFound";
        } else {
            /*player has typed something before his turn*/
            return inputLines[inputLines.length - 1];
        }
    }

    private void saveMessageToSend(Message message){
        inputMessage = message;
    }

    private void saveChoiceToMemory(int choice){
        StringBuilder builder = new StringBuilder();
        builder.append(inputMemoryString)
                .append("\n").append(choice).append("\n");
        setInputMemoryString(builder.toString());
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

    public static void setPlayerBanned(boolean isPlayerBanned){
        playerHasBeenBanned = isPlayerBanned;
    }

    public static void setMatchStarted(boolean isMatchStarted){
        matchHasStarted = isMatchStarted;
    }

    public static void setPlayerIsConnected(Boolean playerIsConnected){
        inputControllerThread.playerIsConnected = playerIsConnected;
    }
    private void threadDisabledInputJoin(){
        while (!playerDisabledThreadHasEnded){
            try {
                /*waiting for the player inactive thread to end*/
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
    }

    private void threadEnabledInputJoin(){
        while (!playerEnableThreadHasEnded){
            try {
                /*waiting for the player inactive thread to end*/
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
    }
}