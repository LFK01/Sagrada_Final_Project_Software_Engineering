package it.polimi.se2018.controller;

import it.polimi.se2018.model.events.moves.NoActionMove;
import org.junit.Test;

public class ControllerTest {

    @Test
    public void extractionTest(){
        Controller  controller = new Controller();
        controller.getModel().extractToolCards();
        controller.getModel().extractPublicObjectiveCards();
        controller.getModel().addPlayer("lucio");
        controller.getModel().addPlayer("ciao");
        controller.getModel().setSchemaCardPlayer(0,"Kaleidoscopic/Dream");


    }

    @Test
    public void updateNoActionMoveTest(){
        Controller controller = new Controller();
        controller.getModel().addPlayer("p1");
        controller.getModel().addPlayer("p2");
        NoActionMove noActionMove = new NoActionMove("p1","server");
        controller.update(noActionMove);


    }
}