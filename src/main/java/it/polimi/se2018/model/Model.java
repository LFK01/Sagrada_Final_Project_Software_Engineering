package it.polimi.se2018.model;

import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.game_equipment.*;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;
import it.polimi.se2018.model.objective_cards.private_objective_cards.*;
import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.objective_cards.public_objective_cards.*;
import it.polimi.se2018.utils.ProjectObservable;

import java.util.*;

/**
 * This class is supposed to contain all the data about a game and all the
 * controls to check if any move is correct. The data is accessible through
 * getter methods.<s Whenever a modifier method is activated by an external class
 * Model verifies that the move can be made and notifies its observer with
 * a Message
 *
 * @author Giovanni
 * edited  Luciano 12/05/2018;
 * edited Luciano 14/05/2018;
 */

public class Model extends ProjectObservable implements Runnable{

    private int roundNumber;
    public static final int SCHEMA_CARDS_EXTRACT_NUMBER = 2;
    public static final int PUBLIC_OBJECTIVE_CARDS_EXTRACT_NUMBER = 3;
    public static final int TOOL_CARDS_EXTRACT_NUMBER = 3;
    public static final int TOOL_CARD_NUMBER = 12;
    public static final int SCHEMA_CARD_NUMBER = 24;
    private GameBoard gameBoard;
    /*local instance of the gameBoard used to access all objects and
     * methods of the game instrumentation*/
    private ArrayList<Player> participants;
    /*local ArrayList to memorize actual playing players*/
    private int turnOfTheRound;
    /*local variable to memorize the current turn in a round, it goes from 0
     * to participants.size()-1*/
    private boolean firstDraftOfDice;
    /*local variable to memorize if every player has been given the option to choose
        his/her first die*/


    /**
     * Constructor method initializing turnOfTheRound and the participant list
     */
    public Model() {
        this.gameBoard = new GameBoard();
        turnOfTheRound = 0;
        firstDraftOfDice = true;
        participants = new ArrayList<>();
        this.roundNumber =0;
    }

    /**
     * Getter for integer value of turnOfTheRound of the Round
     * @return integer value turnOfTheRound of the Round
     */
    public int getTurnOfTheRound() {
        return turnOfTheRound;
    }

    /**
     * Getter method to access all the gaming components
     * @return gameBoard reference
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Getter method for the participants number
     * @return integer value of the participants number
     */
    public int getParticipantsNumber() {
        return participants.size();
    }

    /**
     * Getter method for a specific player
     * @param index
     * @return reference of the player specified in the index parameter
     */
    public Player getPlayer(int index) {
        return participants.get(index);
    }

    public ArrayList<Player> getParticipants() {
        return participants;
    }

    public boolean isFirstDraftOfDice() {
        return firstDraftOfDice;
    }

    /**
     * method to check if a player can place a die in a position on his/her schema card
     *
     */
    public void doDiceMove(int draftPoolPos,int row,int col){
        try{
            placeDie(participants.get(turnOfTheRound).getSchemaCard(),draftPoolPos,row,col, false, false,false);
            removeDieFromDraftPool(draftPoolPos);
            if(isFirstDraftOfDice()) {
                participants.get(turnOfTheRound).getPlayerTurns()[roundNumber].getTurn1().getDieMove().setBeenUsed(true);
            }
            else {
                participants.get(turnOfTheRound).getPlayerTurns()[roundNumber].getTurn2().getDieMove().setBeenUsed(true);
            }
            updateGameboard();
        }
        catch(FullCellException e){
            setChanged();
            notifyObservers(new ErrorMessage("model",participants.get(turnOfTheRound).getName(),"La posizione &eacute; gi&aacute; occupata"));
        }
        catch(RestrictionsNotRespectedException e){
            setChanged();
            notifyObservers(new ErrorMessage("model",participants.get(turnOfTheRound).getName(), "La posizione del dado non &eacute; valida"));
        }
    }

    private void removeDieFromDraftPool(int draftPoolPos) {
        gameBoard.getRoundDice()[roundNumber].removeDiceFromDraftPool(draftPoolPos);
    }

    /**
     * method to check if a player can skip a move
     * @param move
     */
    public void checkNoActionMove(NoActionMove move) {

    }

    /**
     * method to check if the turnOfTheRound of the player specified in the parameter field
     * @param player
     * @return true if is the turnOfTheRound of the current player
     */
    public boolean isPlayerTurn(Player player) {
        return participants.indexOf(player) == turnOfTheRound;
    }

    public void placeDie(SchemaCard schemaCard, int drafPoolPos, int row, int col,
                        boolean avoidColorRestrictions, boolean avoidValueRestrictions,
                        boolean avoidNearnessRestrictions) throws RestrictionsNotRespectedException,
                        FullCellException{
        Dice chosenDie = gameBoard.getRoundDice()[roundNumber].getDice(drafPoolPos);
        schemaCard.placeDie(chosenDie, row, col, avoidColorRestrictions, avoidValueRestrictions, avoidNearnessRestrictions);
    }

    /**
     *
     * method to notifyView the current player turnOfTheRound in a round
     * once the first run is completed, the method proceeds to count backwards
     */
    public void updateTurnOfTheRound(){
        if(isFirstDraftOfDice()){
            /*first run of turns to choose a die*/
            if(turnOfTheRound == participants.size()-1){
                turnOfTheRound = participants.size()-1;
                firstDraftOfDice = false;
            }
            else {
                turnOfTheRound++;
            }
        }
        else{
            /*second run of turns to choose a die*/
                turnOfTheRound--;
        }
    }

    /**
     * method to add a new player
     * @param name
     */
    public void addPlayer(String name){
        participants.add(new Player(name));
        setChanged();
        notifyObservers(new SuccessCreatePlayerMessage("server",name));
    }

    /**
     * Method to remove a player from the model
     * @param username String to retrieve player name
     *
     */
    public void removePlayer(String username){

    }

    /**
     * method to modify the token numeber of a specific player, gets called after a tool card has been used
     * @param toolCardIndex number of tokens to deduct
     * @param playerPosition integer number to get the player reference
     */
    public  void  updateFavorTokens(int toolCardIndex, int playerPosition){
        if(gameBoard.getToolCards()[toolCardIndex].isFirstUsage()){
            participants.get(playerPosition).decreaseFavorTokens(false);
        }
        else {
            participants.get(playerPosition).decreaseFavorTokens(true);
        }
        setChanged();
        notifyObservers();
    }

    public void extractToolCards() {
        ArrayList<Integer> cardIndex = new ArrayList<>(TOOL_CARD_NUMBER);
        for(int i = 1; i <= TOOL_CARD_NUMBER; i++){
            System.out.println("added: " + i);
            cardIndex.add(i);
        }
        Collections.shuffle(cardIndex);
        for(int i = 0; i < 3; i++) {
            System.out.println("extracted: " + cardIndex.get(i));
            gameBoard.setToolCards(new ToolCard(cardIndex.get(i)), i);
        }
    }

    public void extractPublicObjectiveCards() {
        ArrayList<Integer> cardIndex = new ArrayList<>(3);
        for(int i = 1; i <= 3; i++){
            System.out.println("added: " + i);
            cardIndex.add(i);
        }
        Collections.shuffle(cardIndex);
        for(int i = 0; i < 3; i++) {
            System.out.println("extracted: " + cardIndex.get(i));
            gameBoard.setPublicObjectiveCards(new ObjectiveCard(false,cardIndex.get(i)), i);
        }
    }

    /**
     * Extracts Dice from DiceBag and puts them on the RoundTrack
     */
    public void extractRoundTrack(){
        getGameBoard().getRoundTrack().getRoundDice()[roundNumber] = new RoundDice(participants.size(),getGameBoard().getDiceBag(),turnOfTheRound);
    }

    public void sendSchemaCard(){
        System.out.println("Dealing SchemaCards to players ");
        Thread sendingMessageThread;
        int extractedCardIndex = 0;
        ArrayList<Integer> randomValues = new ArrayList<>();
        for(int i=0; i<participants.size(); i++){
            System.out.println("Participant: " + participants.get(i).getName());
        }
        for(int i = 1; i<= SCHEMA_CARD_NUMBER/2; i++){
            randomValues.add(i);
        }
        Collections.shuffle(randomValues);
        for(int t=0; t < participants.size(); t++) {
            SchemaCard[] extractedSchemaCards = new SchemaCard[SCHEMA_CARDS_EXTRACT_NUMBER *2];
            String[] schemaCards = new String[SCHEMA_CARDS_EXTRACT_NUMBER *2];
            for(int i = 0; i< SCHEMA_CARDS_EXTRACT_NUMBER*2; i+=2){
                extractedSchemaCards[i] = new SchemaCard(randomValues.get(extractedCardIndex));
                schemaCards[i] = extractedSchemaCards[i].toString();
                extractedSchemaCards[i+1] = new SchemaCard((SCHEMA_CARD_NUMBER+1)-randomValues.get(extractedCardIndex));
                schemaCards[i+1] = extractedSchemaCards[i+1].toString();
                extractedCardIndex++;
            }
            try{
                Message sentMessage = new ChooseSchemaMessage("model", participants.get(t).getName(), schemaCards);
                memorizeMessage(sentMessage);
                sendingMessageThread = new Thread(this);
                sendingMessageThread.start();
                sendingMessageThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSchemaCardPlayer(int playerPos, String schemaName){
        SchemaCard schema = new SchemaCard(schemaName);
        participants.get(playerPos).setSchemaCard(schema);
    }

    public void sendPrivateObjectiveCard(){
        ArrayList<Integer> cardIndex = new ArrayList<>(3);
        ArrayList<ObjectiveCard> privateObjectiveCard =new ArrayList<>();
        for(int i = 1; i < participants.size(); i++){
            System.out.println("added: " + i);
            cardIndex.add(i);
        }
        Collections.shuffle(cardIndex);
        for(int i = 0; i < participants.size(); i++) {
            System.out.println("extracted: " + cardIndex.get(i));
            privateObjectiveCard.add(new ObjectiveCard(true,cardIndex.get(i)));
        }
        int s =0;
        for(int i=0; i < participants.size(); i++) {
            String colorString = privateObjectiveCard.get(i).getDescription();
            setChanged();
            notifyObservers(new ShowPrivateObjectiveCardsMessage("model", participants.get(i).getName(), colorString,participants.size()));
            s = s+1;
        }
        sendSchemaCard();
    }

    public void updateGameboard(){   //momentaneamente facoltativo
        Thread sendingMessageThread;
        StringBuilder builderGameboard = new StringBuilder();
        builderGameboard.append("PublicObjectiveCards:/");
        for(int i=0; i<PUBLIC_OBJECTIVE_CARDS_EXTRACT_NUMBER; i++){
            builderGameboard.append("Name:/").append(gameBoard.getPublicObjectiveCardName(i)).append("/");
            builderGameboard.append("Description:/").append(gameBoard.getPublicObjectiveCardDescription(i)).append("/");
        }
        builderGameboard.append("ToolCards:/");
        for (int i=0; i<TOOL_CARDS_EXTRACT_NUMBER; i++){
            builderGameboard.append("Name:/").append(gameBoard.getToolCardName(i)).append("/");
            builderGameboard.append("Description:/").append(gameBoard.getToolCardDescription(i)).append("/");
        }
        builderGameboard.append("DiceList:/");
        RoundDice currentRoundDice = gameBoard.getRoundDice()[roundNumber];
        List<Dice> currentDiceList = currentRoundDice.getDiceList();
        for(int i=0; i<currentDiceList.size(); i++){
            builderGameboard.append(currentDiceList.get(i).toString()).append("/");
        }
        builderGameboard.append("/");
        builderGameboard.append("SchemaCard:/");
        for (int i =participants.size()-1; i>=0;i--){
            builderGameboard.append(participants.get(i).getName()).append("\n");
            builderGameboard.append(participants.get(i).getSchemaCard().toString()).append("/");
        }
        builderGameboard.append("schemaStop:/");
        builderGameboard.append("playingPlayer:/");
        builderGameboard.append(participants.get(turnOfTheRound).getName());
        for(int i = 0; i<participants.size(); i++){
            try{
                memorizeMessage(new GameInitializationMessage("model", participants.get(i).getName(),builderGameboard.toString()));
                sendingMessageThread = new Thread(this);
                sendingMessageThread.start();
                sendingMessageThread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void updateRound(){
        roundNumber = roundNumber +1;
        resetTurnOfTheRound();
        resetFirstDieOfDraft();
        changeFirstPlayer();
        extractRoundTrack();
    }
    public void changeFirstPlayer(){
        Player lastPlayer = participants.remove(0);
        participants.add(lastPlayer);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void resetTurnOfTheRound() {
        this.turnOfTheRound = 0;
    }
    public void resetFirstDieOfDraft(){
        firstDraftOfDice =true;
    }


    @Override
    public void run() {
        setChanged();
        notifyObservers();
    }
}
