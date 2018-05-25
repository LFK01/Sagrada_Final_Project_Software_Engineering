package it.polimi.se2018.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoundTrackTest {

    @Test
    public void testRoundTRackInitialization(){
        DiceBag diceBag = new DiceBag();
        RoundDice roundDice = new RoundDice(4,diceBag,1);
        RoundTrack roundTrack = new RoundTrack();
        roundTrack.setRoundDice(roundDice,roundDice.getRound());

    }

}