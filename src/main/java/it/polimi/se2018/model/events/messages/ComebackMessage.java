package it.polimi.se2018.model.events.messages;

/**
 * @Luciano
 * Message sent by a client to reconnect to the game
 */
public class ComebackMessage extends Message {

    private String username;

    public ComebackMessage(String sender, String recipient, String oldUsername) {
        super(sender, recipient);
        username = oldUsername;
    }

    public String getUsername() {
        return username;
    }
}
