package it.polimi.se2018.model.events.messages;

/**
 * @author Giovanni
 * Message that sends the game board to all the clients
 */

public class SendGameboardMessage extends Message {
    private String gameboardInformation;

    public SendGameboardMessage(String sender, String recipient, String gameboardInformation) {
        super(sender, recipient);
        this.gameboardInformation = gameboardInformation;

    }

    public String getGameboardInformation(){return gameboardInformation;}
}
