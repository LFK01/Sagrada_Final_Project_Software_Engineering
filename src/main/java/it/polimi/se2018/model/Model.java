package it.polimi.se2018.model;

import it.polimi.se2018.model.exceptions.NotEnoughFavorTokensException;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.objective_cards.public_objective_cards.*;
import it.polimi.se2018.model.tool_cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

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

public class Model extends Observable {

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
    private boolean doubleDraftDone;
    /*local variable to memorize if the last player in the first dice draft has already
    * drafted the second die*/

    /**
     * Constructor method initializing turnOfTheRound and the participant list
     */
    public Model() {
        this.gameBoard = new GameBoard();
        turnOfTheRound = 0;
        firstDraftOfDice = true;
        doubleDraftDone = false;
        participants = new ArrayList<>();
    }

    /**
     * Getter for integer value of turnOfTheRound of the Round
     * @return integer value turnOfTheRound of the Round
     */
    public int getTurnOfTheRound() {
        return turnOfTheRound;
    }

    /**
     *
     * @return gameBoard reference
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     *
     * @return integer value of the participants number
     */
    public int getParticipantsNumber() {
        return participants.size();
    }

    /**
     *
     * @param index
     * @return reference of the player specified in the index parameter
     */
    public Player getPlayer(int index) {
        return participants.get(index);
    }

    public boolean isFirstDraftOfDice() {
        return firstDraftOfDice;
    }
    /**
     * method to check if a player can place a die in a position on his/her schema card
     * @param move data structure containing all the information about the player move
     */
    public void doDiceMove(ChooseDiceMove move) {
        if (!isPlayerTurn(move.getPlayer())) {
            notifyObservers(new ErrorMessage(move.getPlayer(), "Non &eacute; il tuo turno!"));
            return;
        }
        try{
            placeDie(move.getPlayer().getSchemaCard(), move.getCol(), move.getRow(), move.getDraftPoolPos());
            removeDieFromDrafPool(move.getDraftPoolPos());
        }
        catch(FullCellException e){
            notifyObservers(new ErrorMessage(move.getPlayer(), "La posizione &eacute; gi&aacute; occupata"));
        }
        catch(RestrictionsNotRespectedException e){
            notifyObservers(new ErrorMessage(move.getPlayer(), "La posizione del dado non &eacute; valida"));
        }
    }

    private void removeDieFromDrafPool(int draftPoolPos) {
        int currentRound = gameBoard.getRoundTrack().getCurrentRound();
        gameBoard.getRoundDice()[currentRound].removeDiceFromDraftPool(draftPoolPos);
    }

    /**
     * method to check if a player can activate a card specified on the parameter move
     * @param move data structure containing all the information about the player move
     */
    public void doToolCardMove(UseToolCardMove move) {

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

    private void placeDie(SchemaCard schemaCard, int drafPoolPos, int row, int col) throws RestrictionsNotRespectedException, FullCellException{
        Dice chosenDie = gameBoard.getRoundDice()[gameBoard.getRoundTrack().getCurrentRound()].getDice(drafPoolPos);
        schemaCard.placeDie(chosenDie, row, col);
    }

    /**
     *
     * method to update the current player turnOfTheRound in a round
     * once the first run is completed, the method proceeds to count backwards
     */
    public void updateTurnOfTheRound(){
        if(isFirstDraftOfDice()){
            /*first run of turns to choose a die*/
            if(turnOfTheRound == participants.size()-1){
                turnOfTheRound = participants.size()-1;
                doubleDraftDone = false;
                firstDraftOfDice = false;
            }
            else {
                turnOfTheRound++;
            }
        }
        else{
            /*second run of turns to choose a die*/
            if(turnOfTheRound == participants.size()-1) {
                if (!doubleDraftDone) {
                    turnOfTheRound = participants.size() - 1;
                } else {
                    turnOfTheRound--;
                }
            }
            else{
                turnOfTheRound--;
            }
        }
        notifyObservers();
    }

    /**
     * method to add a new player
     * @param name
     */
    public void addPlayer(String name)/*throws PlayerNumberExceededException*/{
        if(participants.size()<4){
            participants.add(new Player(name));
        }
        else{
            //throw PlayerNumberExceededException
        }
        notifyObservers();
    }

    /**
     *
     * @param player
     * metodo per rimuovere giocatore dalla lista dei giocatori
     */
    public void removePlayer(Player player)/*throws SinglePlayerException*/{
        if(participants.size()>1){
            participants.remove(participants.indexOf(player));
        }
        else{
            //throw SinglePlayerMatchException
        }
        notifyObservers();
    }

    /**
     * method to modify the token numeber of a specific player
     * @param index number of tokens to deduct
     * @param playerPosition integer number to get the player reference
     */
    public  void  updateFavorTokens(int index, int playerPosition) throws NotEnoughFavorTokensException {
        if (participants.get(playerPosition).getFavorTokens()==0)
            throw new NotEnoughFavorTokensException();

        if(gameBoard.getToolCard(index).isFirstUsage()) {
                participants.get(playerPosition).decreaseFavorTokens();
        }
        else {
            if(participants.get(playerPosition).getFavorTokens()==1) throw new NotEnoughFavorTokensException();
            else {
                participants.get(playerPosition).decreaseFavorTokens();
                participants.get(playerPosition).decreaseFavorTokens();
            }
        }

    }

    public void extractToolCards() {

        ArrayList<Integer> cardIndex = new ArrayList<>(12);

        for(int i = 1; i <= 12; i++)
            cardIndex.add(i);

        Collections.shuffle(cardIndex);

        for(int i = 0; i < 3; i++) {

            switch (cardIndex.get(i)) {

                case 1:

                    gameBoard.setToolCards(PinzaSgrossatrice.getThisInstance(), i);
                    break;

                case 2:
                    gameBoard.setToolCards(PennelloPerEglomise.getThisInstance(), i);
                    break;

                case 3:
                    gameBoard.setToolCards(AlesatorePerLaminaDiRame.getThisInstance(), i);
                    break;

                case 4:
                    gameBoard.setToolCards(Lathekin.getThisInstance(), i);
                    break;

                case 5:
                    gameBoard.setToolCards(TaglierinaCircolare.getThisInstance(), i);
                    break;

                case 6:
                    gameBoard.setToolCards(PennelloPerPastaSalda.getThisInstance(), i);
                    break;

                case 7:
                    gameBoard.setToolCards(Martelletto.getThisInstance(), i);
                    break;

                case 8:
                    gameBoard.setToolCards(TenagliaARotelle.getThisInstance(), i);
                    break;

                case 9:
                    gameBoard.setToolCards(RigaInSughero.getThisInstance(), i);
                    break;

                case 10:
                    gameBoard.setToolCards(TamponeDiamantato.getThisInstance(), i);
                    break;

                case 11:
                    gameBoard.setToolCards(DiluentePerPastaSalda.getThisInstance(), i);
                    break;

                case 12:
                    gameBoard.setToolCards(TaglierinaManuale.getThisInstance(), i);
                    break;

            }

        }

        notifyObservers();
        setChanged();
    }

    public void extractPublicObjectiveCards() {

        ArrayList<Integer> cardIndex = new ArrayList<>(12);

        for(int i = 1; i <= 10; i++)
            cardIndex.add(i);

        Collections.shuffle(cardIndex);

        for(int i = 0; i < 3; i++) {

            switch (cardIndex.get(i)) {

                case 1:
                    gameBoard.setPublicObjectiveCards(ColoriDiversiRiga.getThisInstance(), i);
                    break;

                case 2:
                    gameBoard.setPublicObjectiveCards(ColoriDiversiColonna.getThisInstance(), i);
                    break;

                case 3:
                    gameBoard.setPublicObjectiveCards(SfumatureDiverseRiga.getThisInstance(), i);
                    break;

                case 4:
                    gameBoard.setPublicObjectiveCards(SfumatureDiverseRiga.getThisInstance(), i);
                    break;

                case 5:
                    gameBoard.setPublicObjectiveCards(SfumatureChiare.getThisInstance(), i);
                    break;

                case 6:
                    gameBoard.setPublicObjectiveCards(SfumatureMedie.getThisInstance(), i);
                    break;

                case 7:
                    gameBoard.setPublicObjectiveCards(SfumatureScure.getThisInstance(), i);
                    break;

                case 8:
                    gameBoard.setPublicObjectiveCards(SfumatureDiverse.getThisInstance(), i);
                    break;

                case 9:
                    gameBoard.setPublicObjectiveCards(DiagonaliColorate.getThisInstance(), i);
                    break;

                case 10:
                    gameBoard.setPublicObjectiveCards(VarietaDiColore.getThisInstance(), i);
                    break;

            }

        }

        notifyObservers();
        setChanged();

    }



}
