package it.polimi.se2018.model;

import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.DiceBag;
import it.polimi.se2018.model.game_equipment.RoundDice;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Giovanni
 */

public class TestRoundDice {

    @Test
    public void roundDiceTest() {

       RoundDice roundDice = null;

       try {
           DiceBag diceBag = new DiceBag();
           roundDice = new RoundDice(1,diceBag);
       } catch (NullPointerException e) {
           fail();
       }

       assertNotNull(roundDice);

    }

    @Test
    public void removeDieFromDiceListTest(){
        DiceBag dicebag = new DiceBag();
        Dice dice1 = new Dice(Color.BLUE,2);
        dicebag.getDiceList().add(dice1);
        RoundDice roundDice = new RoundDice(4,dicebag);
        roundDice.setDice(dice1,8);
        Dice dice2 = roundDice.getDice(8);
        roundDice.toString();
        assertEquals(2,dice2.getValue());

    }

}