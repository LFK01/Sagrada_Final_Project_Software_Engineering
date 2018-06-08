package it.polimi.se2018.model.game_equipment;

import java.util.Random;

/**
 * Dice class
 * @author Giorgia
 */
public class Dice {

    private Color diceColor;
    private int value;
    private boolean inSchema;
    private boolean inTrack;
    private boolean inDraftPool;
    private PaintingTool paintingTool;
    private String face;
    /**
     * Class constructor
     * @param color the dice color
     * @param value the dice value
     */
    public Dice(Color color, int value) {

        this.diceColor = color;
        this.value = value;
        this.face = faces[value-1];
        inSchema = false;
        inTrack = false;
        inDraftPool = false;

    }

    /**
     * Dice color getter
     * @return the dice color
     */
    public Color getDiceColor() {
        return diceColor;
    }

    /**
     * Dice value getter
     * @return the dice value
     */
    public int getValue() {
        return value;
    }

    /**
     * inSchema value getter
     * @return inSchema value (true if the dice is placed in a schema, false otherwise)
     */
    public boolean isInSchema() {
        return inSchema;
    }

    /**
     * inTrack value getter
     * @return inTrack value (true if the dice is in the round track, false otherwise)
     */
    public boolean isInTrack() {
        return inTrack;
    }

    /**
     * inDraftPool value getter
     * @return inDraftPool value (true if the dice is one of the dice of the round currently in the draft pool, false otherwise)
     */
    public boolean isInDraftPool() {
        return inDraftPool;
    }

    /**
     * Method to set the value of a dice when the player activates a tool card
     * @param value the new value (might be random in case the player is asked to roll the dice again,
     *              or a specific value in case the player is asked to change the dice value). The input is validated by
     *              the controller.
     */
    public void setValue(int value) {
            this.value = value;
    }


   public void setPaintingTool(PaintingTool paintingTool){
        this.paintingTool = paintingTool;
   }

   public PaintingTool getPaintingTool(){
        return paintingTool;
   }


    @Override
    public String toString() {
        switch (diceColor){
            case YELLOW:{
                return PaintingTool.ANSI_YELLOW.escape() + "[" + face + "]" + PaintingTool.RESET;
            }
            case RED:{
                return PaintingTool.ANSI_RED.escape() + "[" + face + "]" + PaintingTool.RESET;
            }
            case PURPLE:{
                return PaintingTool.ANSI_PURPLE.escape() + "[" + face + "]" + PaintingTool.RESET;
            }
            case GREEN:{
                return PaintingTool.ANSI_GREEN.escape() + "[" + face + "]" + PaintingTool.RESET;
            }
            case BLUE:{
                return PaintingTool.ANSI_BLUE.escape() + "[" + face + "]" + PaintingTool.RESET;
            }
            default:{
                return PaintingTool.RESET + "[" + face + "]" + PaintingTool.RESET;
            }
        }
    }


    public static final String[] faces ={
            /*"1",
            "2",
            "3",
            "4",
            "5",
            "6"*/
            "\u2680",
            "\u2681",
            "\u2682",
            "\u2683",
            "\u2684",
            "\u2685"
    };

    public void roll(){
        int count = faces.length;
        Random rand = new Random();
        int index = rand.nextInt(count);
        this.face = faces[index];
    }





}
