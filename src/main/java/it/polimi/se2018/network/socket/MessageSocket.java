package it.polimi.se2018.network.socket;

import java.io.Serializable;

public class MessageSocket implements Serializable {

    static final long serialVersionUID = 42L;

    private String message;

    public MessageSocket(String message) {
        this.message = message+"\n";
    }

    public String getMessage() {
        return message;
    }
}
