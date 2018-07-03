package it.polimi.se2018.model;
/**
 * @author Giovanni
 * edited Luciano
 */

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.*;
import it.polimi.se2018.view.View;
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
        GameBoard gameBoard = null;
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        try{
            gameBoard = model.getGameBoard();
        }
        catch(NullPointerException e) {
            fail();
        }
        assertNotNull(gameBoard);
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
        view1.addObserver(controller);
        view2.addObserver(controller);
    }

    @Test
    public void roundTracktest(){
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        model.extractRoundTrack();
    }

    @Test
    public void updateTurnOfTheRoundTest(){
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        model.updateTurnOfTheRound();
        model.updateTurnOfTheRound();
        model.updateTurnOfTheRound();
        assertEquals(0, model.getTurnOfTheRound());
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
    }

     @Test
    public void countPointsTest() throws RestrictionsNotRespectedException, FullCellException {
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        model.extractPublicObjectiveCards();
        model.sendPrivateObjectiveCard();
        Dice dice1 = new Dice(Color.RED,2);
        Dice dice2 = new Dice(Color.GREEN,1);
        Dice dice3 = new Dice(Color.BLUE,3);
        Dice dice4 = new Dice(Color.YELLOW,6);
         FileParser parser = new FileParser();
         SchemaCard schemaCard1 = parser.createSchemaCardByNumber(Model.FOLDER_ADDRESS_SCHEMA_CARDS, 1);
         SchemaCard schemaCard2 = parser.createSchemaCardByNumber(Model.FOLDER_ADDRESS_SCHEMA_CARDS, 14);

        schemaCard2.placeDie(dice2,3,0,false,false,false);
        schemaCard2.placeDie(dice1,2,0,false,false,false);
         schemaCard2.placeDie(dice3,1,0,false,false,false);
         schemaCard2.placeDie(dice4,0,0,false,false,false);
        model.getParticipants().get(0).setSchemaCard(schemaCard1);
        model.getParticipants().get(1).setSchemaCard(schemaCard2);

        model.countPoints();

     }
     @Test
     public void extractPublicObjectiveCardsTest(){
        Model model = new Model();

        model.extractPublicObjectiveCards();
        for(int i =0; i<3;i++){
            System.out.println(model.getGameBoard().getPublicObjectiveCardName(i));
            System.out.println(model.getGameBoard().getPublicObjectiveCardDescription(i));
        }
    }

}