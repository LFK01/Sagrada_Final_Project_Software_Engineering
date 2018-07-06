package it.polimi.se2018.model.events.messages;

/**
 * @author Giovanni
 * Message containing the cards to choose from
 */

//messaggio che dal model va a tutte le view
public class ChooseSchemaMessage extends Message{
    String[] schemaCards;

    public ChooseSchemaMessage(String sender, String recipient,String[] schemaCards ) {
        super(sender, recipient);
        this.schemaCards = schemaCards;
    }

    public String getSchemaCards(int position) {
        return schemaCards[position];
    }
}