package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.view.comand_line.InputManager;

/**
 * @author Luciano
 */

public class TenagliaARotelle extends AbstractToolCard {

    private static TenagliaARotelle thisInstance;

    private TenagliaARotelle() {
        super(12);
    }

    public static synchronized TenagliaARotelle getThisInstance(){
        if(thisInstance==null){
            thisInstance = new TenagliaARotelle();
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
