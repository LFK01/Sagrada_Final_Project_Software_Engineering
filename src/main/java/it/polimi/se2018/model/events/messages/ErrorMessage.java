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


    public boolean isError() {
        return true;
    }


    public boolean isNewRound() {
        return false;
    }

    @Override
    public String toString() {
        return message;
    }
}
