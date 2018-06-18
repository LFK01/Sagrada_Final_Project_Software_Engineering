package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.view.View;
import org.junit.Test;

public class TestModifyDieValue {

    @Test
    public void doYourJobTest(){
        View view = new View();
        Controller controller = new Controller();
        view.addObserver(controller);
        controller.addObserver(view);
    }
}
