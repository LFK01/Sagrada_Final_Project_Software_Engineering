package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Model;

public interface EffectInterface {

    void doYourJob(String username, String toolCardName, String values, Model model);

    boolean isDone();

    void setDone(boolean b);
}
