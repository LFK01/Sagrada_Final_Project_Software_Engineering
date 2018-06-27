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
 */
public class View extends ProjectObservable implements ProjectObserver, ThreadCompleteListener{

    private boolean stillPlaying;

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
        stillPlaying = true;
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
    public void update(Message message) {
        System.out.println(message.toString());
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        for(int i=0;i<4;i++) {
            System.out.println("type " + (i+1) + " to choose this schema");
            System.out.println(chooseSchemaMessage.getSchemaCards(i));
            schemaName[i]= chooseSchemaMessage.getSchemaCards(i).split("\n")[0];
        }
        inputManager = InputManager.INPUT_SCHEMA_CARD;
        if(inputThread.isAlive()){
            inputThread.interrupt();
        }
        inputThread = new NotifyingThread(inputManager, username, schemaName);
        inputThread.addListener(this);
        inputThread.start();
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
            if(inputThread.isAlive()){
                inputThread.interrupt();
            }
            inputThread = new NotifyingThread(inputManager, username);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equalsIgnoreCase("UsernameNotFound")){
            System.out.println("Username not found");
        }
        if(errorMessage.toString().equalsIgnoreCase("NotEnoughFavorTokens")){
            System.out.println("You need more tokens to activate this card!");
            inputManager = InputManager.INPUT_CHOOSE_MOVE;
            if(inputThread.isAlive()){
                inputThread.interrupt();
            }
            inputThread = new NotifyingThread(inputManager, username);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equals("La posizione &eacute; gi&aacute; occupata")){
            System.out.println("Posizione già occupata");
            inputManager =InputManager.INPUT_CHOOSE_MOVE;
            if(inputThread.isAlive()){
                inputThread.interrupt();
            }
            inputThread = new NotifyingThread(inputManager, username);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equals("La posizione del dado non &eacute; valida")){
            System.out.println("Posizione non valida");
            inputManager =InputManager.INPUT_CHOOSE_MOVE;
            if(inputThread.isAlive()){
                inputThread.interrupt();
            }
            inputThread = new NotifyingThread(inputManager, username);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equals("DiceMoveAlreadyUsed")){
            System.out.println("You have already used all your moves in this round");
            inputManager = InputManager.INPUT_CHOOSE_MOVE;
            if(inputThread.isAlive()){
                inputThread.interrupt();
            }
            inputThread = new NotifyingThread(inputManager, username);
            inputThread.addListener(this);
            inputThread.start();
        }
        if(errorMessage.toString().equalsIgnoreCase("TimeElapsed")){
            inputManager = InputManager.INPUT_NEW_CONNECTION;
            if(inputThread.isAlive()){
                inputThread.interrupt();
            }
            inputThread = new NotifyingThread(inputManager, username);
            inputThread.addListener(this);
            inputThread.start();
        }
    }


    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        System.out.println("Private Objective Card: " + privateObjectiveCardDescription + "\n");
        String playingPlayer;
        boolean alreadyRead = false;
        System.out.println(sendGameboardMessage.getGameboardInformation());
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
                    System.out.println("loop");
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
                if(playingPlayer.equals(username)){
                    inputManager = InputManager.INPUT_CHOOSE_MOVE;
                    if(inputThread.isAlive()){
                        inputThread.interrupt();
                    }
                    inputThread = new NotifyingThread(inputManager, username, toolCardIDs);
                    inputThread.addListener(this);
                    inputThread.start();
                }else{
                    System.out.println("It's not your turn!");
                }
            }
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
        int draftPoolDiceNumber = -1;
        String toolCardUsageName = "";
        for(String name: toolCardNames){
            System.out.println(name);
        }
        ArrayList<String> words = new ArrayList<>(Arrays.asList(requestMessage.getValues().split(" ")));
        for(String word: words){
            if(word.equalsIgnoreCase("DraftPoolDiePosition:")){
                draftPoolDiceNumber = Integer.parseInt(words.get(words.indexOf(word)+1));
            }
            if(word.equalsIgnoreCase("ToolCardName:")){
                toolCardUsageName = words.get(words.indexOf(word)+1).replace("/", " ");
            }
        }
        System.out.println("requestMessage values: " + requestMessage.getValues());
        inputManager = requestMessage.getInputManager();
        System.out.println(inputManager.toString());
        if(inputThread.isAlive()){
            inputThread.interrupt();
        }
        inputThread = new NotifyingThread(inputManager, username, toolCardUsageName, draftPoolDiceNumber);
        inputThread.addListener(this);
        inputThread.start();
    }

    @Override
    public void update(SelectedSchemaMessage selectedSchemaMessage) {

    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        showPrivateObjectiveCard(showPrivateObjectiveCardsMessage.getPrivateObjectiveCardColor());
    }

    @Override
    public void update(SuccessMessage successMessage) {
        System.out.println(successMessage.getSuccessMessage());
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        username = successCreatePlayerMessage.getRecipient();
        System.out.println("Successful  login, new username: " + username);
    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {

    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {

    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        System.out.println("The Winner is: " + sendWinnerMessage.getWinnerName());
        System.out.println("with " + sendWinnerMessage.getWinnerScore()+ " points");
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
        System.out.println("Wrong Input Parameters! Choose different values:");
        if(inputThread.isAlive()){
            inputThread.interrupt();
        }
        inputThread = new NotifyingThread(inputManager, username, toolCardErrorMessage.getToolCardID());
        inputThread.addListener(this);
        inputThread.start();
    }

    @Override
    public void notifyOfThreadComplete(Thread thread, Message message) {
        setChanged();
        if(!message.getSender().equalsIgnoreCase("quit")){
            notifyObservers(message);
        } else {
            stillPlaying = false;
        }
    }

    @Override
    public void quit(){
        stillPlaying = false;
    }

    public boolean isStillPlaying() {
        return stillPlaying;
    }
}