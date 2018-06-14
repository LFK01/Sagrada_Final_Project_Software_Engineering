package it.polimi.se2018.model.events.messages;

/**
 * @author giovanni
 */

public class GameInitializationMessage extends Message {
    private String gameboardInformation;

    public GameInitializationMessage(String sender, String recipient, String gameboardInformation) {
        super(sender, recipient);
        this.gameboardInformation = gameboardInformation;

    }

    public String getGameboardInformation(){return gameboardInformation;}
}
