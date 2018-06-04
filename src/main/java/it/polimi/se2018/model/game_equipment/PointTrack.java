package it.polimi.se2018.model.game_equipment;

/**
 * @author giovanni
 */
public class PointTrack {
    private int[] playerPoints;
    private int minimumPointsSinglePlayer;

    public PointTrack(int numeroGiocatori){
        this.playerPoints = new int[numeroGiocatori];

    }

    public int getMinimumPointsSinglePlayer() {
        return minimumPointsSinglePlayer;
    }

    public int[] getPlayerPoints() {
        return playerPoints;
    }
}
