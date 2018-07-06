package it.polimi.se2018.model.events.messages;

import java.io.Serializable;

/**
 * @author giovanni
 * Our project communicates through messages
 * that all share this same abstract type
 */

public abstract class Message implements Serializable{

    private String sender;
    private String recipient;

    /**
     * Initialization of the message
     * @param sender who sends the message
     * @param recipient who will receive it
     */
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
