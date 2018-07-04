package it.polimi.se2018.model.game_equipment;

/**
 * @author giovanni
 * Class used to color the dice and cells of the schema cards
 */

public enum PaintingTool {

    ANSI_RED("\u001B[1m\u001B[31m"),
    ANSI_GREEN("\u001B[1m\u001B[32m"),
    ANSI_YELLOW("\u001B[1m\u001B[33m"),
    ANSI_BLUE("\u001B[1m\u001B[34m"),
    ANSI_PURPLE("\u001B[1m\u001B[35m");


    public static final String RESET = "\u001B[0m";
    public static final int NUMBER_OF_COLOR = 5;

    private String escape;
    PaintingTool(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }

}

