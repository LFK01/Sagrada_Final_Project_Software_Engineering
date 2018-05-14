package it.polimi.se2018.model;

import it.polimi.se2018.controller.exceptions.InvalidCellPositionException;
import it.polimi.se2018.model.exceptions.InvalidColorException;
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
        boolean erroreAssegnamento=true;
        Dice dadoProva1=null;
        Dice dadoProva2=null;
        while(erroreAssegnamento){
            try{
                dadoProva1 = new Dice(Color.BLUE, 1);
                erroreAssegnamento=false;
            }
            catch(InvalidColorException e){
                erroreAssegnamento=true;
            }
        }
        Cell cellaProva1= new Cell(Color.BLUE, 1);
        Cell cellaProva2 = new Cell(null, 1);
        Cell cellaProva3 = new Cell(Color.BLUE, 0);
        Cell cellaProva4 = new Cell(null, 0);
        Cell cellaProva5 = new Cell(Color.GREEN, 2);
        Cell cellaProva6 = new Cell(Color.GREEN, 1);
        Cell cellaProva7 = new Cell(null, 2);
        /*try{
            cellaProva1.setAssignedDice(dadoProva1);
        }
        catch(InvalidCellPositionException e){
            fail();
        }
        try{
            cellaProva2.setAssignedDice(dadoProva1);
        }
        catch(InvalidCellPositionException e){
            fail();
        }
        try{
            cellaProva3.setAssignedDice(dadoProva1);
        }
        catch(InvalidCellPositionException e){
            fail();
        }
        try{
            cellaProva4.setAssignedDice(dadoProva1);
        }
        catch(InvalidCellPositionException e){
            fail();
        }
        try{
            cellaProva5.setAssignedDice(dadoProva1);
        }
        catch(InvalidCellPositionException e){
            fail();
        }
        try{
            cellaProva6.setAssignedDice(dadoProva1);
        }
        catch(InvalidCellPositionException e){
            fail();
        }
        try{
            cellaProva7.setAssignedDice(dadoProva1);
        }
        catch(InvalidCellPositionException e){
            fail();
        }*/
        assertEquals(dadoProva1, cellaProva1.getAssignedDice());
        assertEquals(dadoProva1.getDiceColor(), cellaProva1.getAssignedDice().getDiceColor());
        assertEquals(dadoProva1.getValue(), cellaProva1.getAssignedDice().getValue());
    }
}