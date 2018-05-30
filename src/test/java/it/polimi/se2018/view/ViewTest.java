package it.polimi.se2018.view;

import it.polimi.se2018.controller.Controller;
import org.junit.Test;

public class ViewTest {

    @Test
    public void createPlayerTest(){
        View view = new View();
        Controller controller = new Controller();
        view.addObserver(controller);
        controller.getModel().addObserver(view);

        view.createPlayer();
        //assertEquals(false, view.getIsPlayerTurn());

    }

}