package it.polimi.se2018.model.player;

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
