package it.polimi.se2018.model.events.messages;

public class ShowPrivateObjectiveCardsMessage extends Message {
private String privateObjectiveCardColor;

public ShowPrivateObjectiveCardsMessage(String sender, String recipient, String  privateObjectiveCardColor) {
        super(sender, recipient);
        this.privateObjectiveCardColor = privateObjectiveCardColor;

    }
    public String getPrivateObjectiveCardColor(){
        return privateObjectiveCardColor;
    }

}
