package it.polimi.se2018.model;

import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.DiceBag;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the dice class
 * @author Giorgia
 */

public class TestDice {

    /**
     * Constructor test. It checks that:
     * - the die is actually created (not null);
     * - the parameters are correctly assigned to the instance's attributes;
     * - the attributes inSchema, inTrack and inDraftPool are set to "false";
     */
    @Test
    public void diceTest() {

        Dice dice = null;

        try {
            dice = new Dice(Color.BLUE, 6);
        } catch (NullPointerException e) {
            fail();
        }

        assertNotNull(dice);
        assertEquals(6, dice.getValue());
        assertEquals(dice.getDiceColor(), Color.BLUE);
        assertFalse(dice.isInSchema());
        assertFalse(dice.isInTrack());
        assertFalse(dice.isInDraftPool());

    }

    /**
     * Test to check the Dice method setValue(int index). It checks that:
     * - the dice is actually created;
     * - the dice value is correctly modified.
     */
    @Test
    public void setValueTest() {

        Dice dice = new Dice(Color.YELLOW, 5);
        dice.setValue(7);

        assertNotNull(dice);
        assertEquals(7, dice.getValue());
        assertNotEquals(5, dice.getValue());

    }

    @Test
    public void colorTest() {
        //String redString =  PaintingTool.ANSI_RED.escape() + "Hello" + PaintingTool.ANSI_BLUE.escape() + "World" + PaintingTool.RESET;
        //System.out.println(redString)
       /* DiceBag  diceBag = new DiceBag();
        for(int i=0;i < diceBag.getDiceBag().size();i++){
            System.out.println(diceBag.getDiceBag().get(i).toString());
        }*/
    }
}