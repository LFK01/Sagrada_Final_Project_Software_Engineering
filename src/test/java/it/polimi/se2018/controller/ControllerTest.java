package it.polimi.se2018.controller;

import it.polimi.se2018.model.events.moves.NoActionMove;
import org.junit.Test;

public class ControllerTest {

    @Test
    public void extractionTest(){
        Controller  controller = new Controller();
    }

    @Test
    public void updateNoActionMoveTest(){
        Controller controller = new Controller();
        NoActionMove noActionMove = new NoActionMove("p1","server");
    }
}