package it.polimi.se2018.model.events.messages;

/**
 * @author giovanni
 */

public class GameInitializationMessage extends Message {
    private String[] publicObjectiveCardsDescription=null;
    private String[] toolCardsDescription =null;
    private String roundTrack;

    public GameInitializationMessage(String sender, String recipient, String[] publicObjectiveCardsDescription, String[] toolCardsDescription,String roundTrack) {
        super(sender, recipient);
        this.publicObjectiveCardsDescription = publicObjectiveCardsDescription;
        this.toolCardsDescription = toolCardsDescription;
        this.roundTrack = roundTrack;


    }
    public String getPublicObjectiveCardsDescription(int index) {
        return publicObjectiveCardsDescription[index];
    }

    public String getToolCardsDescription(int index) {
        return toolCardsDescription[index];
    }


    public String getRoundTrack() {
        return roundTrack;
    }
}
