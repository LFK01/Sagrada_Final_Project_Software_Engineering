package it.polimi.se2018.model.events.messages;

public class DiePlacementMessage extends Message{

    private String values;

    public DiePlacementMessage(String sender, String recipient,String values) {
        super(sender, recipient);
        this.values = values;
    }

    public String getValues(){
        return values;
    }
}
