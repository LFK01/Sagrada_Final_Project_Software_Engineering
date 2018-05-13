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
        assertEquals(0, cell.getValue());
        assertTrue(cell.hasNoColorRestrictions());
        try{
            cell.getCellColor();
        }
        catch(NoColorException e){}
    }

    @Test
    public void setAssignedDice() {
        //Dice dadoProva = new Dice(Color.BLUE, 1);
        Cell cellaProva = new Cell(Color.BLUE, 1);
        /*cellaProva.setAssignedDice(dadoProva);
        assertEquals(dadoProva, cellaProva.getAssignedDice());
        assertEquals(dadoProva.getDiceColor(), cellaProva.getAssignedDice().getDiceColor());
        assertEquals(dadoProva.getValue(), cellaProva.getAssignedDice().getValue());*/
    }
}