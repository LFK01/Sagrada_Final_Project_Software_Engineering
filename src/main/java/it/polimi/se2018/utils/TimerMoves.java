package it.polimi.se2018.utils;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.ErrorMessage;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.player.Player;

import java.util.TimerTask;

public class TimerMoves extends TimerTask {

    private Controller controller;
    private Model model;
    private boolean hasEnded;

    public TimerMoves(Controller controller, Model model){
        this.hasEnded = false;
        this.controller = controller;
        this.model = model;
    }

    @Override
    public void run() {
        this.hasEnded = true;
        Player activePlayer = model.getPlayer(model.getTurnOfTheRound());
        if(controller.countConnectedPlayer()>2) {
            controller.setChanged();
            controller.notifyObservers(new ErrorMessage("server", activePlayer.getName(), "TimeElapsed"));
            System.out.println("calling blockPlayer from TimerMoves.run");
            controller.blockPlayer(activePlayer.getName());
        } else {
            model.getParticipants().stream().filter(
                    p -> !p.equals(activePlayer)
            ).forEach(
                    p -> model.singlePlayerWinning(p)
            );
        }
    }

    public boolean hasEnded() {
        return hasEnded;
    }


}
