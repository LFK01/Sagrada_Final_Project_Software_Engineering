package it.polimi.se2018.model.events;
import it.polimi.se2018.model.Player;


public class NoActionMove extends PlayerMove {

    public NoActionMove(Player player){
        super(player);
    }

    @Override
    public boolean isDiceMove() {
        return false;
    }
}
