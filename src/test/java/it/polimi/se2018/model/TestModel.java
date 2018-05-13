package it.polimi.se2018.model;
/**
 * @author Giovanni
 * edited Luciano
 */

import it.polimi.se2018.controller.exceptions.NotEnoughFavorTokensException;
import org.junit.Test;

import java.util.ArrayList;

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


    @Test
    public void testHasADieNear(){
        GameBoard gameboard = null;
        ArrayList<Player> partecipants = new ArrayList();
        partecipants.add(new Player("luca"));
        partecipants.add(new Player("marco"));

        Model model = new Model();

        try{
            model.getGameBoard();
        }
        catch(NullPointerException e) {
            fail();
        }
    }
    @Test
    public void testUpdateFavorTokens(){
        Model model = null;
        int a=0,b=0;
        model.addPlayer("luca");
        model.addPlayer("lucio");
        try{
            model.updateFavorTokens(a,b);
        }
        catch(NotEnoughFavorTokensException e){
            System.err.println(e);
        }
    }
}