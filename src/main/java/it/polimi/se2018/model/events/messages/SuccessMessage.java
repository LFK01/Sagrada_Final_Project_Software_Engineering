package it.polimi.se2018.model.events.messages;

public class SuccessMessage extends Message {

    String successMessage;

    public SuccessMessage(String sender, String recipient, String successMessage){
        super(sender,recipient);
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
