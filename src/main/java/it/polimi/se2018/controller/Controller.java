package it.polimi.se2018.controller;
import it.polimi.se2018.controller.exceptions.InvalidCellPositionException;
import it.polimi.se2018.controller.exceptions.InvalidDraftPoolPosException;
import it.polimi.se2018.controller.exceptions.InvalidRoundException;
import it.polimi.se2018.controller.tool_cards.*;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.PlayerMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.controller.exceptions.InvalidValueException;
import it.polimi.se2018.model.objective_cards.public_objective_cards.*;
import it.polimi.se2018.model.objective_cards.private_objective_cards.*;
import it.polimi.se2018.view.*;

import java.util.*;

/**
 * Controller class
 * @author Giorgia
 */

public class Controller implements Observer {



    private Model model;
    private View view;
    private AbstractToolCard[] toolCards;

    /**
     * Class constructor
     * @param model model reference
     * @param view view reference
     */

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Method that randomly extracts 3 tool cards
     */

    public void extractToolCards() {

        ArrayList<Integer> cardIndex = new ArrayList<>(12);

        for(int i = 1; i <= 12; i++)
            cardIndex.add(i);

        Collections.shuffle(cardIndex);

        for(int i = 0; i < 3; i++) {

            switch (cardIndex.get(i)) {

                case 1:
                    toolCards[i] = PinzaSgrossatrice.getThisInstance();
                    break;

                case 2:
                    toolCards[i] = PennelloPerEglomise.getThisInstance();
                    break;

                case 3:
                    toolCards[i] = AlesatorePerLaminaDiRame.getThisInstance();
                    break;

                case 4:
                    toolCards[i] = Lathekin.getThisInstance();
                    break;

                case 5:
                    toolCards[i] = TaglierinaCircolare.getThisInstance();
                    break;

                case 6:
                    toolCards[i] = PennelloPerPastaSalda.getThisInstance();
                    break;

                case 7:
                    toolCards[i] = Martelletto.getThisInstance();
                    break;

                case 8:
                    toolCards[i] = TenagliaARotelle.getThisInstance();
                    break;

                case 9:
                    toolCards[i] = RigaInSughero.getThisInstance();
                    break;

                case 10:
                    toolCards[i] = TamponeDiamantato.getThisInstance();
                    break;

                case 11:
                    toolCards[i] = DiluentePerPastaSalda.getThisInstance();
                    break;

                case 12:
                    toolCards[i] = TaglierinaManuale.getThisInstance();
                    break;

            }

        }

        model.getGameBoard().setToolCards(toolCards);

    }

    /**
     * Method that randomly extracts and deals one private objective card per player
     */

    public void dealPrivateObjectiveCards() {

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

    public void extractPublicObjectiveCards() {

        ArrayList<Integer> cardIndex = new ArrayList<>(12);

        for(int i = 1; i <= 10; i++)
            cardIndex.add(i);

        Collections.shuffle(cardIndex);

        for(int i = 0; i < 3; i++) {

            switch (cardIndex.get(i)) {

                case 1:
                    model.getGameBoard().setPublicObjectiveCards(ColoriDiversiRiga.getThisInstance(), i);
                    break;

                case 2:
                    model.getGameBoard().setPublicObjectiveCards(ColoriDiversiColonna.getThisInstance(), i);
                    break;

                case 3:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureDiverseRiga.getThisInstance(), i);
                    break;

                case 4:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureDiverseRiga.getThisInstance(), i);
                    break;

                case 5:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureChiare.getThisInstance(), i);
                    break;

                case 6:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureMedie.getThisInstance(), i);
                    break;

                case 7:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureScure.getThisInstance(), i);
                    break;

                case 8:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureDiverse.getThisInstance(), i);
                    break;

                case 9:
                    model.getGameBoard().setPublicObjectiveCards(DiagonaliColorate.getThisInstance(), i);
                    break;

                case 10:
                    model.getGameBoard().setPublicObjectiveCards(VarietaDiColore.getThisInstance(), i);
                    break;

            }

        }

    }

    /**
     * Method to roll one single dice, assigning a random int value
     * @param dice dice chosen by the player
     * @throws NullPointerException in case the dice parameter is null
     */

    public void rollSingleDice(Dice dice) throws NullPointerException, InvalidValueException{

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

    public void rollRoundDice(DiceBag diceBag, int round, int participants) throws NullPointerException, InvalidRoundException {

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

    public void performDiceMove(ChooseDiceMove move) throws InvalidCellPositionException, InvalidDraftPoolPosException {

        if (((ChooseDiceMove) move).getRow() < 0 ||
                ((ChooseDiceMove) move).getRow() > 3 ||
                ((ChooseDiceMove) move).getCol() < 0 ||
                ((ChooseDiceMove) move).getCol() > 4) {

            throw new InvalidCellPositionException();
        }

        if (((ChooseDiceMove) move).getDraftPoolPos() < 0) {

            throw new InvalidDraftPoolPosException();

        }

        //gestione di altre eccezioni relative al caso

        //scelta e piazzamento dado
        /*Ci sarà una chiamata del tipo model.performDiceMove((ChooseDiceMove) move), e all'interno di questa verranno effettuati sia i controlli
        per verificare che la mossa sia lecita sia l'esecuzione stessa della mossa.*/
        //model.checkDiceMove((ChooseDiceMove) move);

        //move.getPlayer().getSchemaCard().getCell(((ChooseDiceMove) move).getRow(), ((ChooseDiceMove) move).getCol()).setAssignedDice(model.getGameBoard().getRoundDice()[model.getTurnOfTheRound()].getDice(((ChooseDiceMove) move).getDraftPoolPos()));
        //sistemata, eventualmente da rivedere per semplificare la riga di codice e renderla più leggibile

    }

    /**
     * Method that validates the input when the player chooses a tool card
     * @param move the object representing the move
     * @throws NullPointerException in case the tool card is null
     */

    public void performToolCardMove(UseToolCardMove move) throws NullPointerException {

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

        /**
         * If condition to establish whether the message is ChooseDiceMove, UseToolCardMove or NoActionMove
         */
        if(((PlayerMove) message).isDiceMove()) {

            try {

                performDiceMove((ChooseDiceMove) message);

            } catch (InvalidCellPositionException e1){

                /**
                 * InvalidCellPositionException handling: sends an ErrorMessage to the view asking for a valid input
                 */
                update(view, new ErrorMessage(((ChooseDiceMove) message).getPlayer(), "Scegli una posizione valida!"));

            } catch (InvalidDraftPoolPosException e2) {

                /**
                 * InvalidDraftPoolPosException handling: sends an ErrorMessage to the view asking for a valid input
                 */
                update(view, new ErrorMessage(((ChooseDiceMove) message).getPlayer(), "Inserisci un indice valido per la scelta del dado!"));

            }
        }

        else if (!((PlayerMove) message).isDiceMove()) {

            /**
             * In case the player decides not to do anything - or the time expires - calls the model method to update the turn
             */

            if (((PlayerMove) message).isNoActionMove()) {

                model.updateTurnOfTheRound();

            }

            else {

                try {

                    performToolCardMove((UseToolCardMove) message);

                } catch (NullPointerException e) {

                    /**
                     * NullPointerException handling: sends an ErrorMessage to the view asking for a valid input
                     */

                    update(view, new ErrorMessage(((UseToolCardMove) message).getPlayer(), "Scegli una carta utensile!"));

                }

            }

        }

    }

}