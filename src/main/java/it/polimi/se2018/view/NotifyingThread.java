package it.polimi.se2018.view;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.DiePlacementMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.model.events.messages.SelectedSchemaMessage;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotifyingThread extends Thread{

    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<>();
    private final InputManager inputManager;
    private Scanner scanner;
    private final String username;
    private final String toolCardUsageName;
    private final int draftPoolDiceNumber;
    private String[] schemaNames;
    private String[] toolCardNames;


    public NotifyingThread(InputManager inputManager, String username, String toolCardUsageName,
                           String[] names) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = names;
        this.toolCardNames = names;
        this.toolCardUsageName = toolCardUsageName;
        this.draftPoolDiceNumber = -1;
    }

    public NotifyingThread(InputManager inputManager, String username, String toolCardUsageName) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = null;
        this.toolCardNames = null;
        this.toolCardUsageName = toolCardUsageName;
        this.draftPoolDiceNumber = -1;
    }

    public NotifyingThread(InputManager inputManager, String username, String toolCardUsageName,
                           int draftPoolDiceNumber) {
        scanner = new Scanner(new InputStreamReader(System.in));
        this.inputManager = inputManager;
        this.username = username;
        this.schemaNames = null;
        this.toolCardNames = null;
        this.toolCardUsageName = toolCardUsageName;
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
                case INPUT_PLAYER_NAME:{
                    inputMessage = readPlayerName();
                    break;
                }
                case INPUT_SCHEMA_CARD:{
                    inputMessage = readSchemaCard();
                    break;
                }
                case INPUT_CHOOSE_MOVE:{
                    inputMessage = readMove();
                    break;
                }
                case INPUT_CHOOSE_DIE:{
                    inputMessage = readChosenDie();
                    break;
                }
                case INPUT_PLACE_DIE:{
                    inputMessage = readPositions();
                    break;
                }
                case INPUT_MODIFY_DIE_VALUE:{
                    inputMessage = readDieToModify();
                    break;
                }
                case INPUT_INCREASE_DIE_VALUE:{
                    inputMessage = readDieToModifyAndIncreaseChoice();
                    break;
                }
                case INPUT_MOVE_DIE_ON_WINDOW:{
                    inputMessage = readMultiplePositions();
                    break;
                }
                case INPUT_CHOOSE_VALUE:{
                    inputMessage = readValue();
                    break;
                }
                case INPUT_POSITION_DRAFTPOOL_POSITION_ROUNDTRACK:{
                    inputMessage = readDraftPoolRoundTrackPositions();
                    break;
                }
                case INPUT_VOID_TOOL_CARD:{
                    break;
                }
            }
        } finally {
            notifyListeners(inputMessage);
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
                    System.out.println("Wrong input!");
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                System.out.println("Wrong input!");
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
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                System.out.println("choice = " + choice);
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
                        System.out.println("HAI SCELTO QUESTO DADO" + diceOnRoundDice);
                        message = new ChooseDiceMove(username,"server",diceOnRoundDice);
                    }
                    if(choice == 2){
                        System.out.println("Tool card number: ");
                        try{
                            input = scanner.nextLine();
                            toolCardNumber = Integer.parseInt(input);
                            if(toolCardNumber<1 || toolCardNumber >Model.TOOL_CARDS_EXTRACT_NUMBER){
                                System.out.println("Wrong input!");
                                wrongInput = true;
                            } else {
                                wrongInput = false;
                                System.out.println("decided to use: " +
                                        toolCardNames[toolCardNumber-1].split(" ")[1].replace("/", " "));
                                if(toolCardNames[toolCardNumber-1].split(" ")[0].equals("Name:")){
                                    message = new UseToolCardMove(username, "server",
                                            toolCardNames[toolCardNumber-1].split(" ")[1].replace("/", " "));
                                }
                            }
                        } catch (NumberFormatException e){
                            System.out.println("Wrong input!");
                            wrongInput = true;
                        }
                    }
                    if (choice == 3){
                        System.out.println("Ho deciso di passare il turno");
                        message = new NoActionMove(username,"server");
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Wrong input!");
                wrongInput = true;
            }
        }
        return message;
    }

    private Message readChosenDie(){
        Message message = null;
        StringBuilder builder = new StringBuilder();
        boolean wrongInput = true;
        String input;
        int choice = -1;
        System.out.println("Choose a die from the draft pool: ");
        while (wrongInput){
            try {
                System.out.print("Position: ");
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
                    wrongInput = true;
                    System.out.println("Wrong Input!");
                } else {
                    wrongInput = false;
                    builder.append("Position: ").append(choice-1);
                    message = new ToolCardActivationMessage(username, "server",
                            toolCardUsageName.replace(" ", "/"), builder.toString());
                }
            } catch (NumberFormatException e){
                System.out.println("Wrong Input!");
                wrongInput = true;
            }
        }
        return message;
    }

    private Message readPositions(){
        StringBuilder builder = new StringBuilder();
        boolean wrongInput = true;
        String input;
        int row = -1;
        System.out.println("Choose die position:");
        while (wrongInput) {
            System.out.print("readPositions thread) Row: ");
            try {
                input = scanner.nextLine();
                row = Integer.parseInt(input);
                if (row < 1 || row > Model.SCHEMA_CARD_ROWS_NUMBER) {
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
            System.out.print("readPositions thread) Column: ");
            try {
                input = scanner.nextLine();
                col = Integer.parseInt(input);
                if (col < 1 || col > Model.SCHEMA_CARD_COLUMNS_NUMBER) {
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
        return new DiePlacementMessage(username,"server", builder.toString());
    }

    private Message readDieToModify() {
        boolean wrongInput = true;
        String input;
        int choice = -1;
        while (wrongInput){
            System.out.print("readDieToModify) Choose a die from the draft pool to change its values: " +
                    "\nDie position number: ");
            try{
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if(choice<1 || choice > Model.MAXIMUM_PLAYER_NUMBER*2+1){
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
        StringBuilder builder = new StringBuilder();
        builder.append("DiePosition: ").append(choice-1).append(" ");
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageName.replace(" ", "/"), builder.toString());
    }

    private Message readDieToModifyAndIncreaseChoice(){
        String input;
        int choice = -1;
        boolean wrongInput = true;
        while (wrongInput){
            System.out.print("Choose a die from the draft pool to change its values: " +
                    "\nDie position number: ");
            try{
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.MAXIMUM_PLAYER_NUMBER*2+1){
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
        builder.append("DiePosition: ").append(choice-1).append(" ");
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
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageName.replace(" ", "/"), builder.toString());
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
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                    wrongInput = true;
                } else {
                    wrongInput = false;
                    builder.append("oldDieRow: ").append(choice).append("\n");
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
                if(choice<1 || choice>Model.SCHEMA_CARD_COLUMNS_NUMBER){
                    wrongInput = true;
                } else {
                    wrongInput = false;
                    builder.append("oldDieCol: ").append(choice).append("\n");
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
                if(choice<1 || choice>Model.SCHEMA_CARD_ROWS_NUMBER){
                    wrongInput = true;
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
                builder.append("newDieRow: ").append(choice).append("\n");
                positionIndex++;
            }
        }
        wrongInput = true;
        while (wrongInput){
            System.out.print("Column: ");
            try {
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if(choice<1 || choice>Model.SCHEMA_CARD_COLUMNS_NUMBER){
                    wrongInput = true;
                } else {
                    wrongInput = false;
                    builder.append("newDieCol: ").append(choice).append("\n");
                    positionIndex++;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
            }
        }
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageName.replace(" ", "/"), builder.toString());
    }

    private Message readValue(){
        boolean wrongInput = true;
        int choice = -1;
        String input;
        StringBuilder builder = new StringBuilder();
        System.out.println("Choose a value for your die:");
        while (wrongInput){
            System.out.print("Value: ");
            try {
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if(choice<1 || choice >Model.MAXIMUM_DIE_NUMBER){
                    wrongInput = true;
                    System.out.println("Wrong Input!");
                } else {
                    wrongInput = false;
                }
            } catch (NumberFormatException e){
                wrongInput = true;
                System.out.println("Wrong Input!");
            }
        }
        builder.append("NewValue: ").append(choice).append(" ");
        return new ToolCardActivationMessage(username, "server", toolCardUsageName, builder.toString());
    }

    private Message readDraftPoolRoundTrackPositions(){
        boolean wrongInput = true;
        String input;
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
                if(roundNumber<1 || roundNumber>Model.MAXIMUM_ROUND_NUMBER){
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
                if(roundTrackPosition<1 || roundTrackPosition>Model.MAXIMUM_PLAYER_NUMBER*2+1){
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
        builder.append("DraftPoolPosition: ").append(draftPoolPosition-1).append(" ");
        builder.append("RoundNumber: ").append(roundNumber-1).append(" ");
        builder.append("RoundTrackPosition: ").append(roundTrackPosition-1).append(" ");
        return new ToolCardActivationMessage(username, "server",
                toolCardUsageName.replace(" ", "/"), builder.toString());
    }
}