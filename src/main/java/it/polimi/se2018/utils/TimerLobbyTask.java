package it.polimi.se2018.utils;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Model;

import java.util.TimerTask;

public class TimerLobbyTask extends TimerTask {

    private boolean isStarted;

    private boolean isEnded;

    private Controller controller;
    private Model model;

    public TimerLobbyTask(Controller controller, Model model){
        this.controller = controller;
        this.model = model;
        isStarted = false;
        isEnded = false;
    }

    @Override
    public void run() {
        if (controller.countConnectedPlayer() > 1) {
            isEnded = true;
            model.sendPrivateObjectiveCard();
            controller.waitSchemaCards();
        }
        if (controller.countConnectedPlayer() < 2) {
            isStarted = false;
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
