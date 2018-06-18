package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.model.Model;

public interface TCEffectInterface {

    void doYourJob(String username, String toolCardName, String values, Model model) throws ExecutingEffectException;

    boolean isDone();

    void setDone(boolean b);
}
