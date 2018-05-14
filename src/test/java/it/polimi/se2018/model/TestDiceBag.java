package it.polimi.se2018.model;

import it.polimi.se2018.model.exceptions.InvalidColorException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the dice bag
 * @author Giorgia
 */

public class TestDiceBag {

    /**
     * Tests the DiceBag constructor. It checks that the object is actually created and that there are 90 dice
     */
    @Test
    public void diceBagTest() throws InvalidColorException {

        DiceBag diceBag = null;

        try {
            diceBag = new DiceBag();
        } catch (NullPointerException e) {
            fail();
        }

        assertNotNull(diceBag);
        assertEquals(90, diceBag.getDiceBag().size());

    }

    /**
     * Tests that the red dice are 18
     */
    @Test
    public void testRedDiceNumber() throws InvalidColorException {

        DiceBag diceBag = new DiceBag();
        int n = 0;

        for(int i = 0; i < diceBag.getDiceBag().size(); i++) {

            if(diceBag.getDice(i).getDiceColor() == Color.RED)
                n++;

        }

        assertNotNull(diceBag);
        assertEquals(18, n);
        assertNotEquals(19, n);
        assertNotEquals(17, n);

    }

    /**
     * Tests that the yellow dice number are 18
     */
    @Test
    public void testYellowDiceNumber() throws InvalidColorException {

        DiceBag diceBag = new DiceBag();
        int n = 0;

        for(int i = 0; i < diceBag.getDiceBag().size(); i++) {

            if(diceBag.getDice(i).getDiceColor() == Color.YELLOW)
                n++;

        }

        assertNotNull(diceBag);
        assertEquals(18, n);
        assertNotEquals(19, n);
        assertNotEquals(17, n);

    }

    /**
     * Tests that the green dice are 18
     */
    @Test
    public void testGreenDiceNumber() throws InvalidColorException {

        DiceBag diceBag = new DiceBag();
        int n = 0;

        for(int i = 0; i < diceBag.getDiceBag().size(); i++) {

            if(diceBag.getDice(i).getDiceColor() == Color.GREEN)
                n++;

        }

        assertNotNull(diceBag);
        assertEquals(18, n);
        assertNotEquals(19, n);
        assertNotEquals(17, n);

    }

    /**
     * Tests that the blue dice are 18
     */
    @Test
    public void testBlueDiceNumber() throws InvalidColorException {

        DiceBag diceBag = new DiceBag();
        int n = 0;

        for(int i = 0; i < diceBag.getDiceBag().size(); i++) {

            if(diceBag.getDice(i).getDiceColor() == Color.BLUE)
                n++;

        }

        assertNotNull(diceBag);
        assertEquals(18, n);
        assertNotEquals(19, n);
        assertNotEquals(17, n);

    }

    /**
     * Tests that the purple dice are 18
     */
    @Test
    public void testPurpleDiceNumber() throws InvalidColorException {

        DiceBag diceBag = new DiceBag();
        int n = 0;

        for(int i = 0; i < diceBag.getDiceBag().size(); i++) {

            if(diceBag.getDice(i).getDiceColor() == Color.PURPLE)
                n++;

        }

        assertNotNull(diceBag);
        assertEquals(18, n);
        assertNotEquals(19, n);
        assertNotEquals(17, n);

    }

    /**
     * Tests that the dice value is between 1 and 6
     */
    @Test
    public void testDiceValue() throws InvalidColorException {

        DiceBag diceBag = new DiceBag();
        boolean invalidValue = false;

        for(int i = 0; i < diceBag.getDiceBag().size(); i++) {

            if(diceBag.getDice(i).getValue() > 6 || diceBag.getDice(i).getValue() < 1)
                invalidValue = true;

        }

        assertNotNull(diceBag);
        assertFalse(invalidValue);

    }

}
