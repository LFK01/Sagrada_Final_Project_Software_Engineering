package it.polimi.se2018.view;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ChooseDiceMove;
import it.polimi.se2018.model.events.MoveMessage;

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

    public void handleDiceMove(int diceIndex, int pos){
        notifyObservers(new ChooseDiceMove(pos,diceIndex,player));
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


