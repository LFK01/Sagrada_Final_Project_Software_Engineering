package it.polimi.se2018.view;
/**
 * @author Giovanni
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChooseConnectionWindow implements Runnable{
    @Override
    public void run() {

    }

    private JFrame frame = new JFrame("Quale connessione vuoi scegliere ?");
    private Container container = new Container();

    private ImageIcon iconRMI = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\various_images\\CatturaRMI.PNG");
    private ImageIcon iconSocket = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\various_images\\CatturaSocket.PNG");
    private ImageIcon iconComeBack = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\various_images\\comeBack2.jpg");
    private  JButton buttonRMI = new JButton(iconRMI);
    private JButton buttonComeBack =new JButton(iconComeBack);
    private JButton buttonSocket= new JButton(iconSocket);




    public ChooseConnectionWindow(Integer choice){
        buttonRMI.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                 setchoise1(choice);

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


        container.setLayout(new GridLayout(1,3));
        container.add(buttonRMI);
        container.add(buttonComeBack);
        container.add(buttonSocket);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.add(container);
        frame.setSize(550,750);
        frame.setResizable(false);
        frame.setVisible(true);

        while (choice==0);

    }

    public ChooseConnectionWindow(boolean param){
        buttonRMI.setIcon(iconRMI);
        buttonSocket.setIcon(iconSocket);
        container.setLayout(new GridLayout(1,2));
        container.add(buttonRMI);
        container.add(buttonSocket);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.add(container);
        frame.setSize(500,700);
        frame.setResizable(false);
        frame.setVisible(true);


    }

    public void setchoise1(Integer choice){
        choice = new Integer(1);
    }

}
