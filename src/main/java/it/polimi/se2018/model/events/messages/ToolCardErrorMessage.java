package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.view.comand_line.InputManager;

public class ToolCardErrorMessage extends Message {

    private String errorInformation;
    private String toolCardName;
    private InputManager inputManager;

    public ToolCardErrorMessage(String sender, String recipient, String toolCardName, String errorInformation,InputManager inputManager) {
        super(sender, recipient);
        this.errorInformation = errorInformation;
        this.toolCardName = toolCardName;
        this.inputManager = inputManager;
    }

    public String getErrorInformation() {
        return errorInformation;
    }

    public String getToolCardName() {
        return toolCardName;
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
