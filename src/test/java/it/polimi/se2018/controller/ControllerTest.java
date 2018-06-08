package it.polimi.se2018.controller;

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

}