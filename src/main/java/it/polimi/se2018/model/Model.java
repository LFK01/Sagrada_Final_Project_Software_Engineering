package it.polimi.se2018.model;

public class Model {
    private GameBoard gameBoard;
    private int partecipants;
    private int turn = 0;

    public Model(GameBoard gameBoard, int partecipants){
        this.gameBoard = gameBoard;
        this.partecipants = partecipants;
    }

    public int getTurn() {
        return turn;
    }

    public int getPartecipants() {
        return partecipants;
    }
    public void performMove(int i){

    }
    public void permormMove(int pos){

    }


}