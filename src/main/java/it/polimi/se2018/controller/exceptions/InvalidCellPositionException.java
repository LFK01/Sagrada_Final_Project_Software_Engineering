package it.polimi.se2018.controller.exceptions;

/**
 * Exception to handle invalid input concerning the matrix indexes
 * @author Giorgia
 */

public class InvalidCellPositionException extends Exception {

    public InvalidCellPositionException() {
        super("Gli indici di posizione non sono validi.");
    }

}
