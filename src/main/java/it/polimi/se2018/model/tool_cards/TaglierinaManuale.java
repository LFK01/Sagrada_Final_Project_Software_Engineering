package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.view.comand_line.InputManager;

/**
 * @author Luciano
 */

public class TaglierinaManuale extends AbstractToolCard {

    private static TaglierinaManuale thisInstance;

    private TaglierinaManuale() {
        super(10);
    }

    public static synchronized TaglierinaManuale getThisInstance(){
        if(thisInstance==null){
            thisInstance = new TaglierinaManuale();
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
