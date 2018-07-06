package it.polimi.se2018.controller;

import it.polimi.se2018.model.events.messages.NoActionMessage;
import org.junit.Test;

public class ControllerTest {

    @Test
    public void extractionTest(){
        Controller  controller = new Controller();
    }

    @Test
    public void updateNoActionMoveTest(){
        Controller controller = new Controller();
        NoActionMessage noActionMessage = new NoActionMessage("p1","server");
    }
}