package it.polimi.se2018.model.player;

/**
 * @author luciano
 * Class with a boolean to know if the player
 * has already performed a place die move or not.
 */
public class PlaceDieMove {

    private boolean beenUsed;

    public PlaceDieMove(){
        beenUsed = false;
    }

    public boolean isBeenUsed() {
        return beenUsed;
    }

    public void setBeenUsed(boolean beenUsed) {
        this.beenUsed = beenUsed;
    }

}
