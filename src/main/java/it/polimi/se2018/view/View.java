package it.polimi.se2018.view;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.messages.MoveMessage;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;

import java.util.Observable;
import java.util.Observer;

/**
 * @author giovanni
 */

public abstract class View extends Observable implements Observer{
    private Player player;
    private Model model;

    public View(Player player, Model model){
        this.player= player;
        this.model = model;
    }
    public Player getPlayer() {
        return player;
    }

    public void handleDiceMove(int draftPoolPos ,int row,int col){
        notifyObservers(new ChooseDiceMove(draftPoolPos,row,col,player));
    }

    public void handleToolCardMove(int index){


    }
    public void showMessage(String message){


    }
    public void reportError(String message){


    }
    public void update(MoveMessage message){


    }
    @Override
    public void update(Observable model, Object message){
        message = (MoveMessage) message;
    }



}


