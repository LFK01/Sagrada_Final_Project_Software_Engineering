package it.polimi.se2018.model.player;

/**
 * @author luciano
 * Class with a boolean to know if the player
 * has already performed a tool card move or not
 */
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
