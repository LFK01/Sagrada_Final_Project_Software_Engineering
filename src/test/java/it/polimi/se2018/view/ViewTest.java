package it.polimi.se2018.view;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.events.messages.RequestMessage;
import it.polimi.se2018.view.comand_line.InputManager;
import org.junit.Test;

import javax.swing.*;

public class ViewTest {

    @Test
    public void createPlayerTest(){
       /* JFrame parent = new JFrame();
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(parent, "Registration successfully completed!", "Success!", JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        */
    }

    @Test
    public void selectSchemaCardTest(){
        View view = new View();
        Controller controller = new Controller();

        view.addObserver(controller);
        view.setUsername("luca");
        controller.getModel().addObserver(view);
        controller.getModel().addPlayer("luca");
        controller.getModel().addPlayer("username");
        controller.getModel().addPlayer("fabio");
        controller.getModel().addPlayer("biccu");
        //view.setUsername("paolo");
        //view.chooseSchemaWindow(1,1);

        int n=0;
        int sd=0;


    }

    @Test
    public void sendPrivateObjectiveCard(){
        View view = new View();
        Controller controller = new Controller();
        view.addObserver(controller);
        view.setUsername("luca");
        controller.getModel().addObserver(view);
        controller.getModel().addPlayer("luca");
        controller.getModel().addPlayer("username");
        controller.getModel().addPlayer("fabio");
        controller.getModel().addPlayer("biccu");

        //controller.getModel().sendPrivateObjectiveCard();

    }

    @Test
    public void toolUsageInputVisualization(){
        View view = new View();
        String requestDescription = "Choose a die from your schema card:\n" +
                "choose row: \n" +
                "choose column: \n" +
                "Choose a new position in your schema card for the selected die:\n" +
                "choose row: \n" +
                "choose column:";
        view.update(new RequestMessage("a", "b", "Alesatore Lamina di Rame", InputManager.INPUT_CHOOSE_MOVE));
    }
}