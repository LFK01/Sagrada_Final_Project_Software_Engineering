package it.polimi.se2018.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestModel {

    @Test
    public void Model(){
        Model testModel = null;
        try{
            testModel = new Model();
        }
        catch(NullPointerException e){
            fail();
        }
        assertNotNull(testModel);
        assertEquals(1, testModel.getTurnOfTheRound());
        assertNotEquals(2, testModel.getTurnOfTheRound());
        assertNotEquals(3, testModel.getTurnOfTheRound());
        assertEquals(0, testModel.getParticipantsNumber());
        assertNull(testModel.getGameBoard());
    }

    @Test
    public void checkDiceMove() {
    }

    @Test
    public void checkToolCardMove() {
    }

    @Test
    public void checkNoActionMove() {
    }

    @Test
    public void isPlayerTurn() {
    }

    @Test
    public void updateTurnOfTheRound() {
    }

    @Test
    public void addPlayer() {
    }

    @Test
    public void removePlayer() {
    }

}