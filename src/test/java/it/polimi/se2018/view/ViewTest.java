package it.polimi.se2018.view;

import it.polimi.se2018.controller.Controller;
import org.junit.Test;

import javax.swing.*;

import static javax.swing.JOptionPane.DEFAULT_OPTION;

public class ViewTest {

    @Test
    public void createPlayerTest(){
        JFrame parent = new JFrame();
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(parent, "Registration successfully completed!", "Success!", JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    }

}