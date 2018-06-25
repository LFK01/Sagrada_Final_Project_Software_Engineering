package it.polimi.se2018.exceptions;

public class FullCellException extends Exception {
    public FullCellException() {
        super("Posizione già occupata");
    }
}
