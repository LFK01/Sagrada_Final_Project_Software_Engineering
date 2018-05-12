package it.polimi.se2018.model.events.moves;
import it.polimi.se2018.model.Player;


public class NoActionMove extends PlayerMove {

    public NoActionMove(Player player){
        super(player);
    }

    @Override
    public boolean isDiceMove() {
        return false;
    }

    @Override
    public boolean isNoActionMove() {
        return true;
    }

}
