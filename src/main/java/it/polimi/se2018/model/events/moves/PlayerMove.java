package it.polimi.se2018.model.events.moves;

import it.polimi.se2018.model.player.Player;

/**
 * @author Giorgia
 */

public abstract class PlayerMove {

    protected Player player;

    public PlayerMove(Player player) {
        this.player = player;

    }

    public Player getPlayer() {
        return player;
    }

    //metodo astratto per distinguere la mossa eseguita
    public abstract boolean isDiceMove();
    public abstract boolean isNoActionMove();

}
