package it.polimi.se2018.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChooseSchemaWindow {

    JFrame frame = new JFrame("Scegli una delle carte schema");
    private Container container = new Container();
    private ImageIcon schemaIcon1 = new ImageIcon("");
    JButton buttonSchema1 = new JButton(schemaIcon1);

    public ChooseSchemaWindow(){
        buttonSchema1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notify();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        container.setLayout(new GridLayout(2,2));
        container.add(buttonSchema1);
        container.add( new JButton(schemaIcon1));
        container.add( new JButton(schemaIcon1));
        container.add( new JButton(schemaIcon1));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.add(container);
        frame.setSize(800,1000);
        frame.setResizable(true);
        frame.setVisible(true);
    }

public static void main(String[] args){
        ChooseSchemaWindow window = new ChooseSchemaWindow();

}




}
