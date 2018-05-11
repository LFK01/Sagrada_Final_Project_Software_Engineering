package it.polimi.se2018.model;

/**
 * @author Giovanni
 */

import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    private GameBoard gameBoard;
    private ArrayList<Player> participants;
    private int turn = 0;
    private boolean firstRunInTurn; /*boolean variable to memorize if every player has already chosen at least a die*/

    public Model(GameBoard gameBoard, ArrayList<Player> participants) {
        this.gameBoard = gameBoard;
        this.participants = participants;
    }

    public int getTurnOfTheRound() {
        return turn;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public int getParticipants() {
        return participants.size();
    }

    public Player getPlayer(int index) {
        return participants.get(index);
    }

    public void checkDiceMove(ChooseDiceMove move){
        if(!isPlayerTurn(move.getPlayer())) {
            notifyObservers(new ErrorMessage(move.getPlayer(), "Non &eacute; il tuo turno!"));
            return;
        }
        if( move.getPlayer().getSchemaCard().getCell( move.getPos() ).isFull() ){
            notifyObservers(new ErrorMessage(move.getPlayer(), "La cella &eacute; gi&aacute; occupata"));
            return;
        }
        if(!isValidPosition(move)){

        }
    }

    public void checkToolCardMove(UseToolCardMove move){

    }

    public void checkNoActionMove(NoActionMove move){

    }

    public boolean isPlayerTurn(Player player) {
        return participants.indexOf(player) == turn;
    }

    private boolean isValidPosition(ChooseDiceMove move){
        /*
        |posY    posX->
        v    1  2  3  4  5
        1     1  2  3  4  5
        2     6  7  8  9  10
        3     11 12 13 14 15
        4     16 17 18 19 20
         */
        if(move.getPlayer().getSchemaCard().isEmpty()){
            if(move.getRow()==0||move.getRow()==3){
                return true;
            }
            if(move.getCol()==0||move.getCol()==4) {
                return true;
            }
        }
        else{
            return hasADiceNear();
        }
        return false;//da rimuovere
    }

    /**
     * method to update the current player turn in a round
     * once the first run is completed, the method proceeds to count backwards
     */
    public void updateTurnOfTheRound(){
        if(gameBoard.getRoundDice()[turn].getDiceList().size()>(participants.size()*2+1)){
            /*one or more player has left the game*/

        }
        else{
            /*every player is still in the game*/
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
    }
}
