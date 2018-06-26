package it.polimi.se2018.model.player;

public class ToolCardMove {

    private boolean beenUsed;

    public ToolCardMove(){
        beenUsed = false;
    }

    public boolean isBeenUsed() {
        return beenUsed;
    }

    public void setBeenUsed(boolean beenUsed) {
        this.beenUsed = beenUsed;
    }

}
