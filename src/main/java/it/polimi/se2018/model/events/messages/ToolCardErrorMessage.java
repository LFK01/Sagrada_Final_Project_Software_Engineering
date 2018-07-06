package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.view.comand_line.InputManager;

/**
 * @author Luciano
 * Message that warns the client that
 * he can not use the Tool card
 */
public class ToolCardErrorMessage extends Message {

    private String errorInformation;
    private String toolCardID;
    private InputManager inputManager;

    public ToolCardErrorMessage(String sender, String recipient, String toolCardID,
                                String errorInformation, InputManager inputManager) {
        super(sender, recipient);
        this.errorInformation = errorInformation;
        this.toolCardID = toolCardID;
        this.inputManager = inputManager;
    }

    public String getErrorInformation() {
        return errorInformation;
    }

    public String getToolCardID() {
        return toolCardID;
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
