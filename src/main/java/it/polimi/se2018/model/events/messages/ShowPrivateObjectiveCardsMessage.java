package it.polimi.se2018.model.events.messages;

/**
 * @author Giovanni
 * Message that sends private Objective cards to each player
 */
public class ShowPrivateObjectiveCardsMessage extends Message {
private String privateObjectiveCardColor;
private int playerNumber;

public ShowPrivateObjectiveCardsMessage(String sender, String recipient, String  privateObjectiveCardColor, int playerNumber) {
        super(sender, recipient);
        this.privateObjectiveCardColor = privateObjectiveCardColor;
        this.playerNumber = playerNumber;
    }
    public String getPrivateObjectiveCardColor(){
        return privateObjectiveCardColor;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
