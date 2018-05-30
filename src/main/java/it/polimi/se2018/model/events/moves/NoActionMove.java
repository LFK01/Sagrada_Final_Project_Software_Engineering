package it.polimi.se2018.model.events.moves;
import it.polimi.se2018.model.events.messages.Message;

/**
 * @author giovanni
 */
public class NoActionMove extends Message {
    //aggiorna il turno
    public NoActionMove(String sender,String recipient){
        super(sender,recipient);
    }


    public boolean isDiceMove() {
        return false;
    }


    public boolean isNoActionMove() {
        return true;
    }

}
