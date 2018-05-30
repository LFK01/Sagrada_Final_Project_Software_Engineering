package it.polimi.se2018.view;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoteViewTest {

    @Test
    public void connectionTest(){
        Player player = new Player("luca");
        View remoteView = null;
        Controller controller = new Controller();

        //remoteView = new RemoteView(player,controller.getModel());


        controller.getModel().addObserver(remoteView);
        remoteView.addObserver(controller);
        remoteView.createPlayer();

        assertEquals("giacomo",controller.getModel().getPlayer(0).getName());
    }
}