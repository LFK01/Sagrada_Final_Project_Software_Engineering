package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.view.comand_line.InputManager;

/**
 * @author Luciano
 */

public class RigaInSughero extends AbstractToolCard {

    private static RigaInSughero thisInstance;

    private RigaInSughero() {
        super(8);
    }

    public static synchronized RigaInSughero getThisInstance(){
        if(thisInstance==null){
            thisInstance = new RigaInSughero();
        }
        return  thisInstance;
    }

    @Override
    public void activateToolCard(String username, String name, String values, Model model) {

    }

    @Override
    public InputManager getInputManager(String name) {
        return null;
    }
}
