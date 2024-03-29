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
        Dice dice = new Dice(Color.YELLOW, 4);
        dice.setValue(2);

        assertNotNull(dice);
        assertEquals(2, dice.getValue());
        assertNotEquals(5, dice.getValue());

    }

    @Test
    public void colorTest() {
        DiceBag  diceBag = new DiceBag();
        int j=0;
        for(int i = 0; i < diceBag.getDiceList().size(); i++){
            System.out.print(diceBag.getDiceList().get(i).toString());
            j++;
            if(j==9){
                System.out.print("\n");
                j=0;
            }
        }
    }
}