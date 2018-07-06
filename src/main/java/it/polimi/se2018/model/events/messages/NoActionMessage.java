package it.polimi.se2018.model.events.messages;
import it.polimi.se2018.model.events.messages.Message;

/**
 * @author Giovanni
 * Message launched by the client to pass the turn
 */
public class NoActionMessage extends Message {

    public NoActionMessage(String sender, String recipient){
        super(sender,recipient);
    }

    public boolean isNoActionMove() {
        return true;
    }

}
