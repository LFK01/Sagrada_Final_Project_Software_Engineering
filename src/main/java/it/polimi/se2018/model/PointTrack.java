package it.polimi.se2018.model;

//Giovanni
public class PointTrack {
    private int[] playerPoints;
    private int minimumPointsSinglePlayer;

    public PointTrack(int numeroGiocatori){
        this.playerPoints = new int[numeroGiocatori];

    }

    public int getMinimumPointsSinglePlayer() {
        return minimumPointsSinglePlayer;
    }

}
