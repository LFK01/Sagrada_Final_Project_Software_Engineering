package it.polimi.se2018.view;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;
import it.polimi.se2018.view.comand_line.InputManager;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author giovanni
 *  This class, through update (Message), sends information and asks for input to the various players.
 *  This class, through update (Message) sends information and asks for input to the various players
 */
public class View extends ProjectObservable implements ProjectObserver, ThreadCompleteListener{

    private boolean playerIsBanned;
    private boolean gameHasEnded;
    private boolean playerWantsToContinue;
    private boolean serverIsUp;

    private String username;

    private NotifyingThread inputThread;

    private InputManager inputManager;
    private String[] schemaName = new String[4];
    private String[] toolCardNames;

    private String[] toolCardIDs;
    private String privateObjectiveCardDescription;
    /**
     * Initializes view
     */
    public View(){
        username = super.toString();
        playerIsBanned = false;
        gameHasEnded = false;
        toolCardNames = new String[Model.TOOL_CARDS_EXTRACT_NUMBER];
        toolCardIDs = new String[Model.TOOL_CARDS_EXTRACT_NUMBER];
    }

    /**
     * shows up a login window where the user can choose her/his name
     */
    public void createPlayer(){
        inputManager = InputManager.INPUT_PLAYER_NAME;
        inputThread = new NotifyingThread(inputManager, username);
        inputThread.addListener(this);
        inputThread.start();
    }

    public void showPrivateObjectiveCard(String description){
        System.out.println("Your private objective is: " + description);
        privateObjectiveCardDescription = description;
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        for(int i=0;i<4;i++) {
            System.out.println("type " + (i+1) + " to choose this schema");
            System.out.println(chooseSchemaMessage.getSchemaCards(i));
            schemaName[i]= chooseSchemaMessage.getSchemaCards(i).split("\n")[0];
        }
        inputManager = InputManager.INPUT_SCHEMA_CARD;
        NotifyingThread.setPlayerIsActive(true);
        NotifyingThread.setPlayerBanned(false);
        inputThread = new NotifyingThread(inputManager, username, schemaName);
        inputThread.addListener(this);
        inputThread.start();
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        /*should never be called*/
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage) {
        /*should never be called*/
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ErrorMessage errorMessage) {
        if(errorMessage.toString().equalsIgnoreCase("NotValidUsername")){
            System.out.println("Username not available!");
            this.createPlayer();
        }
        if(errorMessage.toString().equalsIgnoreCase("PlayerNumberExceeded")){
            System.out.print("Maximum player number reached, impossible to connect");
            serverIsUp = false;
        }
        if(errorMessage.toString().equalsIgnoreCase("PlayerUnableToUseToolCard")){
            System.out.println("Professor Oak: \"it's not the time to use that!\"");
            NotifyingThread.setPlayerIsActive(true);
            NotifyingThread.setPlayerBanned(false);
            inputManager = InputManager.INPUT_CHOOSE_MOVE;
            inputThread = new NotifyingThread(inputManager, username, toolCardIDs);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equalsIgnoreCase("OldUsernameNotFound")){
            serverIsUp = false;
        }
        if(errorMessage.toString().equalsIgnoreCase("OldUsernameFound")){
            username = errorMessage.getRecipient();
            serverIsUp = true;
        }
        if(errorMessage.toString().equalsIgnoreCase("NotEnoughFavorTokens")){
            System.out.println("You need more tokens to activate this card!");
            NotifyingThread.setPlayerIsActive(true);
            NotifyingThread.setPlayerBanned(false);
            inputManager = InputManager.INPUT_CHOOSE_MOVE;
            inputThread = new NotifyingThread(inputManager, username, toolCardIDs);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equals("FullCellError")){
            System.out.println("You can't place a die over another die!");
            NotifyingThread.setPlayerIsActive(true);
            NotifyingThread.setPlayerBanned(false);
            inputManager =InputManager.INPUT_CHOOSE_MOVE;
            inputThread = new NotifyingThread(inputManager, username, toolCardIDs);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equals("InvalidPositionError")){
            System.out.println("Selected die doesn't respect cell's restrictions");
            NotifyingThread.setPlayerIsActive(true);
            NotifyingThread.setPlayerBanned(false);
            inputManager =InputManager.INPUT_CHOOSE_MOVE;
            inputThread = new NotifyingThread(inputManager, username, toolCardIDs);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equals("DiceMoveAlreadyUsed")){
            System.out.println("You have already used all your moves in this round");
            NotifyingThread.setPlayerIsActive(true);
            NotifyingThread.setPlayerBanned(false);
            inputManager = InputManager.INPUT_CHOOSE_MOVE;
            inputThread = new NotifyingThread(inputManager, username, toolCardIDs);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equalsIgnoreCase("NotValidDraftPoolPosition")){
            System.out.println("Not Valid DraftPoolPosition");
            NotifyingThread.setPlayerIsActive(true);
            NotifyingThread.setPlayerBanned(false);
            inputManager = InputManager.INPUT_CHOOSE_MOVE;
            inputThread = new NotifyingThread(inputManager, username);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equalsIgnoreCase("TimeElapsed")){
            NotifyingThread.setPlayerBanned(true);
            NotifyingThread.setPlayerIsActive(true);
            inputManager = InputManager.INPUT_NEW_CONNECTION;
            inputThread = new NotifyingThread(inputManager, username);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equalsIgnoreCase("ServerIsDown")){
            setServerIsUp(false);
        }
        if(errorMessage.toString().equalsIgnoreCase("LobbyTimeEnded")){
            System.out.println("Match has already started!");
            setServerIsUp(false);
        }
        if(errorMessage.toString().equalsIgnoreCase("AlreadyExistingPlayer")){
            System.out.println("Unable to connect!");
            setServerIsUp(false);
        }
    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        String playingPlayer;
        boolean alreadyRead = false;
        NotifyingThread.setMatchStarted(true);
        String[] words = sendGameboardMessage.getGameboardInformation().split("/");
        for(int i =0; i<words.length;i++){
            int cardNumber=1;
            if (words[i].equalsIgnoreCase("PublicObjectiveCards:")) {
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
                        System.out.println("ToolCards #" + cardNumber + " name: " + words[i + 1]);
                        toolCardNames[cardNumber-1]= "Name: " + words[i+1].replace(" ", "/") + " ";
                        i+=2;
                    }
                    if(words[i].equalsIgnoreCase("ID:")){
                        toolCardIDs[cardNumber-1] = words[i+1];
                        cardNumber++;
                        i+=2;
                    }
                    if (words[i].equalsIgnoreCase("Description:")) {
                        System.out.println("Description: : " + words[i + 1] + "\n");
                        i+=2;
                    }
                    if(words[i].equalsIgnoreCase("SchemaCards:")){
                        alreadyRead = true;
                    }
                }
            }
            if(words[i].equalsIgnoreCase("SchemaCards:")){
                System.out.println("reading schema cards");
                while(!words[i].equalsIgnoreCase("schemaStop:")){
                    System.out.println(words[i]);
                    i++;
                }
            }
            alreadyRead=false;
            if(words[i].equalsIgnoreCase("DiceList:")){
                System.out.println("Draft pool: ");
                while (!alreadyRead) {
                    System.out.print(words[i + 1] + " ");
                    i++;
                    if(words[i+1].equalsIgnoreCase("DiceStop")){
                        alreadyRead = true;
                    }
                }
            }
            if(words[i].equalsIgnoreCase("FavorTokens:")){
                System.out.println("\n\nYou have " + words[i+1] + " favor tokens left\n");
            }
            if(words[i].equalsIgnoreCase("playingPlayer:")){
                playingPlayer = words[i+1];
                System.out.println("Private Objective Card: " + privateObjectiveCardDescription + "\n");
                if(playingPlayer.equals(username)){
                    NotifyingThread.setPlayerIsActive(true);
                    NotifyingThread.setPlayerBanned(false);
                    inputManager = InputManager.INPUT_CHOOSE_MOVE;
                    inputThread = new NotifyingThread(inputManager, username, toolCardIDs);
                    inputThread.addListener(this);
                    inputThread.start();
                }else{
                    System.out.println("It's not your turn!");
                    if(!inputThread.isAlive()) {
                        inputManager = InputManager.INPUT_PLAYER_DISABLED;
                        NotifyingThread.setPlayerIsActive(false);
                        NotifyingThread.setPlayerBanned(false);
                        inputThread = new NotifyingThread(inputManager, username, toolCardIDs);
                        inputThread.addListener(this);
                        inputThread.start();
                    }
                }
            }
        }
    }

    @Override
    public void update(NoActionMove noActionMove) {
        /*should never be called*/
    }

    @Override
    public void update(RequestMessage requestMessage) {
        int draftPoolDiceNumber = -1;
        String toolCardUsageName = "";
        for(String name: toolCardNames){
            System.out.println(name);
        }
        ArrayList<String> words = new ArrayList<>(Arrays.asList(requestMessage.getValues().split(" ")));
        for(String word: words){
            if(word.equalsIgnoreCase("RoundTrack:")){
                int i=1;
                int roundNumber=1;
                System.out.println("RoundTrack:\n" +
                        "Round #" + roundNumber + " : ");
                while(!words.get(words.indexOf(word)+i).equalsIgnoreCase("DiceStop")){
                    if(words.get(words.indexOf(word)+i).equals("\n")){
                        if(!words.get(words.indexOf(word)+i+1).equals("DiceStop")) {
                            roundNumber++;
                            System.out.println("\nRound #" + roundNumber + " : ");
                        }
                    } else {
                        System.out.print(words.get(words.indexOf(word)+i) + " ");
                    }
                    i++;
                }
                System.out.println("\n");
            }
            if(word.equalsIgnoreCase("DraftPoolDiePosition:")){
                draftPoolDiceNumber = Integer.parseInt(words.get(words.indexOf(word)+1));
            }
            if(word.equalsIgnoreCase("ToolCardName:")){
                toolCardUsageName = words.get(words.indexOf(word)+1);
            }
        }
        inputManager = requestMessage.getInputManager();
        System.out.println(inputManager.toString());
        NotifyingThread.setPlayerIsActive(true);
        NotifyingThread.setPlayerBanned(false);
        inputThread = new NotifyingThread(inputManager, username, toolCardUsageName,
                draftPoolDiceNumber);
        inputThread.addListener(this);
        inputThread.start();
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        showPrivateObjectiveCard(showPrivateObjectiveCardsMessage.getPrivateObjectiveCardColor());
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        username = successCreatePlayerMessage.getRecipient();
        serverIsUp = true;
        System.out.println("Successful  login, new username: " + username);
        inputThread = new NotifyingThread(InputManager.INPUT_PLAYER_DISABLED, username);
        inputThread.addListener(this);
        inputThread.start();
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        /*should never be called*/
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        System.out.println("The winner is: " + sendWinnerMessage.getParticipants().get(sendWinnerMessage.getParticipants().size()-1)+
                " " + "with: " + sendWinnerMessage.getScore().get(sendWinnerMessage.getScore().size()-1));
        for(int i =0;i<sendWinnerMessage.getParticipants().size()-1;i++){
            System.out.println("Player: " +sendWinnerMessage.getParticipants().get(i)+
                    "\n"+ "score: " + sendWinnerMessage.getScore().get(i));
        }
        gameHasEnded = true;
    }

    @Override
    public void update(ChooseDiceMove chooseDiceMove) {
        /*should never be called*/
    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        /*should never be called*/
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        String[] myString =toolCardErrorMessage.getErrorInformation().split(" ");
        int draftPoolPosition = Integer.parseInt(myString[1]);
        NotifyingThread.setPlayerIsActive(true);
        NotifyingThread.setPlayerBanned(false);
        inputManager = toolCardErrorMessage.getInputManager();
        System.out.println("Wrong Input Parameters! Choose different values:");
        inputThread = new NotifyingThread(inputManager, username, toolCardErrorMessage.getToolCardID(),draftPoolPosition);
        inputThread.addListener(this);
        inputThread.start();
    }

    @Override
    public void notifyOfThreadComplete(Thread thread, Message message) {
        setChanged();
        if(message.getClass().equals(ErrorMessage.class) &&
                message.toString().equalsIgnoreCase("quit")){
            notifyObservers(message);
            playerWantsToContinue = false;
            gameHasEnded = true;
        } else {
            notifyObservers(message);
            playerWantsToContinue = true;
            playerIsBanned = false;
        }
    }

    public void askOldUsername(){
        inputManager = InputManager.INPUT_OLD_PLAYER_NAME;
        inputThread = new NotifyingThread(inputManager, username);
        inputThread.addListener(this);
        inputThread.start();
    }

    public boolean hasGameEnded() {
        return gameHasEnded;
    }

    public boolean isPlayerBanned() {
        return playerIsBanned;
    }

    public boolean playerWantsToContinue() {
        if(inputThread.isAlive()) {
            try {
                inputThread.join();
            } catch (InterruptedException e) {
                playerWantsToContinue = false;
            }
        }
        return playerWantsToContinue;
    }

    public boolean isServerUp() {
        return serverIsUp;
    }

    public void setServerIsUp(boolean serverIsUp) {
        this.serverIsUp = serverIsUp;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("View(").append(username).append(")");
        return builder.toString();
    }

}