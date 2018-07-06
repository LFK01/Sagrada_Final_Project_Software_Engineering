package it.polimi.se2018.model;

import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.model.game_equipment.*;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.utils.ProjectObservable;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static final int MAXIMUM_ROUND_NUMBER = 10;
    public static final int MAXIMUM_PLAYER_NUMBER = 4;
    public static final int MAXIMUM_DIE_NUMBER = 6;
    public static final int TOOL_CARDS_NUMBER = 12;
    public static final int PUBLIC_OBJECTIVE_CARDS_NUMBER = 10;
    public static final int PRIVATE_OBJECTIVE_CARDS_NUMBER = 5;
    public static final int SCHEMA_CARDS_NUMBER = 24;
    public static final int SCHEMA_CARDS_EXTRACT_NUMBER = 2;
    public static final int PUBLIC_OBJECTIVE_CARDS_EXTRACT_NUMBER = 3;
    public static final int TOOL_CARDS_EXTRACT_NUMBER = 3;
    public static final int SCHEMA_CARD_ROWS_NUMBER = 4;
    public static final int SCHEMA_CARD_COLUMNS_NUMBER = 5;
    public static final String FOLDER_ADDRESS_TOOL_CARDS = "src/main/resources/tool_card_resources";
    public static final String FOLDER_ADDRESS_SCHEMA_CARDS = "src/main/resources/schema_card_resources";
    public static final String OBJECTIVE_CARD_FILE_ADDRESS = "src/main/resources/ObjectiveCards.txt";
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
        FileParser parser = new FileParser();
        parser.writeTapWheelUsingValue(Model.FOLDER_ADDRESS_TOOL_CARDS, false);
        parser.writeLathekinPositions(Model.FOLDER_ADDRESS_TOOL_CARDS, -1, -1,
                -1, -1);
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
     * @param index player number, used to get the current turn player
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
     *@param draftPoolPos Position of the die in the draftPool
     * @param row Row where I want to insert the die
     * @param col Col where I want to insert the die
     */
    public void doDiceMove(int draftPoolPos,int row,int col){
        System.out.println("doing doDiceMove");
        try{
            placeDie(participants.get(turnOfTheRound).getSchemaCard(),draftPoolPos,row,col, false, false,false);
            removeDieFromDraftPool(draftPoolPos);
            System.out.println("removed Die from draftPool");
            if(isFirstDraftOfDice()) {
                participants.get(turnOfTheRound).getPlayerTurns()[roundNumber].getTurn1().getDieMove().setBeenUsed(true);
            }
            else {
                participants.get(turnOfTheRound).getPlayerTurns()[roundNumber].getTurn2().getDieMove().setBeenUsed(true);
            }
        }
        catch(FullCellException e){
            System.out.println("fullCellException caught");
            setChanged();
            notifyObservers(new ErrorMessage("model", participants.get(turnOfTheRound).getName(),"FullCellError"));
        }
        catch(RestrictionsNotRespectedException e){
            System.out.println("fullCellException caught");
            setChanged();
            notifyObservers(new ErrorMessage("model",participants.get(turnOfTheRound).getName(), "InvalidPositionError"));
        }
    }


    /** method that removes a draftpool die
     * @param draftPoolPos position of the die to be removed
     */
    public void removeDieFromDraftPool(int draftPoolPos) {
        gameBoard.getRoundDice()[roundNumber].removeDiceFromList(draftPoolPos);
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
            increaseTurnNumber();
        }
        else{
            /*second run of turns to choose a die*/
            decreaseTurnNumber();
        }
    }

    /**
     * Increases the parameter that represents the turn of the round from the first player to the last
     */
    private void increaseTurnNumber(){
        if(turnOfTheRound == participants.size()-1){
            turnOfTheRound = participants.size()-1;
            firstDraftOfDice = false;
        }
        else {
            turnOfTheRound++;
        }
    }

    /**
     * Decreases the parameter that represents the turn of the round from the last player to the first.
     * Arrived at the first player increases the parameter representing the rounds
     */
    private void decreaseTurnNumber(){
        turnOfTheRound--;
        if(turnOfTheRound<0){
            turnOfTheRound = 0;
            firstDraftOfDice = true;
            updateRound();
            if(roundNumber == MAXIMUM_ROUND_NUMBER-1){
                countPoints();
            }
        }
    }

    /**
     * method to add a new player in the arrayList representing the players in the game
     * @param name
     */
    public void addPlayer(String name){
        System.out.println("add player");
        participants.add(new Player(name));
        setChanged();
        notifyObservers(new SuccessCreatePlayerMessage("server",name));
    }

    /**
     * method to extract and set ToolCard
     */
    public void extractToolCards() {
       FileParser parser = new FileParser();
        ArrayList<Integer> cardIndex = new ArrayList<>(TOOL_CARDS_NUMBER);
        for(int i = 1; i <= TOOL_CARDS_NUMBER; i++){
            cardIndex.add(i);
        }
        Collections.shuffle(cardIndex);
        for(int i = 0; i < Model.TOOL_CARDS_EXTRACT_NUMBER; i++) {
            gameBoard.setToolCards(parser.createToolCard(Model.FOLDER_ADDRESS_TOOL_CARDS, cardIndex.get(i)), i);
        }
    }

    /**
     *method to extract and set PublicObjectiveCard
     */
    public void extractPublicObjectiveCards() {
        FileParser parser = new FileParser();
        ArrayList<Integer> cardIndex = new ArrayList<>();
        for(int i = 1; i <= PUBLIC_OBJECTIVE_CARDS_NUMBER; i++){
            cardIndex.add(i);
        }
        Collections.shuffle(cardIndex);
        for(int i = 0; i < PUBLIC_OBJECTIVE_CARDS_EXTRACT_NUMBER; i++) {
            gameBoard.setPublicObjectiveCards(parser.createObjectiveCard(OBJECTIVE_CARD_FILE_ADDRESS, false,
                    cardIndex.get(i)), i);
        }
    }

    /**
     * Extracts Dice from DiceBag and puts them on the RoundTrack
     */
    public void extractRoundTrack(){
        getGameBoard().getRoundTrack().getRoundDice()[roundNumber] = new RoundDice(participants.size(),getGameBoard().getDiceBag());
    }

    /**
     * method that extract and sends players the schemaCards to choose from
     */
    public void sendSchemaCard(){
        ArrayList<Integer> randomValues = new ArrayList<>();
        FileParser parser = new FileParser();
        HashMap<Player, String[]> playersExtractedSchemaCardsMap = new HashMap<>();
        int cardsExtractedIndex = 0;
        int actualSchemaCardNumber = SCHEMA_CARDS_NUMBER + parser.countExcessSchemaCards(FOLDER_ADDRESS_SCHEMA_CARDS);
        for(int i = 1; i<= actualSchemaCardNumber; i++){
            randomValues.add(i);
        }
        Collections.shuffle(randomValues);
        for(Player player: participants) {
            SchemaCard[] extractedSchemaCards = new SchemaCard[SCHEMA_CARDS_EXTRACT_NUMBER * 2];
            String[] schemaCards = new String[SCHEMA_CARDS_EXTRACT_NUMBER * 2];
            for (int i = 0; i < SCHEMA_CARDS_EXTRACT_NUMBER * 2; i++) {
                extractedSchemaCards[i] = parser
                        .createSchemaCardByNumber(FOLDER_ADDRESS_SCHEMA_CARDS, randomValues.get(cardsExtractedIndex));
                schemaCards[i] = extractedSchemaCards[i].toString();
                cardsExtractedIndex++;
            }
            playersExtractedSchemaCardsMap.put(player, schemaCards);
            setDefaultSchemaCard(player, extractedSchemaCards[0]);
        }
        participants.stream().filter(
                Player::isConnected
        ).forEach(
                player -> {
                    Thread sendingMessageThread = new Thread(this);
                    try{
                        Message sentMessage = new ChooseSchemaMessage("model", player.getName(),
                                playersExtractedSchemaCardsMap.get(player));
                        memorizeMessage(sentMessage);
                        sendingMessageThread.start();
                        sendingMessageThread.join();
                    } catch (InterruptedException e) {
                        Logger.getAnonymousLogger().log(Level.SEVERE, "{0}", e);
                    }
                }
        );
    }

    /**give each player the chosen card
     * @param playerPos  Position of the player in the ArrayList
     * @param schemaName Schemacard chosen by the player
     */
    public void setSchemaCardPlayer(int playerPos, String schemaName){
        FileParser parser = new FileParser();
        SchemaCard schema = parser.createSchemaCardByName(FOLDER_ADDRESS_SCHEMA_CARDS, schemaName);
        participants.get(playerPos).setSchemaCard(schema);
    }

    /**
     * Each player is set by default a schemaCard among those he can choose.
     * Expired the timer for the choice, if there was not the player starts the game with this schemeCard
     * @param player
     * @param schemaCard
     */
    private void setDefaultSchemaCard(Player player, SchemaCard schemaCard){
        player.setSchemaCard(schemaCard);
    }

    /**
     *method that extracts and sends each player his PrivateObjectivecard
     */
    public synchronized void sendPrivateObjectiveCard(){
        FileParser parser = new FileParser();
        ArrayList<Integer> cardIndex = new ArrayList<>(3);
        for(int i = 1; i <= PRIVATE_OBJECTIVE_CARDS_NUMBER; i++){
            cardIndex.add(i);
        }
        Collections.shuffle(cardIndex);
        participants.stream().forEach(
                p -> {
                    int playerIndex = participants.indexOf(p);
                    p.setPrivateObjectiveCard(parser.createObjectiveCard(OBJECTIVE_CARD_FILE_ADDRESS, true, cardIndex.get(playerIndex)));
                }
        );
        participants.stream().filter(
                Player::isConnected
        ).forEach(
                p -> {
                    setChanged();
                    notifyObservers(new ShowPrivateObjectiveCardsMessage("model", p.getName(),
                            p.getPrivateObjective().getDescription(),
                            participants.size()));
                }
        );
        sendSchemaCard();
    }


    /**
     * send the gameboard to all the players
     */
    public void updateGameboard(){
        participants.stream().filter(
                Player::isConnected
        ).forEach(
                p -> {
                    StringBuilder builderGameboard = buildMessage();
                    StringBuilder personalBuilder = new StringBuilder();
                    personalBuilder.append("FavorTokens:/" + p.getFavorTokens()).append("/");
                    personalBuilder.append("playingPlayer:/")
                            .append(participants.get(turnOfTheRound).getName()).append("/");
                    builderGameboard.append(personalBuilder.toString());
                    Thread sendingMessageThread = new Thread(this);
                    try{
                        memorizeMessage(new SendGameboardMessage("model", p.getName(),
                                builderGameboard.toString()));
                        sendingMessageThread.start();
                        sendingMessageThread.join();
                    } catch (InterruptedException e){
                        sendingMessageThread.interrupt();
                        Logger.getAnonymousLogger().log(Level.SEVERE, "{0}", e);
                    }
                }
        );
    }

    public void updateGameboardToolCard() {
        Thread sendingMessageThread;
        StringBuilder builderGameboard = buildMessage();
        for(Player player: participants){
            sendingMessageThread = new Thread(this);
            try{
                memorizeMessage(new SendGameboardMessage("model", player.getName(),
                        builderGameboard.toString()));
                sendingMessageThread.start();
                sendingMessageThread.join();
            } catch (InterruptedException e){
                sendingMessageThread.interrupt();
                Logger.getAnonymousLogger().log(Level.SEVERE, "{0}", e);
            }
        }
    }

    /**
     * Creates a String containing all the information of the gameboard to send to all players
     * @return A String with information
     */
    private StringBuilder buildMessage(){
        StringBuilder builderGameboard = new StringBuilder();
        builderGameboard.append("PublicObjectiveCards:/");
        for(int i=0; i<PUBLIC_OBJECTIVE_CARDS_EXTRACT_NUMBER; i++){
            builderGameboard.append("Name:/").append(gameBoard.getPublicObjectiveCardName(i)).append("/");
            builderGameboard.append("Description:/").append(gameBoard.getPublicObjectiveCardDescription(i)).append("/");
        }
        builderGameboard.append("ToolCards:/");
        for (int i=0; i<TOOL_CARDS_EXTRACT_NUMBER; i++){
            builderGameboard.append("Name:/").append(gameBoard.getToolCardName(i)).append("/");
            builderGameboard.append("ID:/").append(gameBoard.getToolCards()[i].getIdentificationName())
                    .append("/");
            builderGameboard.append("Description:/").append(gameBoard.getToolCardDescription(i)).append("/");
        }
        builderGameboard.append("SchemaCards:/");
        participants.stream().filter(
                player -> !isPlayerTurn(player) && player.isConnected()
        ).forEach(
                player -> {
                    builderGameboard.append(player.getName()).append("\n");
                    builderGameboard.append(player.getSchemaCard().toString()).append("/");
                }
        );
        participants.stream().filter(
                player -> isPlayerTurn(player)
        ).forEach(
                player -> {
                    builderGameboard.append(player.getName()).append("\n");
                    builderGameboard.append(player.getSchemaCard().toString()).append("/");
                }
        );
        builderGameboard.append("schemaStop:/");
        builderGameboard.append("DiceList:/");
        RoundDice currentRoundDice = gameBoard.getRoundDice()[roundNumber];
        List<Dice> currentDiceList = currentRoundDice.getDiceList();
        currentDiceList.forEach(
                die -> builderGameboard.append(die.toString()).append("/")
        );
        builderGameboard.append("DiceStop/");
        return builderGameboard;
    }

    /**
     * update the Round
     */
    public void updateRound(){
        roundNumber++;
        changeFirstPlayer();
        extractRoundTrack();
    }

    /**
     * change the order of the players ArrayList
     */
    public void changeFirstPlayer(){
        Player lastPlayer = participants.remove(0);
        participants.add(lastPlayer);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Method that counts points of all players and sorts them according to the winner
     * The calculation is done by activating the card account method
     * (this method automatically calculates the scores for each player).
     * Then checks are made to see if there are players with the same score.
     */
    public void countPoints(){
        ArrayList<Player> participantsCopy = new ArrayList<>();
        participantsCopy.addAll(participants);
        ArrayList<Player> deadHeat = null;
        Arrays.asList(gameBoard.getPublicObjectiveCards()).forEach(
                objectiveCard -> objectiveCard.countPoints(this, objectiveCard.getName(), objectiveCard.getPoints())
        );
        participants.stream().forEach(
                p -> p.getPrivateObjective()
                        .countPoints(this, p.getPrivateObjective().getName(), p.getPrivateObjective().getPoints())
        );
        Collections.sort(participants, Comparator.comparingInt( p -> p.getPoints()));
        deadHeat = searchIfEqualsValuePublic(participants.size()-1,participants);
        if(deadHeat.size()==1){
            sendWinner(participants);
        }
        resetPoint(deadHeat);
        for(int j=0;j<deadHeat.size();j++){
            deadHeat.get(j).getPrivateObjective().countPoints(this,deadHeat.get(j).getPrivateObjective().getName(),deadHeat.get(j).getPrivateObjective().getPoints());
        }
        Collections.sort(deadHeat, Comparator.comparingInt( p -> p.getPoints()));
        deadHeat = searchIfEqualsValuePublic(deadHeat.size()-1,deadHeat);
        if(deadHeat.size()==1){     //aggiorno la posizione
            participants.remove(deadHeat.get(0));
            participants.add(deadHeat.get(0));
            sendWinner(participants);
        }
        System.out.println("STO CONTROLLANDO I FAVOR TOKENS");
        Collections.sort(deadHeat,Comparator.comparingInt(p->p.getFavorTokens()));
        ArrayList<Player> moreTokens = new ArrayList<>();
        for(int s =0;s<deadHeat.size()-1;s++){
            if(deadHeat.get(s).getFavorTokens()==deadHeat.get(deadHeat.size()-1).getFavorTokens()){
                moreTokens.add(deadHeat.get(s));
            }
        }
        moreTokens.add(deadHeat.get(deadHeat.size()-1));

        if(moreTokens.size()==1){
            participants.remove(moreTokens.get(0));
            participants.add(moreTokens.get(0));
            sendWinner(participants);
        }
        else {
            System.out.println("STO ANDANDO DOVE NON DOVREI");
            //Collections.sort(moreTokens,Comparator.comparingInt(i->participants.indexOf(i);
            //ordine in base a idexOf
            boolean winnerFound = false;
            Player winner =null;
            for(int i =0;i<participantsCopy.size() && !winnerFound;i++){
                for(int j =0;j<moreTokens.size() && !winnerFound;j++){
                    if(participantsCopy.get(i).getName().equalsIgnoreCase(moreTokens.get(j).getName())){
                        winner = moreTokens.get(j);
                        winnerFound = true;
                    }
                }
            }
            participantsCopy.remove(winner);
            participantsCopy.add(winner);
            sendWinner(participantsCopy);
        }
    }

    /**
     * sets the score field for each player to 0 and return the arrayList
     * @param deadHeat
     */
    public void resetPoint(ArrayList<Player> deadHeat ){
        for(int i =0;i<deadHeat.size();i++){
            deadHeat.get(i).setPointTo0();
        }
    }

    /**
     * search for players with the same score as the current winner
     * @param arrayListDimension dimension of the ArrayList
     * @param precDeadHeat ArrayList used in the method
     * @return Changed arrayList
     */
    public ArrayList<Player> searchIfEqualsValuePublic(int arrayListDimension,ArrayList<Player> precDeadHeat){
        ArrayList<Player> deadHeat = new ArrayList<Player>();
        for(int i=0;i<arrayListDimension;i++){
            if (precDeadHeat.get(i).getPoints()==precDeadHeat.get(precDeadHeat.size()-1).getPoints()){
                deadHeat.add(precDeadHeat.get(i));
            }
        }
        deadHeat.add(precDeadHeat.get(precDeadHeat.size()-1));
        return deadHeat;
    }

    /**
     *
     * @param participants  ArrayList of players with the winner in last place
     */
    public void sendWinner(ArrayList<Player> participants){
        ArrayList<String> ranking = new ArrayList<>();
        ArrayList<Integer> score = new ArrayList<>();
        for(int j=0;j<participants.size();j++){
            ranking.add(participants.get(j).getName());
            score.add(participants.get(j).getPoints());

        }
        for(int i=0; i<participants.size();++i){
            setChanged();
            notifyObservers(new SendWinnerMessage("model",participants.get(i).getName(), ranking, score));
        }

    }

    /**
     *gives victory to the last player left
     * in case of disconnection of all the other players
     * @param player player who won
     * @param matchStarted parameter to understand if the game had started or not
     */
    public void singlePlayerWinning(Player player, boolean matchStarted){
        if(matchStarted) {
            Arrays.asList(gameBoard.getPublicObjectiveCards()).forEach(
                    objectiveCard -> objectiveCard.countPoints(this, objectiveCard.getName(), objectiveCard.getPoints())
            );
            player.getPrivateObjective().countPoints(this, player.getName(), player.getPoints());
            setChanged();
            ArrayList<String> singlePlayerWinnerName = new ArrayList<>();
            singlePlayerWinnerName.add(player.getName());
            ArrayList<Integer> singlePlayerWinningPoints = new ArrayList<>();
            singlePlayerWinningPoints.add(player.getPoints());
            notifyObservers(new SendWinnerMessage("server", player.getName(),
                    singlePlayerWinnerName, singlePlayerWinningPoints));
        } else {
            ArrayList<String> singlePlayerWinnerName = new ArrayList<>();
            singlePlayerWinnerName.add(player.getName());
            ArrayList<Integer> singlePlayerWinningPoints = new ArrayList<>();
            singlePlayerWinningPoints.add(0);
            notifyObservers(new SendWinnerMessage("server", player.getName(),
                    singlePlayerWinnerName, singlePlayerWinningPoints));
        }
    }

    @Override
    public void run() {
        setChanged();
        notifyObservers();
    }
}
