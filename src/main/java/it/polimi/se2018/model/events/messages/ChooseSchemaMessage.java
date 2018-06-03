package it.polimi.se2018.model.events.messages;

import java.util.ArrayList;

//messaggio che dal model va a tutte le view
public class ChooseSchemaMessage extends Message{
    private int schema1;
    private int schema2;

    public ChooseSchemaMessage(String sender, String recipient, int schema1,int schema2) {
        super(sender, recipient);
        this.schema1 = schema1;
        this.schema2 = schema2;
    }

    public int getSchema1() {
        return schema1;
    }

    public int getSchema2() {
        return schema2;
    }
}
