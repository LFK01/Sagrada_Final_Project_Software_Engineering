package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.view.comand_line.InputManager;

public class Lathekin extends AbstractToolCard {

    private static Lathekin thisInstance;

    private Lathekin() {
        super(3);
    }

    public synchronized static Lathekin getThisInstance(){
        if(thisInstance==null){
            thisInstance = new Lathekin();
        }
        return thisInstance;
    }

    @Override
    public void activateToolCard(String username, String name, String values, Model model) {

    }

    @Override
    public InputManager getInputManager(String name) {
        return null;
    }
}
