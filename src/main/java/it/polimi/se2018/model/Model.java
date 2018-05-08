package it.polimi.se2018.model;
//Giovanni

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

    public int getPartecipants() {
        return participants.size();
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


}
