package it.polimi.se2018.model.events.messages;

/**
 * @author Luciano
 * Error message, based on the message parameter,
 * the client can recognize the type of error
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
