package it.polimi.se2018.view;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.events.messages.RequestMessage;
import it.polimi.se2018.view.comand_line.InputManager;
import org.junit.Test;

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
    }

    @Test
    public void sendPrivateObjectiveCard(){
        View view = new View();
        Controller controller = new Controller();
        view.addObserver(controller);
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