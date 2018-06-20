package it.polimi.se2018.exceptions;

/**
 * Exception handling invalid input concerning the dice choice in the draft pool
 * @author Giorgia
 */

public class InvalidDraftPoolPosException extends Exception {

    public InvalidDraftPoolPosException() {
        super("Indice del dado scelto non valido.");
    }

}
