package it.polimi.se2018.model;

import it.polimi.se2018.model.exceptions.InvalidColorException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the RoundDice class
 * @author Giorgia
 */

public class RoundDiceTest {


    /**
     * Tests the class constructor, throwing exceptions in case diceBag generates errors. It checks that the number of the dice extracted from the dice bag is correct
     * (must be number_of_players*2+1)
     * @throws InvalidColorException in case the dice bag creates dice with invalid color
     */
    @Test
    public void roundDiceTest() throws InvalidColorException {

       RoundDice roundDice = null;

       try {
           DiceBag diceBag = new DiceBag();
           roundDice = new RoundDice(4, diceBag, 6);
       } catch (NullPointerException e) {
           fail();
       }

       assertNotNull(roundDice);
       assertEquals(roundDice.getParticipantsNumber()*2+1, roundDice.getDiceList().size());

    }

    /**
     * Test to check that the dice extracted from the dice bag are the correct dice associated to the actual round
     * @throws InvalidColorException in case the dice bag creates dice with invalid color
     */
    @Test
    public void validExtractionTest() throws InvalidColorException {

        DiceBag diceBag = new DiceBag();
        RoundDice roundDice = new RoundDice(3, diceBag, 7);
        int n = 0;

        assertNotNull(diceBag);
        assertNotNull(roundDice);

        for (int i = (roundDice.getRound()-1)*(roundDice.getParticipantsNumber()*2+1); i < roundDice.getRound()*(roundDice.getParticipantsNumber()*2+1); i++) {
            assertEquals(diceBag.getDice(i), roundDice.getDice(n));
            n++;
        }

    }

}