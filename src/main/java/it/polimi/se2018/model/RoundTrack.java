package it.polimi.se2018.model;
//Giorgia

public class RoundTrack {

    private int currentRound;
    private RoundDice[] round = new RoundDice[10];

    public RoundTrack() {
        this.currentRound = 1;
        round = null;
    }

    //currentRoundDice rappresenta la lista di dadi "avanzati" al termine del turno
    public void setRound(RoundDice currentRoundDice, int currentRound) {
        this.round[this.currentRound]=currentRoundDice;
        this.currentRound = currentRound;
    }

    public RoundDice[] getRound() {
        return round;
    }

    public Dice getDice(int index) {
        return round[this.currentRound].getDice(index);
    }
}
