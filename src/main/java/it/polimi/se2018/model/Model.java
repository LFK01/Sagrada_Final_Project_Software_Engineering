package it.polimi.se2018.model;

/**
 * @author Giovanni
 */

import it.polimi.se2018.model.events.ChooseDiceMove;
import it.polimi.se2018.model.events.ErrorMessage;
import it.polimi.se2018.model.events.NoActionMove;
import it.polimi.se2018.model.events.UseToolCardMove;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    private GameBoard gameBoard;
    private ArrayList<Player> participants;
    private int turn = 0;

    public Model(GameBoard gameBoard, ArrayList<Player> partecipants) {
        this.gameBoard = gameBoard;
        this.participants = partecipants;
    }

    public int getTurn() {
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
        if(participants.indexOf(player)==turn){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isValidPosition(ChooseDiceMove move){
        /*
        |posY    posX->
        v    1  2  3  4  5
        1     1  2  3  4  5
        2     6  7  8  9  10
        3     11 12 13 14 15
        4     16 17 18 19 20
         */
        //move.getPlayer().getSchemaCard()
        return false;
    }
    public void updateTurn(){
        if (turn==4){
            turn=1;
        }
        else{
            turn++;
        }
    }

    /**
     * metodo per aggiungere un giocatore
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
