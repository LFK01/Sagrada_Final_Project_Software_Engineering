package it.polimi.se2018.model.events.messages;

/**
 * @author giovanni
 */

public class GameInitializationMessage extends Message {
    private String[] publicObjectiveCardsDescription;
    private String[] toolCardsDescription;
    private String roundTrack;

    public GameInitializationMessage(String sender, String recipient, String[] publicObjectiveCardsDescription, String[] toolCardsDescription,String roundTrack) {
        super(sender, recipient);
        this.publicObjectiveCardsDescription = publicObjectiveCardsDescription;
        this.toolCardsDescription = toolCardsDescription;
        this.roundTrack = roundTrack;
    }

    public String[] getPublicObjectiveCardsDescription() {
        return publicObjectiveCardsDescription;
    }

    public String[] getToolCardsDescription() {
        return toolCardsDescription;
    }


    public String getRoundTrack() {
        return roundTrack;
    }
}
