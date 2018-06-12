package it.polimi.se2018.model.player;

public class Round {

    private Turn turn1;
    private Turn turn2;

    public Round(){
        turn1 = new Turn();
        turn2 = new Turn();
    }

    public Turn getTurn1() {
        return turn1;
    }

    public Turn getTurn2() {
        return turn2;
    }

}
