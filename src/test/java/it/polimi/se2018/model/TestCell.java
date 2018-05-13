package it.polimi.se2018.model;

import it.polimi.se2018.model.exceptions.NoColorException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCell {

    @Test
    public void Cell(){
        Cell cell = null;
        try{
            cell = new Cell(null, 0);
        }
        catch(NullPointerException e){
            fail();
        }
        assertEquals(cell.getValue(), 0);
        assertTrue(cell.hasNoColorRestrictions());
        try{
            cell.getCellColor();
        }
        catch(NoColorException e){        }
        
    }
    @Test
    public void setAssignedDice() {
    }
}