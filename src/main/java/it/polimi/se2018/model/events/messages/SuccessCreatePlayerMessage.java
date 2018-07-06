package it.polimi.se2018.model.events.messages;

/**
 * @author @Luciano
 * Message that alerts the client of
 * the correct creation of the player

 */
public class SuccessCreatePlayerMessage extends Message {

    public SuccessCreatePlayerMessage(String sender, String recipient) {
        super(sender, recipient);
    }
}
