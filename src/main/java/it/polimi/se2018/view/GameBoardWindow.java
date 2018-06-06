package it.polimi.se2018.view;

import it.polimi.se2018.model.game_equipment.GameBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author giovanni
 */
public class GameBoardWindow {

JFrame frame = new JFrame("GameBoard");

    public GameBoardWindow(){
      frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setBounds(0,0,screenSize.width, screenSize.height);
      frame.setUndecorated(false);

        //aggiunge immagine a frame
        /*try {
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ImageIcon icon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurora Sagradis.jpg");
        ImageIcon icon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurorae Mangnificus.jpg");
        ImageIcon icon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Battlo.jpg");
        ImageIcon icon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Gravitas.jpg");
        JButton button1 = new JButton(icon1);
        JButton button2 = new JButton(icon2);
        JButton button3 = new JButton(icon3);
        JButton button4 = new JButton(icon4);

        ImageIcon iconTool1 = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\ToolCard\\Alesatore per Lamina di Rame.png");
        ImageIcon iconTool2 = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\ToolCard\\Alesatore per Lamina di Rame.png");
        ImageIcon iconTool3 = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\ToolCard\\Alesatore per Lamina di Rame.png");
        ImageIcon iconTool4 = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\ToolCard\\Alesatore per Lamina di Rame.png");
        ImageIcon iconTool5 = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\ToolCard\\Alesatore per Lamina di Rame.png");
        ImageIcon iconTool6 = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\ToolCard\\Alesatore per Lamina di Rame.png");

        ImageIcon iconTop1 = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\ToolCard\\Alesatore per Lamina di Rame.png");
        ImageIcon iconTop2 = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\ToolCard\\Alesatore per Lamina di Rame.png");

        JButton buttonTop1 = new JButton(iconTop1);
        JButton buttonTop2 = new JButton(iconTop2);

        JButton buttonTool1 = new JButton(iconTool1);
        JButton buttonTool2 = new JButton(iconTool2);
        JButton buttonTool3 = new JButton(iconTool3);
        JButton buttonTool4 = new JButton(iconTool4);
        JButton buttonTool5 = new JButton(iconTool5);
        JButton buttonTool6 = new JButton(iconTool6);

        Container centerContainer = new Container();
        Container downContainer = new Container();
        Container topContainer = new Container();
        centerContainer.setLayout(new FlowLayout());
        downContainer.setLayout(new FlowLayout());
        topContainer.setLayout(new FlowLayout());


        downContainer.add(button1);
        downContainer.add(button2);
        downContainer.add(button3);
        downContainer.add(button4);

        centerContainer.add(buttonTool1);
        centerContainer.add(buttonTool2);
        centerContainer.add(buttonTool3);
        centerContainer.add(buttonTool4);
        centerContainer.add(buttonTool5);
        centerContainer.add(buttonTool6);

        topContainer.add(buttonTop1);
        topContainer.add(buttonTop2);

        Container parentConteiner = new Container();
        parentConteiner.setLayout(new BorderLayout());
        parentConteiner.add(downContainer,BorderLayout.SOUTH);
        parentConteiner.add(centerContainer,BorderLayout.CENTER);
        parentConteiner.add(topContainer,BorderLayout.NORTH);


        frame.setResizable(false);
        frame.setContentPane(parentConteiner);
        //frame.pack();
        frame.setVisible(true);

    }
}
