package it.polimi.se2018.model.exceptions;

/**
 * Exception to handle an invalid dice color
 * @author Giorgia
 */

public class InvalidColorException extends Exception {

    public InvalidColorException() {
        super("Colore assegnato al dado non valido.");
    }

}
