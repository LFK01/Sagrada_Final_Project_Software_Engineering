package it.polimi.se2018.model;
/**
 * @author Giovanni
 * edited Luciano
 */

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.*;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.view.View;
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
    public void doDiceMoveTest() throws RestrictionsNotRespectedException, FullCellException {
        Model model  = new Model();
        model.addPlayer("luca");
        model.addPlayer("giacomo");
        model.extractPublicObjectiveCards();
        model.extractToolCards();
        model.setSchemaCardPlayer(0,"Kaleidoscopic Dream");
        Dice dice1 = new Dice(Color.YELLOW,4);
        Dice dice2 = new Dice(Color.GREEN,5);
        Dice dice3 = new Dice(Color.RED,1);
        Dice dice4 = new Dice(Color.BLUE,2);

        model.getGameBoard().getRoundDice()[0] = new RoundDice(2,model.getGameBoard().getDiceBag(),0);
        model.getGameBoard().getRoundTrack().getRoundDice()[0].getDiceList().add(dice1);
        model.getGameBoard().getRoundTrack().getRoundDice()[0].getDiceList().add(dice2);
        model.getGameBoard().getRoundTrack().getRoundDice()[0].getDiceList().add(dice3);
        model.getGameBoard().getRoundTrack().getRoundDice()[0].getDiceList().add(dice4);

        SchemaCard schemaCard = new SchemaCard(1);
        ChooseDiceMove chooseDiceMove = new ChooseDiceMove("luca","model",1,1,1);
        //model.doDiceMove(chooseDiceMove);
        //model.placeDie(schemaCard,5,0,0);
        //model.placeDie(schemaCard,6,0,0);
        model.placeDie(schemaCard,5,1,4);
        model.placeDie(schemaCard,6,2,4);
        model.placeDie(schemaCard,7,1,3);

        try{
            model.placeDie(schemaCard,8,0,1);
        }catch(RestrictionsNotRespectedException e){
            fail();
        }


        /*try {
            model.placeDie(schemaCard, 5, 3, 4);
        }
        catch(RestrictionsNotRespectedException e){
            fail();
        }*/

        System.out.println(schemaCard.toString());

        assertEquals(Color.YELLOW,schemaCard.getCell(0,0).getAssignedDice().getDiceColor());
        //assertEquals(Color.BLUE,schemaCard.getCell(0,1).getAssignedDice().getDiceColor());
        //assertNotEquals(Color.RED,schemaCard.getCell(2,3).getAssignedDice().getDiceColor());





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

    @Test
    public  void sendSchemaTest(){
        Controller controller = new Controller();
        View view1 = new View();
        View view2 = new View();

        view1.setUsername("lucio");
        view2.setUsername("luca");
        view1.addObserver(controller);
        view2.addObserver(controller);
        controller.getModel().addObserver(view1);
        controller.getModel().addObserver(view2);
        controller.getModel().addPlayer("lucio");
        controller.getModel().addPlayer("luca");
        controller.getModel().addPlayer("ciao");
        //controller.getModel().extractToolCards();
        controller.getModel().extractPublicObjectiveCards();
        //controller.getModel().extractToolCards();
        controller.getModel().sendInitializationMessage();

        //controller.getModel().sendSchemaCard();
        //controller.getModel().extractRoundTrack();
    }

    @Test
    public void roundTracktest(){
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        model.extractRoundTrack();
        model.sendInitializationMessage();

    }

    @Test
    public void updateTurnOfTheRoundTest(){
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        model.updateTurnOfTheRound();
        model.updateTurnOfTheRound();
        model.updateTurnOfTheRound();
        assertEquals(0,model.getTurnOfTheRound());

    }

    @Test
    public void changeFirstPlayerTest(){
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        model.addPlayer("p3");
        model.changeFirstPlayer();
        assertEquals("p2",model.getParticipants().get(0).getName());
    }
    @Test
    public void updateRoundTest(){
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        model.addPlayer("p3");
        model.extractRoundTrack();
        model.updateRound();

        assertEquals(1,model.getRoundNumber());
    }

    @Test
    public void schemaIngameMessage(){
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("P2");
        model.extractRoundTrack();
        model.extractToolCards();
        model.extractPublicObjectiveCards();
        model.setSchemaCardPlayer(0,"Battlo");
        model.setSchemaCardPlayer(1,"Battlo");
        model.sendInitializationMessage();
    }


}