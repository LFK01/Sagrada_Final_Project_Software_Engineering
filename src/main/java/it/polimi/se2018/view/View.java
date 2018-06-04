package it.polimi.se2018.view;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author giovanni
 */
public class View extends Observable implements Observer{

    protected String username;
    private boolean isPlayerTurn;
    private Scanner scanner;
    boolean windowCreated = true;
    private int choice = 0;

    /**
     * Initializes view
     */
    public View(){
        scanner = new Scanner(new InputStreamReader(System.in));
    }

    public boolean IsPlayerTurn(){
        return this.isPlayerTurn;
    }

    public void setFalsePlayerTurn() {
        isPlayerTurn =false;
    }

    /**
     *method that throws a message to controller with the description of the move
     * @param draftPoolPos  a class that contains dice in the game
     * @param row   it serves for the position of the die
     * @param col   it serves for the position of the die
     */
    public void handleDiceMove(int draftPoolPos ,int row,int col){
        notifyObservers(new ChooseDiceMove(username, "server", draftPoolPos, row, col));
    }

    /**
     * shows up a login window where the user can choose her/his name
     */
    public void createPlayer(){
        //TODO when called by socket connection we have to check player number is lower than 4
        /*JFrame frameCreatePlayer = new JFrame("New Player");
        Container containerCreatePlayer = new Container();
        GridLayout layourCreatePlayer = new GridLayout(3,1);

        frameCreatePlayer.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameCreatePlayer.setSize(300, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frameCreatePlayer.setLocation(dim.width/2-frameCreatePlayer.getSize().width/2, dim.height/2-frameCreatePlayer.getSize().height/2);
        frameCreatePlayer.add(containerCreatePlayer);
        containerCreatePlayer.setLayout(layourCreatePlayer);

        JLabel instructionLabel = new JLabel("Username:", JLabel.LEFT);
        JTextField usernameTextField = new JTextField("username", JTextField.CENTER);
        usernameTextField.setEnabled(true);
        usernameTextField.setFocusable(true);

        usernameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    username = usernameTextField.getText();
                    frameCreatePlayer.dispose();
                    setChanged();
                    notifyObservers(new CreatePlayerMessage(username, "server", username));
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {            }
            @Override
            public void keyReleased(KeyEvent e) {            }
        });

        usernameTextField.addMouseListener(new MouseListener() {
            boolean firstPressed = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!firstPressed){
                    firstPressed=true;
                    usernameTextField.setText("");
                }}
            @Override
            public void mousePressed(MouseEvent e) {            }
            @Override
            public void mouseReleased(MouseEvent e) {            }
            @Override
            public void mouseEntered(MouseEvent e) {            }
            @Override
            public void mouseExited(MouseEvent e) {            }
        });

        JButton confirmButton = new JButton("OK");
        confirmButton.setEnabled(true);

        confirmButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                username = usernameTextField.getText();
                frameCreatePlayer.dispose();
                setChanged();
                notifyObservers(new CreatePlayerMessage(username, "server", username));
            }
            @Override
            public void mousePressed(MouseEvent e) {           }
            @Override
            public void mouseReleased(MouseEvent e) {            }
            @Override
            public void mouseEntered(MouseEvent e) {            }
            @Override
            public void mouseExited(MouseEvent e) {            }
        });

        containerCreatePlayer.add(instructionLabel);
        containerCreatePlayer.add(usernameTextField);
        containerCreatePlayer.add(confirmButton);

        frameCreatePlayer.setVisible(true);*/
        System.out.print("New username: ");
        username = scanner.nextLine();
        setChanged();
        notifyObservers(new CreatePlayerMessage(username, "server", username));
    }

    public int demandConnectionType() {
        JFrame frameDemandConnection = new JFrame("Parent Window");
        Object[] options = {"RMI", "Socket"};
        int n = JOptionPane.showOptionDialog(frameDemandConnection, "Choose connection:", "Connection", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        if(n == JOptionPane.YES_OPTION){
            frameDemandConnection.dispose();
            return 1;
        }else{
            frameDemandConnection.dispose();
            return 2;
        }
    }

    @Override
    public void update(Observable o, Object message){
        try{
            Method updateView = this.getClass().getDeclaredMethod("updateView", message.getClass());
            updateView.invoke(this, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void updateView(SuccessCreatePlayerMessage successCreatePlayerMessage){
        if(successCreatePlayerMessage.getRecipient().equals(username)){
            System.out.println("Successful  login, new username: " + successCreatePlayerMessage.getRecipient());
        }
    }

    private void updateView(ErrorMessage errorMessage){
        if(errorMessage.getRecipient().equals(username)){
            if(errorMessage.toString().equals("NotValidUsername")){
                System.out.println("Username not available!");
                this.createPlayer();
            }
            if(errorMessage.toString().equals("PlayerNumberExceeded")){
                //TODO waiting lobby
                System.out.print("Impossibile connettersi");
            }
            if(errorMessage.toString().equals("NotEnoughPlayer")){
                System.out.println("Minimum players number not reached.");
            }
        }
    }
    private void updateView(ChooseSchemaMessage message){
        if(username.equals(message.getRecipient())){
            chooseSchemaWindow(message.getSchema1(),message.getSchema2());
        }

    }

    public void playerNumberExceededDialog() {
        JFrame framePlayerNumberExceeded = new JFrame();
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(framePlayerNumberExceeded, "Connection Failed", "Player number limit already reached, unable to add another player", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
    }

    public static void showSuccessMessage(JFrame parent){
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(parent, "Registration successfully completed!", "Success!", JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    public int chooseConnectionWindow() {

            if (windowCreated) {

                JFrame frame = new JFrame("Quale connessione vuoi scegliere ?");
                Container container = new Container();
                ImageIcon iconRMI = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\CatturaRMI.PNG");
                ImageIcon iconSocket = new ImageIcon("src\\main\\java\\it\\polimi\\se2018\\view\\CatturaSocket.PNG");
                ImageIcon iconComeBack = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\comeBack2.jpg");
                JButton buttonRMI = new JButton(iconRMI);
                JButton buttonComeBack = new JButton(iconComeBack);
                JButton buttonSocket = new JButton(iconSocket);
                buttonRMI.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        setChoice();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        setChoice();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        setChoice();

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setChoice();

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setChoice();

                    }
                });

                container.setLayout(new GridLayout(1, 3));
                container.add(buttonRMI);
                container.add(buttonComeBack);
                container.add(buttonSocket);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
                frame.add(container);
                frame.setSize(550, 750);
                frame.setResizable(false);
                frame.setVisible(true);
                windowCreated = false;

            }
        return choice;
    }


    public void setChoice(){
        choice = 1;
    }




    public void chooseSchemaWindow(int schema1,int schema2) {
        /*JFrame frame = new JFrame("Scegli una delle carte schema");
        Container container = new Container();
        container.setLayout(new GridLayout(2,2));
        JButton buttonSchema1 = null;
        JButton buttonSchema2 = null;
        JButton buttonSchema3 = null;
        JButton buttonSchema4 = null;
        ImageIcon schemaIcon1;
        ImageIcon schemaIcon2;
        ImageIcon schemaIcon3;
        ImageIcon schemaIcon4;

        int selected1 = 0;
        int selected2 = 0;
        int selected3 = 0;
        int selected4 = 0;

         switch(schema1) {

            case 1:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Kaleidoscopic Dream.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Firmitas.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;

                break;
            case 2:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Fractal Drops.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Ripples Of Light.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 1;
                break;
            case 3:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Lux Mundi.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Lux Astram.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1=1;
                selected2 =2;
                break;
            case 4:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Gravitas.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Water Of Life.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 5:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Sun Catcher.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Shadow Thief.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 6:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurorae Mangnificus.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurora Sagradis.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 7:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Symphony of Light.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Virtus.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 8:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Firelight.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Sun's Glory.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 9:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Battlo.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Bellesguard.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                break;
            case 10:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Fulgor del Cielo.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Luz Celestial.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
            case 11:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Chromatic Splendor.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Comitas.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                break;
            case 12:
                schemaIcon1 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Via Lux.jpg");
                schemaIcon2 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Industria.jpg");
                buttonSchema1 = new JButton(schemaIcon1);
                buttonSchema2 = new JButton(schemaIcon2);
                selected1 = 1;
                selected2 = 2;
                break;
        }

        switch(schema2){
            case 1:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Kaleidoscopic Dream.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Firmitas.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);


                break;
            case 2:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Fractal Drops.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Ripples Of Light.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 3:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Lux Mundi.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Lux Astram.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 4:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Gravitas.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Water Of Life.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 5:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Sun Catcher.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Shadow Thief.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 6:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurorae Mangnificus.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Aurora Sagradis.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 7:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Symphony of Light.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Virtus.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 8:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Firelight.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Sun's Glory.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 9:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Battlo.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Bellesguard.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 10:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Fulgor del Cielo.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Luz Celestial.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 11:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Chromatic Splendor.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Comitas.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;
            case 12:
                schemaIcon3 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Via Lux.jpg");
                schemaIcon4 = new ImageIcon("C:\\Users\\giovanni\\IdeaProjects\\ing-sw-2018-fiscaletti-franchin-gangemi\\src\\main\\java\\it\\polimi\\se2018\\view\\SchemaCardWindow\\Industria.jpg");
                buttonSchema3 = new JButton(schemaIcon3);
                buttonSchema4 = new JButton(schemaIcon4);
                break;


        }

        int finalSelected1 = selected1;
        int finalSelected2 = selected2;
        int finalSelected3 = selected3;
        int finalSelected4 = selected4;


        buttonSchema1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("ho premuto il tasto");
                boolean notSelected = false;
                setChanged();
                notifyObservers(new SelectedSchemaMessage("username","controller",finalSelected1));
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


        buttonSchema2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifyObservers(new SelectedSchemaMessage("username","controller",finalSelected2));

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

        buttonSchema3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifyObservers(new SelectedSchemaMessage("username","controller",finalSelected3));

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

        buttonSchema4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setChanged();
                notifyObservers(new SelectedSchemaMessage("username","controller",finalSelected4));

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
        container.add(buttonSchema2);
        container.add(buttonSchema3);
        container.add(buttonSchema4);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.add(container);
        frame.setSize(550,750);
        frame.setResizable(true);
        frame.setVisible(true);
*/
        setChanged();
        notifyObservers(new SelectedSchemaMessage(username,"server",1));
    }


    //metodo che serve solo per i test


    public void setUsername(String username) {
        this.username = username;
    }
}




