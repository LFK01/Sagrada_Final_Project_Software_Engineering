package it.polimi.se2018.view;

import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
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

    private static final String QUIT_QUOTE = "Type \"quit\" anytime to interrupt the move\n";
    private static final String ERROR_QUOTE = "Wrong input!";
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

    public NotifyingThread(InputManager inputManager, String username, String toolCardUsageID) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = null;
        this.toolCardIDs = null;
        this.toolCardUsageID = toolCardUsageID;
        this.draftPoolDiceNumber = -1;
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
            }
        } finally {
            notifyListeners(inputMessage);
        }
    }

    private Message readNewConnectionChoice() {
        String input;
        boolean wrongInput = true;
        int choice = -1;
        System.out.println("You've been banned from the match due to inactivity, choose what to do:\n" +
                "1 Reconnect to the game\n" +
                "2 Quit");
        while(wrongInput){
            System.out.print("choice: ");
            input = scanner.nextLine();
            try{
                choice = Integer.parseInt(input);
                if(choice<1||choice>2){
                    System.out.println("ERROR_QUOTE");
                    wrongInput = true;
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
                System.out.println(ERROR_QUOTE);
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

    private Message readSchemaCard(){
        boolean wrongInput = true;
        String input;
        int choice = -1;
        while(wrongInput){
            try{
                System.out.println("Schema card number: ");
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                System.out.println("choice = " + choice);
                if(choice<1||choice>4){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        System.out.println("schemaName: " + schemaNames[choice-1]);
        return new SelectedSchemaMessage(username,"server", schemaNames[choice -1]);
    }

    private Message readMove() {
        String input;
        Message message = null;
        int choice = -1;
        int toolCardNumber = -1;
        System.out.println("It's your Turn! Type the number of your choice:" +
                "\n1 Place a die" +
                "\n2 Use a ToolCard" +
                "\n3 Do nothing");
        boolean wrongInput = true;
        while(wrongInput){
            try{
                System.out.print("Choice: ");
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if(choice<1 || choice>3){
                    System.out.println("Wrong Input");
                    wrongInput = true;
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
                                if (diceOnRoundDice < 1 || diceOnRoundDice > Model.MAXIMUM_PLAYER_NUMBER*2 +1) {
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
                        message = new ChooseDiceMove(username,"server", diceOnRoundDice);
                    }
                    if(choice == 2){
                        wrongInput = true;
                        while (wrongInput){
                            System.out.print("Tool card number: ");
                            try{
                                input = scanner.nextLine();
                                System.out.println("choose: " + input);
                                toolCardNumber = Integer.parseInt(input);
                                System.out.println("read: " + toolCardNumber);
                                if(toolCardNumber < 1 || toolCardNumber > Model.TOOL_CARDS_EXTRACT_NUMBER){
                                    System.out.println(ERROR_QUOTE);
                                    wrongInput = true;
                                } else {
                                    wrongInput = false;
                                    System.out.println("decided to use: " +
                                            toolCardIDs[toolCardNumber-1]);
                                    message = new UseToolCardMove(username, "server",
                                            toolCardIDs[toolCardNumber-1]);
                                }
                            } catch (NumberFormatException e){
                                System.out.println(ERROR_QUOTE);
                                wrongInput = true;
                            }
                        }
                    }
                    if (choice == 3){
                        System.out.println("You have chose to forfeit this turn");
                        message = new NoActionMove(username,"server");
                    }
                }
            } catch (NumberFormatException e){
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        return message;
    }

    private Message readChosenDie(){
        System.out.println("Choose a die from the draft pool:");
        return new ToolCardActivationMessage(username, "server", toolCardUsageID,
                getDraftPoolNumber());
    }

    private Message readChosenDieToPlace(){
        Message message;
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose a die from the draft pool:");
        builder.append(getDraftPoolNumber());
        System.out.println("Choose where to place the selected die:");
        builder.append(getRowNumberColNumber());
        message = new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
        return message;
    }

    private Message readPositions(){
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose die position:");
        String rowColValues = getRowNumberColNumber();
        builder.append(rowColValues);
        builder.append("DraftPoolDiePosition: ");
        builder.append(draftPoolDiceNumber).append(" ");
        System.out.println("player has entered this values: \n" + rowColValues);
        System.out.println(builder.toString());
        return new DiePlacementMessage(username,"server", builder.toString());
    }

    private Message readToolPositions(){
        StringBuilder builder = new StringBuilder();
        System.out.println(QUIT_QUOTE
                + "Choose die position:");
        builder.append(getRowNumberColNumber());
        builder.append("DraftPoolDiePosition: ");
        builder.append(draftPoolDiceNumber);
        System.out.println(builder.toString());
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    private Message readDieToModify() {
        boolean wrongInput = true;
        String input;
        int choice = -1;
        while (wrongInput){
            System.out.print("Type \" quit \" anytime to interrupt the move\n"
                    + "Choose a die from the draft pool to change its values: " +
                    "\nDie position number: ");
            try{
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                choice = Integer.parseInt(input);
                if(choice<1 || choice > Model.MAXIMUM_PLAYER_NUMBER*2+1){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
                System.out.println(ERROR_QUOTE);
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("draftPoolDiePosition: ").append(choice-1).append(" ");
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    private Message readDieToModifyAndIncreaseChoice(){
        String input;
        int choice = -1;
        boolean wrongInput = true;
        while (wrongInput){
            System.out.print(QUIT_QUOTE
                    + "Choose a die from the draft pool to change its values: " +
                    "\nDie position number: ");
            try{
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
                System.out.println(ERROR_QUOTE);
            }
        }
        wrongInput = true;
        StringBuilder builder = new StringBuilder();
        builder.append("draftPoolDiePosition: ").append(choice-1).append(" ");
        System.out.println("Do you want to increase the die value? (Y/N)");
        while (wrongInput){
            input = scanner.nextLine();
            input.replace("\"", "");
            input.trim();
            if(input.equalsIgnoreCase("quit")){
                return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                        "InputQuit", null);
            }
            if(input.equalsIgnoreCase("y")||input.equalsIgnoreCase("n")){
                wrongInput = false;
                builder.append("IncreaseValue: ").append(input);
            } else {
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    private Message readMultiplePositions(){
        StringBuilder builder = new StringBuilder();
        boolean wrongInput = true;
        int positionIndex = 0;
        String input;
        int choice = -1;
        System.out.println("Choose a die to move from your window:");
        while(wrongInput){
            System.out.print("Row: ");
            try {
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                    wrongInput = true;
                } else {
                    wrongInput = false;
                    builder.append("OldDieRow: ").append(choice-1).append(" ");
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
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.SCHEMA_CARD_COLUMNS_NUMBER){
                    wrongInput = true;
                } else {
                    wrongInput = false;
                    builder.append("OldDieCol: ").append(choice-1).append(" ");
                    positionIndex++;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
            }
        }
        System.out.println("Choose a new position where to place the selected die:");
        wrongInput = true;
        while(wrongInput){
            System.out.print("Row: ");
            try {
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                    wrongInput = true;
                } else {
                    builder.append("newDieRow: ").append(choice-1).append(" ");
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
                positionIndex++;
            }
        }
        wrongInput = true;
        while (wrongInput){
            System.out.print("Column: ");
            try {
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.SCHEMA_CARD_COLUMNS_NUMBER){
                    wrongInput = true;
                } else {
                    wrongInput = false;
                    builder.append("newDieCol: ").append(choice-1).append(" ");
                    positionIndex++;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
            }
        }
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    private Message readValue(){
        boolean wrongInput = true;
        int choice = -1;
        String input;
        StringBuilder builder = new StringBuilder();
        System.out.println(QUIT_QUOTE
                + "Choose a value for your die:");
        while (wrongInput){
            System.out.print("Value: ");
            try {
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                choice = Integer.parseInt(input);
                if(choice<1 || choice >Model.MAXIMUM_DIE_NUMBER){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
                System.out.println(ERROR_QUOTE);
            }
        }
        builder.append("NewValue: ").append(choice).append(" ");
        builder.append("draftPoolDiePosition: ").append(draftPoolDiceNumber).append(" ");
        return new ToolCardActivationMessage(username, "server", toolCardUsageID, builder.toString());
    }

    private Message readDraftPoolRoundTrackPositions(){
        boolean wrongInput = true;
        String input;
        int draftPoolPosition = -1;
        int roundNumber = -1;
        int roundTrackPosition = -1;
        System.out.println(QUIT_QUOTE
                + "Choose a die from the draft pool: ");
        while (wrongInput){
            System.out.print("Position: ");
            try{
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                draftPoolPosition = Integer.parseInt(input);
                if(draftPoolPosition<1 || draftPoolPosition>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        wrongInput = true;
        System.out.println("Choose a round of the Round Track: ");
        while (wrongInput){
            System.out.print("Round: ");
            try {
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                roundNumber = Integer.parseInt(input);
                if(roundNumber<1 || roundNumber>Model.MAXIMUM_ROUND_NUMBER){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        wrongInput = true;
        System.out.println("Choose a die from the Round Track:");
        while (wrongInput){
            System.out.print("Position: ");
            try {
                input = scanner.nextLine();
                input.replace("\"", "");
                input.trim();
                if(input.equalsIgnoreCase("quit")){
                    return new ToolCardErrorMessage(username, "server", toolCardUsageID,
                            "InputQuit", null);
                }
                roundTrackPosition = Integer.parseInt(input);
                if(roundTrackPosition<1 || roundTrackPosition>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("DraftPoolPosition: ").append(draftPoolPosition-1).append(" ");
        builder.append("RoundNumber: ").append(roundNumber-1).append(" ");
        builder.append("RoundTrackPosition: ").append(roundTrackPosition-1).append(" ");
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageID, builder.toString());
    }

    private String getRowNumberColNumber(){
        StringBuilder builder = new StringBuilder();
        boolean wrongInput = true;
        String input;
        int row = -1;
        while (wrongInput) {
            System.out.print("Row: ");
            try {
                input = scanner.nextLine();
                row = Integer.parseInt(input);
                if (row < 1 || row > Model.SCHEMA_CARD_ROWS_NUMBER) {
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    row = row-1;
                    wrongInput = false;
                    builder.append("row: ").append(row).append(" ");
                }
            } catch (NumberFormatException e) {
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        wrongInput = true;
        int col = -1;
        while (wrongInput) {
            System.out.print("Column: ");
            try {
                input = scanner.nextLine();
                col = Integer.parseInt(input);
                if (col < 1 || col > Model.SCHEMA_CARD_COLUMNS_NUMBER) {
                    System.out.println(ERROR_QUOTE);
                    wrongInput = true;
                } else {
                    col = col-1;
                    wrongInput = false;
                    builder.append("col: ").append(col).append(" ");
                }
            } catch (NumberFormatException e) {
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        return builder.toString();
    }

    private String getDraftPoolNumber(){
        boolean wrongInput = true;
        StringBuilder builder = new StringBuilder();
        String input;
        int choice;
        while (wrongInput){
            try {
                System.out.print("Position: ");
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                    wrongInput = true;
                    System.out.println(ERROR_QUOTE);
                } else {
                    wrongInput = false;
                    builder.append("draftPoolDiePosition: ").append(choice-1).append(" ");
                }
            } catch (NumberFormatException e){
                System.out.println(ERROR_QUOTE);
                wrongInput = true;
            }
        }
        return builder.toString();
    }
}