package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.view.comand_line.InputManager;

public class TaglierinaCircolare extends AbstractToolCard {

    private static TaglierinaCircolare thisInstance;

    public TaglierinaCircolare() {
        super(9);
    }

    public static TaglierinaCircolare getThisInstance(){
        if(thisInstance==null){
            thisInstance = new TaglierinaCircolare();
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
