package it.polimi.se2018.controller;
import it.polimi.se2018.controller.exceptions.InvalidCellPositionException;
import it.polimi.se2018.controller.exceptions.InvalidDraftPoolPosException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.DiceBag;
import it.polimi.se2018.model.game_equipment.RoundDice;
import it.polimi.se2018.model.objective_cards.private_objective_cards.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Controller class
 * @author Luciano, Giovanni
 */

public class Controller extends Observable implements Observer {

    private Model model;
    private int time;
    private boolean timerStarted;
    private Timer t;
    private boolean enoughPlayers;

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
        model.doDiceMove(move);
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

    /**
     * Override of method update from observer
     * @param object the generic object that will receive the update (can be view or model)
     * @param message the generic message (can be a move or a message)
     */
    @Override
    public void update(Observable object, Object message) {
        try{
            Method update = this.getClass().getMethod("update", message.getClass());
            update.invoke(this, message);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e){
            e.printStackTrace();
        }
        //TODO new update(PlayerMove message) with all the instruction below
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
    }

    public void update(CreatePlayerMessage message){
        if(!timerStarted){
            timerStarted = true;
            System.out.println("timer inizializzato");
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(!enoughPlayers) {
                        if(model.getParticipants().size()>=2) {
                            /*enough player to start a match*/
                            System.out.println("Time's up, match starting with " + model.getParticipants().size() + " players");
                            model.sendPrivateObjectiveCard();
                        }
                        else {
                            /*Not enough player*/
                            System.out.println("Time's up, minimun player number not reached!");
                            notifyObservers(new ErrorMessage("model","all","NotEnoughPlayer"));
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

    public void update(ComebackSocketMessage message){
        setChanged();
        notifyObservers(new SuccessCreatePlayerMessage("server", message.getSender()));
    }

    public void update(SelectedSchemaMessage message){
        for(int playerPos =0;playerPos< model.getParticipants().size();playerPos++){
            if(model.getParticipants().get(playerPos).getName().equals(message.getSender())){
                //model.setSchemacardPlayer(playerPos,message);
            }
        }
        //estrae le toolcard e le manda
        model.extractPublicObjectiveCards();
        model.extractToolCards();




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
        model.extractPublicObjectiveCards();
        model.extractToolCards();
        model.getGameBoard().getRoundDice();

    }


}