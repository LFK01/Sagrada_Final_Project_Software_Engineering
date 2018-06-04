package it.polimi.se2018.model;
import static org.junit.Assert.*;

import it.polimi.se2018.model.game_equipment.PointTrack;
import org.junit.Test;

/**
 * @author giovanni
 */

public class TestPointTrack {
    @Test
    public void testPointTrackInitialization(){
        PointTrack pointTrack = null;
        int n =2;
        try{
            pointTrack = new PointTrack(n);

        }
        catch(NullPointerException e){
            fail();
        }
    assertEquals(2,pointTrack.getPlayerPoints().length);
    }

}
