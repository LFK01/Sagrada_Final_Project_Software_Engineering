package it.polimi.se2018.model;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.player.Player;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author giovanni
 *
 */
public class TestChooseDiceMove {
    /**
     * Test initialization of testChooseDiceMove
     */
    @Test
    public void testChooseDiceMoveInitialization(){
        ChooseDiceMessage chooseDiceMessage = null;
        int row=2,col=1,draftPoolPos=0;
        Player player = null;
        try{
            chooseDiceMessage = new ChooseDiceMessage("p1", "server", draftPoolPos);
        }
        catch(NullPointerException e){
            fail();
        }
        assertNotNull(chooseDiceMessage);
    }

}
