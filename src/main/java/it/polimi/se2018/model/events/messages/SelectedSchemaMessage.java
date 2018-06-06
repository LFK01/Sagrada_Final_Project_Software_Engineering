package it.polimi.se2018.model.events.messages;

public class SelectedSchemaMessage extends Message {
    private String schemaCardName;
    public SelectedSchemaMessage(String sender, String recipient,String schemaCardName) {
        super(sender, recipient);
        this.schemaCardName = schemaCardName;
    }

    public String getSchemaCardName() {
        return schemaCardName;
    }
}
