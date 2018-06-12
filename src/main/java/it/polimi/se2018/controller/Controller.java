package it.polimi.se2018.controller;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.DiceBag;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.game_equipment.RoundDice;
import it.polimi.se2018.model.objective_cards.private_objective_cards.*;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;

import java.util.*;

/**
 * Controller class
 * @author Luciano, Giovanni
 */

public class Controller extends ProjectObservable implements ProjectObserver {

    private Model model;
    private int time;
    private boolean timerStarted;
    private Timer t;
    private boolean enoughPlayers;
    private int playerNumberDoneSelecting = 0;
    private int allPlayersReady =0;

    /**
     * Class constructor
     */
    public Controller() {
        this.model = new Model();
        timerStarted = false;
        t = new Timer();
        enoughPlayers = false;
    }

    /**
     * Method that randomly extracts game cards
     */
    private void cardsExtraction() {
        model.extractToolCards();
        model.extractPublicObjectiveCards();
    }

    /**
     * Method that randomly extracts and deals one private objective card per player
     */
    private void dealPrivateObjectiveCards() {
        ArrayList<Integer> cardIndex = new ArrayList<>(12);
        for(int i = 1; i <= 5; i++){
            cardIndex.add(i);
        }
        Collections.shuffle(cardIndex);
        for(int i = 0; i < model.getParticipantsNumber(); i++) {
            switch(cardIndex.get(i)) {
                case 1: {
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureRosse.getThisInstance());
                    break;
                }
                case 2: {
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureGialle.getThisInstance());
                    break;
                }
                case 3: {
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureVerdi.getThisInstance());
                    break;
                }
                case 4: {
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureBlu.getThisInstance());
                    break;
                }
                case 5: {
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureViola.getThisInstance());
                    break;
                }
            }
        }
    }

    /**
     * Method that randomly extracts 3 public objective cards
     */
    /**
     * Method to roll one single dice, assigning a random int value
     * @param dice dice chosen by the player
     * @throws NullPointerException in case the dice parameter is null
     */
    private void rollSingleDice(Dice dice) {
        dice.setValue((int)Math.ceil(Math.random()*6));
    }

    /**
     * Method to throw the dice at the beginning of a round. It creates a new RoundDice instance for every round and associates it
     * to its position in the RoundTrack array (RoundTrack[round]).
     * @param diceBag the match diceBag already shuffled
     * @param round round number
     * @param participants number of players
     * @throws NullPointerException in case diceBag is null
     */
    private void rollRoundDice(DiceBag diceBag, int round, int participants) {
        RoundDice roundDice = new RoundDice(participants, diceBag, round);
        model.getGameBoard().getRoundTrack().setRoundDice(roundDice, round);
    }

    /**
     * Method that validates the input when the player chooses a dice to place it on his schema and calls the model methods.
     * @param move the object representing the player move
     * @throws InvalidCellPositionException in case the player gave an invalid input while choosing the cell
     * @throws InvalidDraftPoolPosException in case the player gave an invalid input while choosing the dice
     */
    private void performDiceMove(ChooseDiceMove move) throws InvalidCellPositionException, InvalidDraftPoolPosException {
        if (((ChooseDiceMove) move).getRow() < 0 ||
                ((ChooseDiceMove) move).getRow() > 3 ||
                ((ChooseDiceMove) move).getCol() < 0 ||
                ((ChooseDiceMove) move).getCol() > 4) {
            throw new InvalidCellPositionException();
        }
        if (((ChooseDiceMove) move).getDraftPoolPos() < 0) {
            throw new InvalidDraftPoolPosException();
        }
        //model.doDiceMove(move);
        //gestione di altre eccezioni relative al caso
        //scelta e piazzamento dado
        /*Ci sarà una chiamata del tipo model.performDiceMove((ChooseDiceMove) move), e all'interno di questa verranno effettuati sia i controlli
        per verificare che la mossa sia lecita sia l'esecuzione stessa della mossa.
        model.doDiceMove((ChooseDiceMove) move);
        move.getPlayer().getSchemaCard().getCell(((ChooseDiceMove) move).getRow(), ((ChooseDiceMove) move).getCol()).setAssignedDice(model.getGameBoard().getRoundDice()[model.getTurnOfTheRound()].getDice(((ChooseDiceMove) move).getDraftPoolPos()));
        sistemata, eventualmente da rivedere per semplificare la riga di codice e renderla più leggibile*/
    }

    /**
     * Method that validates the input when the player chooses a tool card
     * @param move the object representing the move
     * @throws NullPointerException in case the tool card is null
     */
    private void performToolCardMove(UseToolCardMove move){
        //attivazione tool card
        //Idem di sopra, ci sarà una chiamata del tipo model.performToolCardMove((UseToolCardMove) move).
        //((UseToolCardMove) move).getToolCard().activateCard(move.getPlayer());
    }


        /*if (message instanceof PlayerMove) {
            if (((PlayerMove) message).isDiceMove()) {
                try {
                    performDiceMove((ChooseDiceMove) message);
                } catch (InvalidCellPositionException e) {
                    notifyObservers(/*Insert errore message here);
                } catch (InvalidDraftPoolPosException e) {
                    notifyObservers(/*Insert errore message here);
                }
            } else {
                if (!((PlayerMove) message).isDiceMove()) {
                    if (((PlayerMove) message).isNoActionMove()) {
                        model.updateTurnOfTheRound();
                    } else {
                        try {
                            performToolCardMove((UseToolCardMove) message);
                        } catch (NullPointerException e) {
                            setChanged();
                            notifyObservers(inserire messaggio di errore);
                        }
                    }
                }
            }
    }*/

    @Override
    public void update(Message message) {
        System.out.println("calls the wrong method");
    }


    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(toolCard.getName().equals(toolCardActivationMessage.getToolCardName())){
                for (Player player: model.getParticipants()) {
                    if(player.getName().equals(toolCardActivationMessage.getSender())){
                        if(toolCard.isFirstUsage()){
                            if(player.getFavorTokens()>=1){
                                String values = toolCardActivationMessage.getValues();
                                toolCard.activateToolCard(toolCardActivationMessage.getSender(), toolCardActivationMessage.getToolCardName(), values, model);
                            } else {
                                setChanged();
                                notifyObservers(new ErrorMessage("server", player.getName(), "NotEnoughFavorTokens"));
                            }
                        } else {
                            if(player.getFavorTokens()>=2){

                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {

    }



    @Override
    public void update(RequestMessage requestMessage) {

    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(useToolCardMove.getToolCardName().equals(toolCard.getName())){
                if(toolCard.isFirstUsage()){
                    for(Player player: model.getParticipants()){
                        if(player.getName().equals(useToolCardMove.getSender())){
                            if(player.getFavorTokens()>=1){
                                player.decreaseFavorTokens(false);
                                setChanged();
                                String name = useToolCardMove.getToolCardName();
                                notifyObservers(new RequestMessage("server", useToolCardMove.getSender(), name, toolCard.getInputManager(name)));
                            }
                            else{
                                setChanged();
                                notifyObservers(new ErrorMessage("server", player.getName(), "NotEnoughFavorTokens"));
                            }
                        }
                    }
                } else {
                    for(Player player: model.getParticipants()){
                        if(player.getName().equals(useToolCardMove.getSender())){
                            if(player.getFavorTokens()>=2){
                                player.decreaseFavorTokens(true);
                                setChanged();
                                String name = useToolCardMove.getToolCardName();
                                notifyObservers(new RequestMessage("server", useToolCardMove.getSender(), name, toolCard.getInputManager(name)));
                            }
                            else{
                                setChanged();
                                notifyObservers(new ErrorMessage("server", player.getName(), "NotEnoughFavorTokens"));
                            }
                        }
                    }
                }
            }
        }
    }

    public void update(CreatePlayerMessage message){
        if(!timerStarted){
            timerStarted = true;
            System.out.println("timer inizializzato");
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    timerStarted = false;
                    if(!enoughPlayers) {
                        if(model.getParticipants().size()>=2) {
                            /*enough player to start a match*/
                            System.out.println("Time's up, match starting with " + model.getParticipants().size() + " players");
                            model.sendPrivateObjectiveCard();
                        }
                        else {
                            /*Not enough player*/
                            System.out.println("Time's up, minimun player number not reached!");
                            for(Player player: model.getParticipants()){
                                setChanged();
                                notifyObservers(new ErrorMessage("model", player.getName(),"NotEnoughPlayer"));
                            }
                        }
                    }
                }
            }, 1000*time);
        }
        model.addPlayer(message.getPlayerName());
        if(model.getParticipants().size()==4){
            System.out.println("Maximum player number reached");
            enoughPlayers = true;
            model.sendPrivateObjectiveCard();
        }
    }

    @Override
    public void update(ErrorMessage errorMessage) {

    }

    @Override
    public void update(GameInitializationMessage gameInitializationMessage) {

    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {

    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {

    }

    public void update(ComebackSocketMessage message){
        setChanged();
        notifyObservers(new SuccessCreatePlayerMessage("server", message.getSender()));
    }

    public void update(SelectedSchemaMessage message) {
        for (int playerPos = 0; playerPos < model.getParticipants().size(); playerPos++) {
            if (model.getParticipants().get(playerPos).getName().equals(message.getSender())) {
                model.setSchemaCardPlayer(playerPos, message.getSchemaCardName());
                playerNumberDoneSelecting++;
            }
        }
        /*  extracts public objective cards
            extracts tool cards
            sends initialization message
        */
        System.out.println("Number of players that has selected a schemaCard: " + playerNumberDoneSelecting);
        System.out.println("Number of participants in the match: " + model.getParticipantsNumber());
        if(playerNumberDoneSelecting == model.getParticipantsNumber()){
            model.extractPublicObjectiveCards();
            model.extractToolCards();
            model.extractRoundTrack();
            model.updateGameboard();
        }
    }
    public void update(ChooseDiceMove message) {
        System.out.println(message.getDraftPoolPos() + " " + message.getRow() + " " + message.getCol());
        model.doDiceMove(message);
        //gestione di altre eccezioni relative al caso
        //scelta e piazzamento dado
        /*Ci sarà una chiamata del tipo model.performDiceMove((ChooseDiceMove) move), e all'interno di questa verranno effettuati sia i controlli
        per verificare che la mossa sia lecita sia l'esecuzione stessa della mossa.
        model.doDiceMove((ChooseDiceMove) move);
        move.getPlayer().getSchemaCard().getCell(((ChooseDiceMove) move).getRow(), ((ChooseDiceMove) move).getCol()).setAssignedDice(model.getGameBoard().getRoundDice()[model.getTurnOfTheRound()].getDice(((ChooseDiceMove) move).getDraftPoolPos()));
        sistemata, eventualmente da rivedere per semplificare la riga di codice e renderla più leggibile*/
    }
    public void update(NoActionMove message){
        System.out.println(model.getTurnOfTheRound());
        model.updateTurnOfTheRound();
        System.out.println(model.getTurnOfTheRound());
        if(model.getTurnOfTheRound()<0){
            model.updateRound();
            if(model.getRoundNumber()==10){
                //model.countPoints
            }
            else
            model.updateGameboard();
        }
        else model.updateGameboard();
    }


    public void update(StartGameMessage startGameMessage){
        allPlayersReady = allPlayersReady + 1;
        if(allPlayersReady==4){
            model.sendSchemaAndTurn();
        }
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {

    }

    @Override
    public void update(SuccessMessage successMessage) {

    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {

    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {

    }

    @Override
    public void update(UpdateTurnMessage updateTurnMessage) {

    }


    public void sendSchemaCardController(){
        model.sendSchemaCard();
    }

    public Model getModel(){
        return model;
    }

    public void setTimer(int time){
        this.time = time;
    }

    public void roundManager() {

    }
}