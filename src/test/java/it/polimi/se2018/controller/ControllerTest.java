package it.polimi.se2018.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void extractionTest(){
        Controller  controller = new Controller();
        controller.getModel().extractToolCards();
        controller.getModel().extractPublicObjectiveCards();



    }

}