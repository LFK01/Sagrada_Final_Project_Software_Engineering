package it.polimi.se2018.model;

import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.exceptions.NoColorException;
import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.Cell;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCell {

    @Test
    public void Cell(){
        Cell cell = null;
        boolean correctExcpetioncCalling=false;
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
        catch(NoColorException e){
            correctExcpetioncCalling=true;
        }
        assertTrue(correctExcpetioncCalling);
    }

    @Test
    public void setAssignedDice() {
        boolean excpetionCalledCorrect=true;
        boolean erroreAssegnamento=true;
        boolean testPassed=false;
        //boolean value to check if all the exceptions in the test get caught
        Dice dadoProva1=null;
        Dice dadoProva2=null;
        dadoProva1 = new Dice(Color.BLUE, 1);
        dadoProva2 = new Dice(Color.GREEN, 2);
        Cell cellaProva1= new Cell(Color.BLUE, 1);
        //compatible w/ dado1, not compatible w/ dado2
        Cell cellaProva2 = new Cell(null, 1);
        //compatible w/ dado1, not compatible w/ dado2
        Cell cellaProva3 = new Cell(Color.BLUE, 0);
        //compatible w/ dado1, not compatible w/ dado2
        Cell cellaProva4 = new Cell(null, 0);
        //compatible w/ dado1, compatible w/ dado2
        Cell cellaProva5 = new Cell(Color.GREEN, 2);
        //not compatible w/ dado1, compatible w/ dado2
        Cell cellaProva6 = new Cell(Color.GREEN, 0);
        //not compatible w/ dado1, compatible w/ dado2
        Cell cellaProva7 = new Cell(null, 2);
        //not compatible w/ dado1, compatible w/ dado2
        try
        {
            cellaProva1.setAssignedDice(dadoProva1);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch(FullCellException e){
            fail();
        }
        try{
            cellaProva2.setAssignedDice(dadoProva1);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch(FullCellException e){
            fail();
        }
        try{
            cellaProva3.setAssignedDice(dadoProva1);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch(FullCellException e){
            fail();
        }
        try{
            cellaProva4.setAssignedDice(dadoProva1);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch(FullCellException e){
            fail();
        }
        try{
            cellaProva4.setAssignedDice(dadoProva2);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch(FullCellException e){
            excpetionCalledCorrect=true;
        }
        assertTrue(excpetionCalledCorrect);
        excpetionCalledCorrect=false;
        Dice thrownAwayDie = cellaProva4.removeDieFromCell();
        assertEquals(thrownAwayDie, dadoProva1);
        try{
            cellaProva4.setAssignedDice(dadoProva2);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch (FullCellException e){
            fail();
        }
        try{
            cellaProva5.setAssignedDice(dadoProva2);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch(FullCellException e){
            fail();
        }
        try{
            cellaProva6.setAssignedDice(dadoProva2);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch(FullCellException e){
            fail();
        }
        try{
            cellaProva7.setAssignedDice(dadoProva2);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }
        catch (FullCellException e){
            fail();
        }
        assertEquals(dadoProva1, cellaProva1.getAssignedDice());
        assertEquals(dadoProva1, cellaProva2.getAssignedDice());
        assertEquals(dadoProva1, cellaProva3.getAssignedDice());
        assertEquals(dadoProva2, cellaProva4.getAssignedDice());
        assertEquals(dadoProva2, cellaProva5.getAssignedDice());
        assertEquals(dadoProva2, cellaProva6.getAssignedDice());
        assertEquals(dadoProva2, cellaProva7.getAssignedDice());
        assertEquals(dadoProva1.getDiceColor(), cellaProva1.getAssignedDice().getDiceColor());
        assertEquals(dadoProva2.getDiceColor(), cellaProva4.getAssignedDice().getDiceColor());
        assertEquals(dadoProva1.getValue(), cellaProva1.getAssignedDice().getValue());
        assertEquals(dadoProva2.getValue(), cellaProva4.getAssignedDice().getValue());
        thrownAwayDie = cellaProva1.removeDieFromCell();
        thrownAwayDie = cellaProva2.removeDieFromCell();
        thrownAwayDie = null;
        try{
            cellaProva1.setAssignedDice(dadoProva2);
        }
        catch (RestrictionsNotRespectedException e){
            excpetionCalledCorrect = true;
        }
        catch(FullCellException e){
            fail();
        }
        assertTrue(excpetionCalledCorrect);
        thrownAwayDie = cellaProva1.removeDieFromCell();
        excpetionCalledCorrect = false;
        try{
            cellaProva2.setAssignedDice(dadoProva2);
        }
        catch (RestrictionsNotRespectedException e){
            excpetionCalledCorrect = true;
        }
        catch(FullCellException e){
            fail();
        }
        assertTrue(excpetionCalledCorrect);
    }

    @Test
    public void getCellColor(){
        boolean excpetionCalledCorrect = false;
        Color color1 = null;
        Cell cellaProva1 = new Cell(null, 0);
        try{
            color1 = cellaProva1.getCellColor();
        }
        catch(NoColorException e){
            excpetionCalledCorrect = true;
        }
        assertTrue(excpetionCalledCorrect);
        assertNull(color1);
        Cell cellaProva2 = new Cell(Color.RED, 0);
        Color color2 = null;
        try{
            color2 = cellaProva2.getCellColor();
        }
        catch (NoColorException e){
            fail();
        }
        assertNotNull(color2);
        assertEquals(Color.RED, color2);
    }
}