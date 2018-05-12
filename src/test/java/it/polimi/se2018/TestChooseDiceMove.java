package it.polimi.se2018;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestChooseDiceMove {
    @Test
    public void testChooseDiceMoveInitialization(){
        ChooseDiceMove chooseDiceMove = null;
        int row=2,col=1,draftPoolPos=0;
        Player player = null;
        try{
            chooseDiceMove = new ChooseDiceMove(draftPoolPos,row,col,player);
        }
        catch(NullPointerException e){
            fail();
        }
        assertEquals(2,chooseDiceMove.getRow());
    }

}
