package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.view.comand_line.InputManager;

public class RequestMessage extends Message {

    private String toolCardName;

    private InputManager inputManager;

    public RequestMessage(String sender, String recipient, String toolCardName, InputManager inputManager) {
        super(sender, recipient);
        this.toolCardName = toolCardName;
        this.inputManager = inputManager;
    }

    public String getToolCardName() {
        return toolCardName;
    }

    public InputManager getInputManager() {
        return inputManager;
    }


}
