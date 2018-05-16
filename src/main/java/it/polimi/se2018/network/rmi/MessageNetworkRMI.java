package it.polimi.se2018.network.rmi;
import java.io.Serializable;

/**
 * @author Giovanni
 */

public class MessageNetworkRMI implements Serializable {
    static final long serialVersionUID = 43L;
    private String message;

    public MessageNetworkRMI(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
