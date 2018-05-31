package it.polimi.se2018.model.events.messages;

import java.io.Serializable;

public abstract class Message implements Serializable{
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
