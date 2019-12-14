package client;

import java.io.*;
import java.net.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

public class ClientGui extends Client {

    private static String[] clickedCoords = new String[2];
    protected static String clickedEnemyCoord = "";

    private JTextArea outputText = new JTextArea();
    private JButton btnPrecision, btnMissile, btnAirstrike, btnRadar, btnBigshoot;
    private String shotBtnPressed = "";


    // !---------------------------------------------------------------------------------
    // ! Constructor
    // !---------------------------------------------------------------------------------

    /**
     * Constructor
     */
    public ClientGui() {
        JFrame f = new JFrame("Battleground");
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        f.setSize(1215, 930);
        f.setLayout(null);

        JPanel myGrid = new JPanel();
        myGrid.setBounds(5, 25, 520, 515);
        // myGrid.setBackground(Color.gray);

        myGrid.setLayout(new GridLayout(14, 14));
        GuiGrid.createYourGrid(myGrid);

        JTextPane myGridTitle = new JTextPane();
        myGridTitle.setBounds(180, 0, 231, 25);
        myGridTitle.setText("Your Battleground :");
        myGridTitle.setFont(font);
        myGridTitle.setForeground(Color.GREEN);
        myGridTitle.setEditable(false);

        JPanel ennemyGrid = new JPanel();
        ennemyGrid.setBounds(540, 25, 510, 515);
        // ennemyGrid.setBackground(Color.gray);

        ennemyGrid.setLayout(new GridLayout(14, 14));
        GuiGrid.createEnnemyGrid(ennemyGrid);

        JTextPane ennemyGridTitle = new JTextPane();
        ennemyGridTitle.setBounds(650, 0, 290, 25);
        ennemyGridTitle.setText("Ennemy's Battleground :");
        ennemyGridTitle.setFont(font);
        ennemyGridTitle.setForeground(Color.RED);
        ennemyGridTitle.setEditable(false);

        JPanel outputPane = new JPanel();
        outputPane.setBounds(10, 600, 1180, 210);
        outputPane.setBackground(Color.RED);

        // outputText declarer avant pour pouvoir y acceder avec setters
        outputText.setFont(font);
        outputText.setForeground(Color.YELLOW);
        outputText.setPreferredSize(new Dimension(1170, 200));
        outputText.setBackground(Color.BLACK);
        // outputText.setText("Zone de texte \n\n");
        // outputText.append("Text added with 'back to line' (fonction 'addText'=>
        // Protected)");
        outputPane.add(outputText);

        JPanel weaponsPane = new JPanel();
        weaponsPane.setBounds(1060, 0, 200, 550);
        // weaponsPane.setBackground(Color.BLUE);

        JPanel separationPane = new JPanel();
        separationPane.setBounds(1054, 0, 5, 540);
        separationPane.setBackground(Color.BLACK);

        JToolBar weaponsBar = new JToolBar();
        weaponsBar.setFloatable(false);
        weaponsBar.setOrientation(SwingConstants.VERTICAL);
        JTextField text = new JTextField("   WEAPONS :");
        text.setEditable(false);
        text.setFont(font2);
        weaponsBar.add(text);
        weaponsBar.addSeparator();

        btnPrecision = new JButton(new ImageIcon(getClass().getResource("/img/Bonus/precision.png")));
        btnPrecision.setText(" Precision shoot (S) ");
        buttonEvent(btnPrecision);
        textCenter(btnPrecision);
        weaponsBar.add(btnPrecision);

        btnMissile = new JButton(new ImageIcon(getClass().getResource("/img/Bonus/missile.jpg")));
        btnMissile.setText(" Missile barage (R)   ");
        textCenter(btnMissile);
        buttonEvent(btnMissile);
        weaponsBar.add(btnMissile);
        weaponsBar.addSeparator();

        btnAirstrike = new JButton(new ImageIcon(getClass().getResource("/img/Bonus/airstrike.png")));
        btnAirstrike.setText(" Airstrike (A)             ");
        textCenter(btnAirstrike);
        buttonEvent(btnAirstrike);
        weaponsBar.add(btnAirstrike);
        weaponsBar.addSeparator();

        btnRadar = new JButton(new ImageIcon(getClass().getResource("/img/Bonus/radar.png")));
        btnRadar.setText(" Radar discovery (D)");
        textCenter(btnRadar);
        buttonEvent(btnRadar);
        btnRadar.setEnabled(false);
        weaponsBar.add(btnRadar);

        btnBigshoot = new JButton(new ImageIcon(getClass().getResource("/img/Bonus/bigShoot.png")));
        btnBigshoot.setText(" Big Shoot (B)           ");
        textCenter(btnBigshoot);
        buttonEvent(btnBigshoot);
        weaponsBar.add(btnBigshoot);

        weaponsPane.add(weaponsBar);

        f.add(myGrid);
        f.add(ennemyGrid);
        f.add(outputPane);
        f.add(weaponsPane);
        f.add(separationPane);
        f.add(myGridTitle);
        f.add(ennemyGridTitle);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        outputText.setCaretColor(Color.red);

    }

    // !---------------------------------------------------------------------------------
    // ! Buttons manager
    // !---------------------------------------------------------------------------------

    private void buttonEvent(JButton but) {
        but.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame();
                switch (but.getText()) {
                case " Precision shoot (S) ":
                    shotBtnPressed = "S";
                    break;
                case " Missile barage (R)   ":
                    shotBtnPressed = "R";
                    but.setEnabled(false);
                    break;
                case " Airstrike (A)             ":
                    shotBtnPressed = "A";
                    but.setEnabled(false);
                    break;
                case " Radar discovery (D)":
                    shotBtnPressed = "D";
                    break;

                case " Big Shoot (B)           ":
                    shotBtnPressed = "B";
                    but.setEnabled(false);
                    break;
                }
            }
        });
    }

    private String getButtonPressed() {

        while(shotBtnPressed.equals("")){
            sleep(100);
        }
        String btn = shotBtnPressed;
        shotBtnPressed = "";
        return btn;
    }

    private void setShotAvailability(String avShots) {

        if (avShots.contains("A")) {
            btnAirstrike.setEnabled(true);
        }
        if (avShots.contains("D")) {
            btnRadar.setEnabled(true);
        }
        if (avShots.contains("B")) {
            btnBigshoot.setEnabled(true);
        }
        if (avShots.contains("R")) {
            btnMissile.setEnabled(true);
        }
    }

    private void textCenter(JButton x) {
        x.setHorizontalTextPosition(JButton.CENTER);
        x.setVerticalTextPosition(JButton.BOTTOM);
    }

    // !---------------------------------------------------------------------------------
    // ! Textfield manager
    // !---------------------------------------------------------------------------------

    protected void setText(String text) {
        outputText.setText(text);
    }

    protected void addText(String text) {
        outputText.append(text);
    }

    protected void removeLines() {
        outputText.setText(null);
    }

    // !---------------------------------------------------------------------------------
    // ! Initialize Cleint <-> Server Connection
    // !---------------------------------------------------------------------------------


    /**
     * Method that checks if name (input from user) is between 4 and 12 chracters in
     * length
     * 
     * @param name    {String} - //TODO
     * @param scanner {Scanner} - //TODO
     */
    private boolean checkNameClient(String name) {
        if (name.length() < 4 || name.length() > 13) {
            return false;
        }
        return true;
    }

    /**
     * Method thet initialises a connection with the server
     * 
     */
    private void InitConnection() {
        String[] connectionInfo = GuiPopups.getConnectionInfo().split("-");
        try{
            name = connectionInfo[0];
            port = Integer.valueOf(connectionInfo[1]);
            ip = connectionInfo[2];
        }
        catch(Exception e){
            setText("You must connect to the server!");
            InitConnection();
        }

        if(!checkNameClient(name)){
            setText("Name must be >4 and <14!");
            InitConnection();
        }

        try {
            // Establish the connection with the server with IP and Port from user input
            setText("Waiting to connect to server...");
            sock = new Socket(ip, port);
            setText("Connected !");
            // setText("__________________________");

            // In and out streams => Information received (inputStream) and sent
            // (outputStream) :
            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());

            out.writeUTF(name);// Sends client name to server

        } catch (Exception e) {
            setText("Error, retry connection");
            InitConnection();
        }
    }

    // !---------------------------------------------------------------------------------
    // ! Cleint <-> Server Communication
    // !---------------------------------------------------------------------------------

    /**
     * Method that gets and processes the queries from the server.
     */
    private void listenToServer() {
        String[] queryFromServer;
        String command = "";
        String comment = "";
        String strToServer = "";

        while (true) {

            queryFromServer = getFromServer().split("-");
            command = queryFromServer[0];
            switch (command) {
            case "U": // Placing of units -> queryFromServer format : U-<unitName>-<unitSize>-<Comment>
                String unitName = queryFromServer[1];
                comment = queryFromServer[3];
                addText("\nWhere do you want to place the " + unitName + "? Click on top-left and bottom-right coordinates.\n");
                if (!comment.equals("NC")) {
                    addText(comment);
                }
                sendToServer("U-" + unitName + "-" + queryFromServer[2]);
                strToServer = getClickedCoords();
                sendToServer(strToServer);
                break;

            case "S": // Shooting -> queryFromServer format : S-<commandType>-<data>-<Comment>
                String commandType = queryFromServer[1];
                comment = queryFromServer[3];
                switch (commandType) {
                case "T": // shot Type
                    String availableShotTypes = queryFromServer[2];
                    setShotAvailability(availableShotTypes);
                    setText("Choose a shot type\n");
                    if (!comment.equals("NC")) {
                        addText(comment);
                    }
                    strToServer = getButtonPressed();
                    sendToServer(strToServer);
                    break;

                case "C": // shot center Coord
                    addText("Click the coordinate of the center of the shot. (on the enemy's grid!)\n");
                    if (!comment.equals("NC")) {
                        addText(comment);
                    }
                    strToServer = getClickedEnemyCoord();
                    sendToServer(strToServer);
                    break;

                case "D": // direction
                    strToServer = GuiPopups.getShotDirection();
                    sendToServer(strToServer);
                    break;
                }
                break;

            case "I": // Comment with input from client
                comment = queryFromServer[1];
                addText(comment);
                // strToServer = scn.nextLine();
                sendToServer(strToServer);
                break;

            case "C": // Comment without input from client
                comment = queryFromServer[1];    
                addText(comment);
                break;

            case "displayGrid":
                break;

            case "insertUnit":
                command = getFromServer();
                GuiGrid.insertInGrid("Unit", command, false);
                break;

            case "Hit":
                command = getFromServer();
                GuiGrid.insertInGrid("Hit", command, true);
                break;

            case "noHit":
                command = getFromServer();
                GuiGrid.insertInGrid("noHit", command, true);
                break;

            case "Destroyed":
                command = getFromServer();
                GuiGrid.insertInGrid("Destroyed", command, true);
                break;

            case "myDestroyed":
                command = getFromServer();
                GuiGrid.insertInGrid("Destroyed", command, false);
                break;

            case "myHit":
                command = getFromServer();
                GuiGrid.insertInGrid("Hit", command, false);
                break;

            case "myNoHit":
                command = getFromServer();
                GuiGrid.insertInGrid("noHit", command, false);
                break;

            case "Rem":
                command = getFromServer();
                removeLines();
                break;

            case "WON":
                GuiPopups.youWon();
                System.exit(0);
                break;
            
            case "LOST":
                GuiPopups.youLost();
                System.exit(0);
                break;

            case "CLOSE":
                System.exit(0);
                break;

            default:
               
            }
        }

    }

    // !---------------------------------------------------------------------------------
    // ! Other Methods
    // !---------------------------------------------------------------------------------

    public static void addToClickedCoords(String coord) {
        if (clickedCoords[0] == null) {
            clickedCoords[0] = coord;
        } else {
            clickedCoords[1] = coord;
        }
    }

    private void emptyClickedCoords() {
        clickedCoords[0] = null;
        clickedCoords[1] = null;
    }

    private String getClickedCoords() {
        String coords;
        while (clickedCoords[1] == null) {
            sleep(100);
        }
        coords = clickedCoords[0] + " " + clickedCoords[1];
        emptyClickedCoords();
        return coords;
    }

    private String getClickedEnemyCoord() {
        while (clickedEnemyCoord.equals("")) {
            sleep(100);
        }
        String coord = clickedEnemyCoord;
        clickedEnemyCoord = "";
        return coord;
    }
    //clickedEnemyCoord

    private static void setCrossPlatformLook() {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
    }

    // !---------------------------------------------------------------------------------
    // ! MAIN
    // !---------------------------------------------------------------------------------

    public static void main(String args[]) {
        setCrossPlatformLook();
        ClientGui gui = new ClientGui();
        gui.InitConnection();
        gui.listenToServer();
    }

}