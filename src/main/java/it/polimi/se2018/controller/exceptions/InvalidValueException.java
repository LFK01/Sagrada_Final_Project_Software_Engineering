package it.polimi.se2018.controller.exceptions;

/**
 * Exception to handle an invalid dice value
 * @author Giorgia
 */

public class InvalidValueException extends Exception {

    public InvalidValueException() {
        super("Valore assegnato al dado non valido.");
    }

}
