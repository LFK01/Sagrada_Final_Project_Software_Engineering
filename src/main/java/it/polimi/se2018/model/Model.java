package it.polimi.se2018.model;

import it.polimi.se2018.model.events.messages.SuccessMessage;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;

import java.util.ArrayList;
import java.util.Observable;

/**
 * @author Giovanni
 * modified: - Luciano 12/05/2018;
 *
 * this class is supposed to contain all the data about a game and all the
 * controls to check if any move is correct. The data is accessible through
 * getter methods. Whenever a modifier method is activated by an external class
 * Model verifies that the parameters are correct and notifies its observer with
 * a Message
 */
public class Model extends Observable {

    private GameBoard gameBoard;
    /*local instance of the gameBoard used to access all objects and
     * methods of the game instrumentation*/
    private ArrayList<Player> participants;
    /*local ArrayList to memorize actual playing players*/
    private int turn;
    /*local variable to memorize the current turn in a round, it goes from 1
     * to participants.size()*/

    /**
     * constructor method initializing turn and the participant list
     */
    public Model() {
        turn = 1;
        participants = new ArrayList<>();
    }

    /**
     *
     * @return integer value turn of the Round
     */
    public int getTurnOfTheRound() {
        return turn;
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

    /**
     * method to check if a player can place a die in a position on his/her schema card
     * @param move data structure containing all the information about the player move
     */
    public void checkDiceMove(ChooseDiceMove move) {
        Dice chosenDie = gameBoard.getRoundDice()[gameBoard.getRoundTrack().getCurrentRound()].getDice(move.getDraftPoolPos());
        if (!isPlayerTurn(move.getPlayer())) {
            notifyObservers(new ErrorMessage(move.getPlayer(), "Non &eacute; il tuo turno!"));
            return;
        }
        if (move.getPlayer().getSchemaCard().getCell(move.getRow(), move.getCol()).isFull()) {
            notifyObservers(new ErrorMessage(move.getPlayer(), "La cella &eacute; gi&aacute; occupata"));
            return;
        }
        if (!isValidPosition(move.getPlayer().getSchemaCard(), move.getRow(), move.getCol(), chosenDie.getValue(), chosenDie.getDiceColor())) {
            notifyObservers(new ErrorMessage(move.getPlayer(), "La posizione del dado non &eacute; valida"));
        }
        //placeDie(int drafPoolPos, int col, int row, int);
    }

    /**
     * method to check if a player can activate a card specified on the parameter move
     * @param move data structure containing all the information about the player move
     */
    public void checkToolCardMove(UseToolCardMove move) {

    }

    /**
     * method to check if a player can skip a move
     * @param move
     */
    public void checkNoActionMove(NoActionMove move) {

    }

    /**
     * method to check if the turn of the player specified in the parameter field
     * @param player
     * @return true if is the turn of the current player
     */
    public boolean isPlayerTurn(Player player) {
        return participants.indexOf(player) == turn;
    }

    /**
     * method to check if player has chosen a valid position
     * @param schemaCard to see if its empty
     * @param col to know die position
     * @param row to know die position
     * @return boolean value true if the die position is correct
     * and the die can be placed on the SchemaCard
     */
    private boolean isValidPosition(SchemaCard schemaCard, int row, int col, int value, Color color) {
        /*
   posY, row|             posX, col->
            v             0  1  2  3  4

            0             1  2  3  4  5
            1             6  7  8  9  10
            2             11 12 13 14 15
            3             16 17 18 19 20
         */
        if (schemaCard.isEmpty()) {
            if (row == 0 || row == 3) {
                return (respectsColorRestrictions(schemaCard, color, row, col) && respectsValueRestrictions(schemaCard, value, row, col));
            }
            if (col == 0 || col == 4) {
                return (respectsColorRestrictions(schemaCard, color, row, col) && respectsValueRestrictions(schemaCard, value, row, col));
            }
        }
        return (hasADieNear(schemaCard, col, row) && respectsColorRestrictions(schemaCard, color, row, col) && respectsValueRestrictions(schemaCard, value, row, col));
    }

    /**
     * method to check if a die respects value restrictions
     * @param schemaCard to access schema's cells
     * @param value integer value to see if a die respects value restriction
     * @param row integer value to access the schema cell
     * @param col integer value to access the schema cell
     * @return returns true if the chosen value respects the cell's value restriction
     */
    private boolean respectsValueRestrictions(SchemaCard schemaCard, int value, int row , int col) {
        if (schemaCard.getCell(row, col).getValue()==0){
            return true;
        }
        return (schemaCard.getCell(row, col).getValue()==value);
    }

    /**
     * method to check if a die respects color restrctions
     * @param schemaCard
     * @param color
     * @param row
     * @param col
     * @return
     */
    private boolean respectsColorRestrictions(SchemaCard schemaCard, Color color, int row, int col) {
        return (schemaCard.getCell(row, col).getCellColor()==color);
    }

    /**
     * method to check if there's a full cell that has at least one corner
     * in common with the cell of the considered die
     *
     * @return true if there's die in any die in the cells near the one
     * of the considered die
     */
    private boolean hasADieNear(SchemaCard schemaCard, int row, int col) {
        if (col == 0) {
            if (row == 0) {
                if (schemaCard.getCell(row, col + 1).isFull()) {
                    return true;
                }
                if (schemaCard.getCell(row + 1, col + 1).isFull()) {
                    return true;
                }

                if (schemaCard.getCell(row + 1, col).isFull()) {
                    return true;
                }
                return false;
            }
            if (row == 3) {
                if (schemaCard.getCell(row - 1, col).isFull()) {
                    return true;
                }
                if (schemaCard.getCell(row - 1, col + 1).isFull()) {
                    return true;
                }
                if (schemaCard.getCell(row, col + 1).isFull()) {
                    return true;
                }
                return false;
            }
            for (int i = 0; i < 3; i++) {
                if (schemaCard.getCell(row - 1 + i, col + 1).isFull()) {
                    return true;
                }
            }
            if (schemaCard.getCell(row - 1, col).isFull()) {
                return true;
            }
            if (schemaCard.getCell(row + 1, col).isFull()) {
                return true;
            }
            return false;
        }
        if (col == 4) {
            if (row == 0) {
                if (schemaCard.getCell(row, col - 1).isFull()) {
                    return true;
                }
                if (schemaCard.getCell(row + 1, col - 1).isFull()) {
                    return true;
                }

                if (schemaCard.getCell(row + 1, col).isFull()) {
                    return true;
                }
                return false;
            }
            if (row == 3) {
                if (schemaCard.getCell(row - 1, col).isFull()) {
                    return true;
                }
                if (schemaCard.getCell(row - 1, col - 1).isFull()) {
                    return true;
                }

                if (schemaCard.getCell(row, col - 1).isFull()) {
                    return true;
                }
                return false;
            }
            for (int i = 0; i < 3; i++) {
                if (schemaCard.getCell(row - 1 + i, col - 1).isFull()) {
                    return true;
                }
            }
            if (schemaCard.getCell(row - 1, col).isFull()) {
                return true;
            }
            if (schemaCard.getCell(row + 1, col).isFull()) {
                return true;
            }
            return false;
        }
        if (row == 0) {
            for (int i = 0; i < 3; i++) {
                if (schemaCard.getCell(row + 1, col - 1 + i).isFull()) {
                    return true;
                }
            }
            if (schemaCard.getCell(row, col - 1).isFull()) {
                return true;
            }
            if (schemaCard.getCell(row, col + 1).isFull()) {
                return true;
            }
            return false;
        }
        if (row == 3) {
            for (int i = 0; i < 3; i++) {
                if (schemaCard.getCell(row - 1, col - 1 + i).isFull()) {
                    return true;
                }
            }
            if (schemaCard.getCell(row, col - 1).isFull()) {
                return true;
            }
            if (schemaCard.getCell(row, col + 1).isFull()) {
                return true;
            }
            return false;
        }
        for(int i=0; i<3; i++){
            if (schemaCard.getCell(row-1, col-1+i).isFull()) {
                return true;
            }
            if (schemaCard.getCell(row+1, col-1+i).isFull()) {
                return true;
            }
        }
        if (schemaCard.getCell(row-1, col).isFull()) {
            return true;
        }
        if (schemaCard.getCell(row+1, col).isFull()) {
            return true;
        }
        return false;
    }

    /**
     * method to update the current player turn in a round
     * once the first run is completed, the method proceeds to count backwards
     */
    public void updateTurnOfTheRound(){
        if(gameBoard.getRoundDice()[turn].getDiceList().size()>(participants.size()*2+1)){
            /*one or more player has left the game*/
            /*one or more player has refused to pick a die*/
        }
        else{
            /*every player is still in the game*/
            /*every player has chosen a die*/
            if(gameBoard.getRoundDice()[turn].getDiceList().size()>(participants.size()+1)){
                /*first run of turns to choose a die*/
                if(turn==participants.size()){
                    turn=1;
                }
                else {
                    turn++;
                }
            }
            else{
                /*second run of turns to choose a die*/
                if(turn==1){
                    turn=participants.size();
                }
                else{
                    turn--;
                }
            }
        }
        setChanged();
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
        setChanged();
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
        setChanged();
        notifyObservers();
    }

    /**
     * mthod to modify the token numeber of a specific player
     * @param index number of tokens to deduct
     * @param playerPosition integer number to get the player reference
     */
    public void updateFavorTokens(int index,int playerPosition){
        if(gameBoard.getToolCardState(index))
        try{
            participants.get(playerPosition).decreaseFavorTokens();
        }
        catch(NullPointerException e){ //eccezione temporanea, ne va messa una che avvisa de player.favortokens va a 0
            e.fillInStackTrace();
            //va generato un messaggio di errore
        }
        else {
            try {
                participants.get(playerPosition).decreaseFavorTokens();
                participants.get(playerPosition).decreaseFavorTokens();
            }
            catch(NullPointerException e){      //eccezione temporanea come la precedente
                e.fillInStackTrace();
                //va generato un messaggio di errore
            }
        }

    }

}
