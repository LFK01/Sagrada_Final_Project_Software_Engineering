package it.polimi.se2018.model.events.messages;

public class SelectedSchemaMessage extends Message {
    private int schemaSelected ;
    public SelectedSchemaMessage(String sender, String recipient,int schemaSelected) {
        super(sender, recipient);
        this.schemaSelected = schemaSelected;
    }

    public int getSchemaSelected() {
        return schemaSelected;
    }
}
