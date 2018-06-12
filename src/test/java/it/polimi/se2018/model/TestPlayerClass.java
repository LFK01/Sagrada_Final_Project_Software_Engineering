package it.polimi.se2018.model;
import static org.junit.Assert.*;
import it.polimi.se2018.model.player.Player;
import org.junit.Test;

/**
 * @author giovanni
 */


public class TestPlayerClass {
    @Test
    public void testplayerInitialization(){
        Player player = null;
        try {
            player = new Player("giovanni");
        }
        catch(NullPointerException e){
            fail();
        }

        assertEquals(0,player.getPoints());

    }


}
