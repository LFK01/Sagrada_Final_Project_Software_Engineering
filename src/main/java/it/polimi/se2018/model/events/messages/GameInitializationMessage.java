package it.polimi.se2018.model.events.messages;

/**
 * @author giovanni
 */

public class GameInitializationMessage extends Message {
    private String[] publicObjectiveCardsDescription;
    private String[] toolCardsDescription;
    private String roundTrack;
    private String playingPlayerName;
    private String[] schemaInGame;

    public GameInitializationMessage(String sender, String recipient, String[] publicObjectiveCardsDescription,
                                     String[] toolCardsDescription, String[] schemaCardInGame,
                                     String roundTrack, String playingPlayerName) {
        super(sender, recipient);
        this.publicObjectiveCardsDescription = publicObjectiveCardsDescription;
        this.toolCardsDescription = toolCardsDescription;
        this.roundTrack = roundTrack;
        this.playingPlayerName = playingPlayerName;
        this.schemaInGame = schemaCardInGame;
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

    public String getPlayingPlayer() {
        return playingPlayerName;
    }

    public String[] getSchemaInGame() {
        return schemaInGame;
    }
}
