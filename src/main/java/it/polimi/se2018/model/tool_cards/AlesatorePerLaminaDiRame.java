package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.view.comand_line.InputManager;

/**
 * @author Luciano
 */

public class AlesatorePerLaminaDiRame extends AbstractToolCard {

    private static AlesatorePerLaminaDiRame thisInstance;

    private AlesatorePerLaminaDiRame() {
        super(1);
    }

    public static synchronized AlesatorePerLaminaDiRame getThisInstance(){
        if(thisInstance==null){
            thisInstance= new AlesatorePerLaminaDiRame();
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