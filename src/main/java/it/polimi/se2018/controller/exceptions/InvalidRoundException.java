package it.polimi.se2018.controller.exceptions;

/**
 * Exception in case the round number exceeds 10 (under review)
 * @author Giorgia
 * @deprecated (might be useless)
 */

public class InvalidRoundException extends Exception {

    public InvalidRoundException() {
        super("La partita al decimo turno dovrebbe essere terminata.");
    }

}
