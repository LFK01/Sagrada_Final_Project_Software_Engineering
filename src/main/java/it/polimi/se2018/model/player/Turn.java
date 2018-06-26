package it.polimi.se2018.model.player;

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
