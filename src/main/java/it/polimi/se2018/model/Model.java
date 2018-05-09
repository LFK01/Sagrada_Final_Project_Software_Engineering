package it.polimi.se2018.model;

/**
 * @author Giovanni
 */

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {
    private GameBoard gameBoard;
    private ArrayList<Player> participants;
    private int turn = 0;

    public Model(GameBoard gameBoard,ArrayList<Player> partecipants) {
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

    public void performDiceMove(int pos){

    }

    public void permormToolCardMove(int i){

    }

    public boolean isPlayerTurn(Player player) {
        return true; //implementazione provvisoria da modificare - Luciano
    }

    public void updateTurn(){

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
