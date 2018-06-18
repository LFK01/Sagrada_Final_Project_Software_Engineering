package it.polimi.se2018.exceptions;

/**
 * Exception thrown when someone tries to access the Color field
 * of a cell that doesn't have color restrictions
 * @author Luciano
 */
public class NoColorException  extends Exception{

    /**
     * Constructor method
     */
    public NoColorException() {
        super("Cell doesn't have any color restriction.");
    }
}
