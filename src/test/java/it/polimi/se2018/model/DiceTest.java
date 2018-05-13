package it.polimi.se2018.model;

import it.polimi.se2018.model.exceptions.InvalidColorException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the dice class
 * @author Giorgia
 */

public class DiceTest {

    /**
     * Constructor test. It checks that:
     * - the die is actually created (not null);
     * - the parameters are correctly assigned to the instance's attributes;
     * - the attributes inSchema, inTrack and inDraftPool are set to "false";
     * - the constructor correctly handles InvalidColorException.
     * @throws InvalidColorException in case the dice color is not valid
     */
    @Test
    public void diceTest() throws InvalidColorException {

        Dice dice = null;

        try{
            dice = new Dice(Color.BLUE, 6);
        } catch (NullPointerException e){
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
     * @throws InvalidColorException in case the dice color is not valid
     */
    @Test
    public void setValueTest() throws InvalidColorException {

        Dice dice = new Dice(Color.YELLOW, 5);
        dice.setValue(7);

        assertNotNull(dice);
        assertEquals(7, dice.getValue());
        assertNotEquals(5, dice.getValue());

    }

}