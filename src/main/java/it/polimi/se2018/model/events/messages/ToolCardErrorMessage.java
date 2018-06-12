package it.polimi.se2018.model.events.messages;

public class ToolCardErrorMessage extends Message {

    private String errorInformation;
    private String toolCardName;

    public ToolCardErrorMessage(String sender, String recipient, String toolCardName, String errorInformation) {
        super(sender, recipient);
    }

    public String getErrorInformation() {
        return errorInformation;
    }

    public String getToolCardName() {
        return toolCardName;
    }
}
