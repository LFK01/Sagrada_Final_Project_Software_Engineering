package it.polimi.se2018.model.player;

public class Turn {

    private UseToolCardMove toolMove;
    private PlaceDieMove dieMove;

    public Turn(){
        toolMove = new UseToolCardMove();
        dieMove = new PlaceDieMove();
    }

    public PlaceDieMove getDieMove() {
        return dieMove;
    }

    public UseToolCardMove getToolMove() {
        return toolMove;
    }

}
