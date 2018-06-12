package it.polimi.se2018.model.player;

public class UseToolCardMove {

    private boolean beenUsed;

    public UseToolCardMove(){
        beenUsed = false;
    }

    public boolean isBeenUsed() {
        return beenUsed;
    }

    public void setBeenUsed(boolean beenUsed) {
        this.beenUsed = beenUsed;
    }

}
