package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.view.comand_line.InputManager;

/**
 * @author Luciano
 */

public class PennelloPerEglomise extends AbstractToolCard {

    private static PennelloPerEglomise thisIstance;

    private PennelloPerEglomise() {
        super(5);
    }

    public static synchronized PennelloPerEglomise getThisInstance() {
        if(thisIstance==null){
            thisIstance= new PennelloPerEglomise();
        }
        return thisIstance;
    }

    @Override
    public void activateToolCard(String username, String name, String values, Model model) {

    }

    @Override
    public InputManager getInputManager(String name) {
        return null;
    }
}
