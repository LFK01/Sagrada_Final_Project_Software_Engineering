package it.polimi.se2018.controller;
import it.polimi.se2018.controller.exceptions.InvalidCellPositionException;
import it.polimi.se2018.controller.exceptions.InvalidDraftPoolPosException;
import it.polimi.se2018.controller.exceptions.InvalidRoundException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.PlayerMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.model.exceptions.PlayerNumberExceededException;
import it.polimi.se2018.model.objective_cards.private_objective_cards.*;
import it.polimi.se2018.view.*;

import java.util.*;

/**
 * Controller class
 * @author Giorgia
 */

public class Controller extends Observable implements Observer {

    private Model model;
    /**
     * Class constructor
     */
    public Controller() {
        this.model = new Model();
    }

    /**
     * Method that randomly extracts 3 tool cards
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

        for(int i = 1; i <= 5; i++)
            cardIndex.add(i);

        Collections.shuffle(cardIndex);

        for(int i = 0; i < model.getParticipantsNumber(); i++) {

            switch(cardIndex.get(i)) {

                case 1:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureRosse.getThisInstance());
                    break;

                case 2:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureGialle.getThisInstance());
                    break;

                case 3:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureVerdi.getThisInstance());
                    break;

                case 4:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureBlu.getThisInstance());
                    break;

                case 5:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureViola.getThisInstance());
                    break;

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

    private void rollSingleDice(Dice dice) throws NullPointerException/*, InvalidValueException*/{

        if (dice == null)
            throw new NullPointerException();

        dice.setValue((int)Math.ceil(Math.random()*6));

    }

    /**
     * Method to throw the dice at the beginning of a round. It creates a new RoundDice instance for every round and associates it
     * to its position in the RoundTrack array (RoundTrack[round]).
     * @param diceBag the match diceBag already shuffled
     * @param round round number
     * @param participants number of players
     * @throws NullPointerException in case diceBag is null
     * @throws InvalidRoundException in case the round number exceeds 10
     */

    private void rollRoundDice(DiceBag diceBag, int round, int participants) throws NullPointerException, InvalidRoundException {

        if (round > 10)
            throw new InvalidRoundException();

        if (diceBag == null)
            throw new NullPointerException();

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
        per verificare che la mossa sia lecita sia l'esecuzione stessa della mossa.*/
        //model.doDiceMove((ChooseDiceMove) move);

        //move.getPlayer().getSchemaCard().getCell(((ChooseDiceMove) move).getRow(), ((ChooseDiceMove) move).getCol()).setAssignedDice(model.getGameBoard().getRoundDice()[model.getTurnOfTheRound()].getDice(((ChooseDiceMove) move).getDraftPoolPos()));
        //sistemata, eventualmente da rivedere per semplificare la riga di codice e renderla più leggibile

    }

    /**
     * Method that validates the input when the player chooses a tool card
     * @param move the object representing the move
     * @throws NullPointerException in case the tool card is null
     */

    private void performToolCardMove(UseToolCardMove move) throws NullPointerException {

        if (move.getToolCard() == null)
            throw new NullPointerException();

        move.getToolCard().activateCard(move.getPlayer());

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
        if (this.getModel().isPlayerTurn(((Message)message).getPlayer())) {
            if ((message instanceof CreatePlayerMessage)) {
                try {
                    model.addPlayer(((CreatePlayerMessage) message).getPlayerName());
                } catch (PlayerNumberExceededException e) {
                    System.out.println("numero massimo di giocatori raggiunto");  //può essere sostituito dal messaggio presente nel'
                }

            }


            /**
             * If condition to establish whether the message is ChooseDiceMove, UseToolCardMove or NoActionMove
             */{
                if (message instanceof PlayerMove) {
                    if (((PlayerMove) message).isDiceMove()) {

                        try {

                            performDiceMove((ChooseDiceMove) message);

                        } catch (InvalidCellPositionException e1) {

                            /**
                             * InvalidCellPositionException handling: sends an ErrorMessage to the view asking for a valid input
                             */
                            notifyObservers(/*Insert errore message here*/);

                        } catch (InvalidDraftPoolPosException e2) {

                            /**
                             * InvalidDraftPoolPosException handling: sends an ErrorMessage to the view asking for a valid input
                             */
                            notifyObservers(/*Insert errore message here*/);

                        }
                    } else if (!((PlayerMove) message).isDiceMove()) {

                        /**
                         * In case the player decides not to do anything - or the time expires - calls the model method to update the turn
                         */

                        if (((PlayerMove) message).isNoActionMove()) {

                            model.updateTurnOfTheRound();

                        } else {

                            try {

                                performToolCardMove((UseToolCardMove) message);

                            } catch (NullPointerException e) {

                                /**
                                 * NullPointerException handling: sends an ErrorMessage to the view asking for a valid input
                                 */

                                setChanged();
                                notifyObservers(/*inserire messaggio di errore*/);
                            }

                        }

                    }
                }
            }
        }
        else{
            notify(/*inserire messaggio*/); //notifica che non è il turno del giocatore
        }

    }

    //metto un nuovo metodo update per poter gestire i messaggi che arrivano da Player message (inizializzazione del giocatore)
    public void update(CreatePlayerMessage message){

        try{
            model.addPlayer(message.getPlayerName());
        } catch (PlayerNumberExceededException e) {
            System.out.println("numero massimo di giocatori raggiunto");  //può essere sostituito dal messaggio presente nel'
        }


    }

    //update che serve per aggiornare il il turno e passarlo al giocatore successivo
    public void update (Observable objecy, NoActionMove move){
        model.updateTurnOfTheRound();
    }





    //aggiungo classe che potrebbe servirmi per test su controller
    public Model getModel(){
        return model;
    }


}