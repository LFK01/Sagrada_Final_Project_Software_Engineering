package it.polimi.se2018.view;

import it.polimi.se2018.controller.Controller;
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



}