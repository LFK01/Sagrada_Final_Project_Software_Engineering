package it.polimi.se2018.model;
/**
 * @author Giovanni
 * edited Luciano
 */

import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.exceptions.NotEnoughFavorTokensException;
import it.polimi.se2018.model.exceptions.PlayerNumberExceededException;
import it.polimi.se2018.model.exceptions.SinglePlayerException;
import org.junit.After;
import org.junit.Before;
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
        try{
            model2Giocatori.addPlayer("p1");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model2Giocatori.addPlayer("p2");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        assertTrue(model2Giocatori.isPlayerTurn(model2Giocatori.getPlayer(0)));
        model2Giocatori.updateTurnOfTheRound();
        assertFalse(model2Giocatori.isPlayerTurn(model2Giocatori.getPlayer(0)));
    }

    @Test
    public void updateTurnOfTheRound() {
        Model model4Giocatori = new Model();
        try{
            model4Giocatori.addPlayer("p1");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model4Giocatori.addPlayer("p2");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model4Giocatori.addPlayer("p3");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model4Giocatori.addPlayer("p4");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        assertEquals(0, model4Giocatori.getTurnOfTheRound());
        assertTrue(model4Giocatori.isFirstDraftOfDice());
        model4Giocatori.updateTurnOfTheRound();
        assertEquals(1, model4Giocatori.getTurnOfTheRound());
        assertNotEquals(0, model4Giocatori.getTurnOfTheRound());
        assertTrue(model4Giocatori.isFirstDraftOfDice());
        model4Giocatori.updateTurnOfTheRound();
        assertEquals(2, model4Giocatori.getTurnOfTheRound());
        assertTrue(model4Giocatori.isFirstDraftOfDice());
        model4Giocatori.updateTurnOfTheRound();
        assertEquals(3, model4Giocatori.getTurnOfTheRound());
        assertTrue(model4Giocatori.isFirstDraftOfDice());
        model4Giocatori.updateTurnOfTheRound();
        assertEquals(3, model4Giocatori.getTurnOfTheRound());
        assertFalse(model4Giocatori.isFirstDraftOfDice());
        model4Giocatori.updateTurnOfTheRound();
        assertEquals(2, model4Giocatori.getTurnOfTheRound());
        assertFalse(model4Giocatori.isFirstDraftOfDice());
        model4Giocatori.updateTurnOfTheRound();
        assertEquals(1, model4Giocatori.getTurnOfTheRound());
        assertFalse(model4Giocatori.isFirstDraftOfDice());
        model4Giocatori.updateTurnOfTheRound();
        assertEquals(0, model4Giocatori.getTurnOfTheRound());
        assertFalse(model4Giocatori.isFirstDraftOfDice());
    }

    @Test
    public void addPlayer() {
        Model model = new Model();
        boolean excpetionCalledCorrect = false;
        try{
            model.addPlayer("p1");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model.addPlayer("p2");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model.addPlayer("p3");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model.addPlayer("p4");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model.addPlayer("p5");
        }
        catch(PlayerNumberExceededException e){
                excpetionCalledCorrect = true;
        }
        assertTrue(excpetionCalledCorrect);
    }

    @Test
    public void removePlayer() {
        Model model = new Model();
        boolean excpetionCalledCorrect = false;
        try{
            model.addPlayer("p1");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model.removePlayer(model.getPlayer(0));
        }
        catch (SinglePlayerException e){
            excpetionCalledCorrect = true;
        }
        assertTrue(excpetionCalledCorrect);
        try{
            model.addPlayer("p2");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        try{
            model.removePlayer(model.getPlayer(1));
        }
        catch(SinglePlayerException e){
            fail();
        }
        assertEquals(model.getParticipantsNumber(), 1);
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
        Model model = new Model();
        try{
            model.addPlayer("p1");
        }
        catch(PlayerNumberExceededException e){
            fail();
        }
        model.extractToolCards();
        try{
            model.addPlayer("p2");
        }
        catch(PlayerNumberExceededException e) {
            fail();
        }
        model.getPlayer(0).setSchemaCard(new SchemaCard(1));
        model.updateFavorTokens(0, 0);
        assertEquals((new SchemaCard(1).getDifficultyLevel())-1, model.getPlayer(0).getFavorTokens());
        model.getGameBoard().getToolCard(0).isBeingUsedForTheFirstTime();
        model.updateFavorTokens(0,0);
        assertEquals((new SchemaCard(1).getDifficultyLevel())-3, model.getPlayer(0).getFavorTokens());
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