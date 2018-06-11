package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.view.comand_line.InputManager;

/**
 * @author Luciano
 */

public class PennelloPerPastaSalda extends AbstractToolCard {

    private static PennelloPerPastaSalda thisInstance;

    private PennelloPerPastaSalda() {
        super(6);
    }

    public static synchronized PennelloPerPastaSalda getThisInstance() {
        if(thisInstance==null){
            thisInstance = new PennelloPerPastaSalda();
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
