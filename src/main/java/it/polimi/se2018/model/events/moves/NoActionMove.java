package it.polimi.se2018.model.events.moves;
import it.polimi.se2018.model.events.messages.Message;

/**
 * @author Giovanni
 * Message launched by the client to pass the turn
 */
public class NoActionMove extends Message {

    public NoActionMove(String sender,String recipient){
        super(sender,recipient);
    }

    public boolean isNoActionMove() {
        return true;
    }

}
