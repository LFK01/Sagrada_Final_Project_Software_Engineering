package it.polimi.se2018.model.events.messages;

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
