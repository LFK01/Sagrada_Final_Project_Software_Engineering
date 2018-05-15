package it.polimi.se2018.view;
import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.PointTrack;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.messages.MoveMessage;

import java.util.Observable;
import java.util.Observer;

/**
 * @author giovanni
 */

public abstract class View extends Observable implements Observer{
    private Player player;
    private Model model;

    /**
     * Initializes view connected with player and model
     * @param player
     * @param model
     */
    public View(Player player, Model model){
        this.player= player;
        this.model = model;
    }

    /**
     *
     * @return a referece to player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *method that throws a message to controller with the description of the move
     * @param draftPoolPos  a class that contains dice in the game
     * @param row   it serves for the position of the die
     * @param col   it serves for the position of the die
     */
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


