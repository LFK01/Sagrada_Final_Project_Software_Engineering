package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;

//Giorgia
public abstract class PlayerMove {

    protected Player player;
    //i riferimenti alla View sono momentaneamente commentati in attesa dell'implementazione della classe corrispondente
    //protected View view;

    public PlayerMove(Player p/*, View v*/) {
        this.player = p;
        //this.view = v;
    }

    public Player getPlayer() {
        return player;
    }

    /*public View getView() {
        return view;
    }*/

    //metodo astratto per distinguere la mossa eseguita
    public abstract boolean isDiceMove();

}
