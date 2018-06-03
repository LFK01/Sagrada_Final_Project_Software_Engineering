package it.polimi.se2018.model.events.messages;

/**
 * @author Luciano
 */

public class ErrorMessage extends Message {

    private  String message;

    public ErrorMessage(String sender,String recipient, String message) {
        super(sender,recipient);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
