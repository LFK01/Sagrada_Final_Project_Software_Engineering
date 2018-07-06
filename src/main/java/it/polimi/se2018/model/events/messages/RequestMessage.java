package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.view.comand_line.InputManager;

/**
 * @author Luciano
 * Message to request the row and the column
 * in which to place the die in the parameter
 */
public class RequestMessage extends Message {

    private String values;

    private InputManager inputManager;

    public RequestMessage(String sender, String recipient, String values, InputManager inputManager) {
        super(sender, recipient);
        this.values = values;
        this.inputManager = inputManager;
    }

    public String getValues() {
        return values;
    }

    public InputManager getInputManager() {
        return inputManager;
    }


}
