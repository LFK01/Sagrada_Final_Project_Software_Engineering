package it.polimi.se2018.model.events.messages;

public abstract class Message {
    private String sender;
    private String recipient;

    public Message(String sender,String recipient){
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }
}
