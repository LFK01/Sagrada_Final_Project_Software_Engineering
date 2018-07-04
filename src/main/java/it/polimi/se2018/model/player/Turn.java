package it.polimi.se2018.model.player;

/**
 * @author luciano
 * Class representing the possible moves in a turn
 * Each player has two moves per turn
 */

public class Turn {

    private ToolCardMove toolMove;
    private PlaceDieMove dieMove;

    public Turn(){
        toolMove = new ToolCardMove();
        dieMove = new PlaceDieMove();
    }

    public PlaceDieMove getDieMove() {
        return dieMove;
    }

    public ToolCardMove getToolMove() {
        return toolMove;
    }

}
