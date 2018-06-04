package it.polimi.se2018.model;
/**
 * @author Giovanni
 * edited Luciano
 */

import it.polimi.se2018.model.game_equipment.GameBoard;
import it.polimi.se2018.model.game_equipment.Player;
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
        assertEquals(0, testModel.getTurnOfTheRound());
        assertNotEquals(2, testModel.getTurnOfTheRound());
        assertNotEquals(3, testModel.getTurnOfTheRound());
        assertEquals(0, testModel.getParticipantsNumber());
        assertNotNull(testModel.getGameBoard());
    }


    @Test
    public void doDiceMove(){

    }

    @Test
    public void isPlayerTurn() {
        Model model2Giocatori = new Model();
        model2Giocatori.addPlayer("p1");
        model2Giocatori.addPlayer("p2");
        assertTrue(model2Giocatori.isPlayerTurn(model2Giocatori.getPlayer(0)));
        model2Giocatori.updateTurnOfTheRound();
        assertFalse(model2Giocatori.isPlayerTurn(model2Giocatori.getPlayer(0)));
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
    }

    @Test
    public void testextractPublicObjectiveCards(){
        Model model = new Model();

        try{
            model.extractPublicObjectiveCards();
        }
        catch(NullPointerException e){
            fail();
        }














    }


}