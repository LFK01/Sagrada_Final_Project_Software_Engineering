package it.polimi.se2018.model;

import it.polimi.se2018.model.game_equipment.DiceBag;
import it.polimi.se2018.model.game_equipment.RoundDice;
import it.polimi.se2018.model.game_equipment.RoundTrack;
import org.junit.Test;

public class RoundTrackTest {

    @Test
    public void testRoundTRackInitialization(){
        DiceBag diceBag = new DiceBag();
        RoundDice roundDice = new RoundDice(4,diceBag,1);
        RoundTrack roundTrack = new RoundTrack();
        roundTrack.setRoundDice(roundDice,roundDice.getRound());

    }

}