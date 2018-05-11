package it.polimi.se2018.model;
//Giorgia

public class RoundTrack {

    private int turn;
    private RoundDice[] round = new RoundDice[10];

    public RoundTrack() {
        this.turn = 1;
        round = null;
    }

    //currentRoundDice rappresenta la lista di dadi "avanzati" al termine del turno
    public void setRound(RoundDice currentRoundDice, int currentTurn) {
        this.round[turn]=currentRoundDice;
        this.turn = currentTurn;
    }

    public RoundDice[] getRound() {
        return round;
    }

    public Dice getDice(int index) {
        return round[this.turn].getDice(index);
    }
}
