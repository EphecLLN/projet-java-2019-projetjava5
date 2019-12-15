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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * This class inherits from the CLient class.
 * This class is executable and is responsible for displaying the game with a GUI-interface. 
 * 
 * This class is separated in three big sections :
 *
 *  1) The construction of the actual GUI-interface
 * 
 *  2) First the user is prompted to enter valid connection information to connect to
 *     the server.
 * 
 *  3) Once connected, the class will wait for a query from the server,
 *     process the incoming data and act accordingly.
 */
public class ClientGui extends Client {

    private static String[] clickedCoords = new String[2];
    protected static String clickedEnemyCoord = "";

    private JTextArea outputText = new JTextArea();
    private JButton btnPrecision, btnMissile, btnAirstrike, btnRadar, btnBigshot;
    private String shotBtnPressed = "";


    //!---------------------------------------------------------------------------------
    //!                                  Constructor
    //!---------------------------------------------------------------------------------

    /**
     * Constructor 
     * 
     * This constructor creates and places all the buttons and text-areas of the GUI-interface
     */
    public ClientGui() {

        //!----------------
        //! General window
        //!----------------
        JFrame f = new JFrame("Battleground");
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        f.setSize(1215, 930);
        f.setLayout(null);

        //!-----------------
        //! myGrid creation
        //!-----------------
        JPanel myGrid = new JPanel();
        myGrid.setBounds(5, 25, 520, 515);
        myGrid.setLayout(new GridLayout(14, 14));
        GuiGrid.createYourGrid(myGrid);

        JTextPane myGridTitle = new JTextPane();
        myGridTitle.setBounds(180, 0, 231, 25);
        myGridTitle.setText("Your Battleground :");
        myGridTitle.setFont(font);
        myGridTitle.setForeground(Color.GREEN);
        myGridTitle.setEditable(false);

        //!--------------------
        //! enemyGrid creation
        //!--------------------
        JPanel enemyGrid = new JPanel();
        enemyGrid.setBounds(540, 25, 510, 515);
        enemyGrid.setLayout(new GridLayout(14, 14));
        GuiGrid.createEnnemyGrid(enemyGrid);

        JTextPane enemyGridTitle = new JTextPane();
        enemyGridTitle.setBounds(650, 0, 290, 25);
        enemyGridTitle.setText("Enemy's Battleground :");
        enemyGridTitle.setFont(font);
        enemyGridTitle.setForeground(Color.RED);
        enemyGridTitle.setEditable(false);

        //!-------------------
        //! Text display zone
        //!-------------------
        JPanel outputPane = new JPanel();
        outputPane.setBounds(10, 600, 1180, 210);
        outputPane.setBackground(Color.RED);

        outputText.setFont(font);
        outputText.setForeground(Color.YELLOW);
        outputText.setPreferredSize(new Dimension(1170, 200));
        outputText.setBackground(Color.BLACK);
        outputText.setCaretColor(Color.red);

        outputPane.add(outputText);
        

        //!---------------
        //! Shot buttons 
        //!---------------
        JPanel weaponsPane = new JPanel();
        weaponsPane.setBounds(1060, 0, 200, 550);

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

        btnBigshot = new JButton(new ImageIcon(getClass().getResource("/img/Bonus/bigShoot.png")));
        btnBigshot.setText(" Big Shoot (B)           ");
        textCenter(btnBigshot);
        buttonEvent(btnBigshot);
        weaponsBar.add(btnBigshot);

        weaponsPane.add(weaponsBar);

        //!-----------------------------------
        //! Placing of all created components
        //!-----------------------------------
        f.add(myGrid);
        f.add(enemyGrid);
        f.add(outputPane);
        f.add(weaponsPane);
        f.add(separationPane);
        f.add(myGridTitle);
        f.add(enemyGridTitle);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }


    //!---------------------------------------------------------------------------------
    //!                                 Buttons manager
    //!---------------------------------------------------------------------------------

    /**
     * Method that listens to all of the shot-buttons,
     * If a button is pressed, the associated string is saved into a global variable.
     * 
     * @param but {JButton} - the button type that is checked
     */
    private void buttonEvent(JButton but) {
        but.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
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

    /**
     * Method that waits until a shot-button is pressed,
     * if a shot-button is pressed, the method returns the corresponding string.
     * 
     * @return {String} - the string corresponding to the button that was pressed
     */
    private String getButtonPressed() {
        
        shotBtnPressed = "";
        while(shotBtnPressed.equals("")){
            sleep(100);
        }
        String btn = shotBtnPressed;
        shotBtnPressed = "";
        return btn;
    }

    /**
     * This method checks if a shot-type is available or not and 
     * disables/enables the corresponding shot-buttons accordingly. 
     * 
     * @param avShots {String} - a string containing all the available shot-types,
     *                           previously received from the server
     */
    private void setShotAvailability(String avShots) {

        if (avShots.contains("A")) {
            btnAirstrike.setEnabled(true);
        }
        if (avShots.contains("D")) {
            btnRadar.setEnabled(true);
        }
        if (avShots.contains("B")) {
            btnBigshot.setEnabled(true);
        }
        if (avShots.contains("R")) {
            btnMissile.setEnabled(true);
        }
    }

    /**
     * Method that centers the text of a given button.
     * @param x {JButton} - the button on which the text should be centered
     */
    private void textCenter(JButton x) {
        x.setHorizontalTextPosition(JButton.CENTER);
        x.setVerticalTextPosition(JButton.BOTTOM);
    }


    //!---------------------------------------------------------------------------------
    //!                                Textfield manager
    //!---------------------------------------------------------------------------------

    /**
     * Simple method that redefines the way of printing text to the text-area.
     * This method erase all text present in the text-area before printing the new one.
     * 
     * @param text {String} - the text to print
     */
    protected void setText(String text) {
        outputText.setText(text);
    }

    /**
     * Simple method that redefines the way of printing text to the text-area.
     * This method appends the text to the existing text present in the text-area.
     * 
     * @param text {String} - the text to print
     */
    protected void addText(String text) {
        outputText.append(text);
    }

    /**
     * Method that clears the whole text-area. 
     */
    protected void removeLines() {
        outputText.setText(null);
    }


    //!---------------------------------------------------------------------------------
    //!                    Initialize Client <-> Server Connection
    //!---------------------------------------------------------------------------------

    /**
     * Method that checks if name (input from user) is between 4 and 12 characters in length
     * 
     * @param name {String} - the name the user has input. 
     * 
     * @return {boolean} - true if names is ok, false otherwise
     */
    private boolean checkNameClient(String name) {
        if (name.length() < 4 || name.length() > 13) {
            return false;
        }
        return true;
    }

    /**
     * Method that tries to establish a connection with the server.
     */
    private void InitConnection() {
        String[] connectionInfo = GuiPopups.getConnectionInfo().split("-");
        try{
            name = connectionInfo[0];
            port = Integer.valueOf(connectionInfo[1]);
            ip = connectionInfo[2];
        }
        catch(Exception e){
            if(GuiPopups.getQuit("You must connect to the server to be able to play.\n Please retry or quit game.")){
                InitConnection();
            }
            else{
                System.exit(0);
            }            
        }

        if(!checkNameClient(name)){
            setText("Your name must be between 4 and 12 characters!");
            InitConnection();
        }

        try {
            // Establish the connection with the server with IP and Port from user input
            setText("Waiting to connect to server...");
            sock = new Socket(ip, port);
            setText("Connected!\n");

            // In and out streams => Information received (inputStream) and sent (outputStream):
            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());

            out.writeUTF(name);// Sends client name to server

        } catch (Exception e) {
            System.out.println(e);
            if(GuiPopups.getQuit("Unable to connect to the server.\nPlease retry or quit game.")){
                InitConnection();
            }
            else{
                System.exit(0);
            }  
        }
    }

    // !---------------------------------------------------------------------------------
    // !                       Client <-> Server Communication
    // !---------------------------------------------------------------------------------

    /**
     * Method that gets and processes the queries from the server.
     * There are two main kind of queries:
     * 
     *  -> queries where the user is asked to do something 
     *  -> queries only to update the display, no user-interaction needed
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

                //!---------------------------
                //!  Interactions with user 
                //!---------------------------

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
                
                //!---------------------------
                //!    display management  
                //!---------------------------

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
    // !                                Other Methods
    // !---------------------------------------------------------------------------------

    /**
     * Method that adds a given coordinate to a list of coords.
     * This method is as a buffer to get the two coordinates needed for the 
     * placing of the units on the grid. 
     * 
     * @param coord {String} - the coordinated that pas clicked 
     */
    public static void addToClickedCoords(String coord) {
        if (clickedCoords[0] == null) {
            clickedCoords[0] = coord;
        } 
        else {
            clickedCoords[1] = coord;
        }
    }

    /**
     * Method that resets the array of coordinates for the
     * placing of the units. 
     */
    private void emptyClickedCoords() {
        clickedCoords[0] = null;
        clickedCoords[1] = null;
    }

    /**
     * Method that waits until a two coordinates where chosen,
     * if both are chosen, the method returns their names separated by a whitespace.
     * 
     * @return {String} - the names of the two coordinates separated by a whitespace
     */
    private String getClickedCoords() {
        String coords;
        while (clickedCoords[1] == null) {
            sleep(100);
        }
        coords = clickedCoords[0] + " " + clickedCoords[1];
        emptyClickedCoords();
        return coords;
    }

    /**
     * Method that waits until a coordinate is clicked from the enemy's grid,
     * if a coordinated is clicked, it's name is returned. 
     * 
     * @return {String} - the name of the clicked coordinate
     */
    private String getClickedEnemyCoord() {
        while (clickedEnemyCoord.equals("")) {
            sleep(100);
        }
        String coord = clickedEnemyCoord;
        clickedEnemyCoord = "";
        return coord;
    }

    /**
     * This method tries to define the look of the GUI in a way that it is 
     * as much as possible compatible with all platforms (-> Windows / OSX / Linux / ...)
     * 
     * If it fails to set the cross-platform theme, the system-specific theme will be used!
     */
    private static void setCrossPlatformLook() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) {
            System.out.println(e);
            System.out.println("ERROR - unable to set the cross-platform theme, some interface glitches may occur!");
            //Default theme is used, no further action is needed. 
        }
        
    }

    // !---------------------------------------------------------------------------------
    // !                                        MAIN
    // !---------------------------------------------------------------------------------
    /**
     * Run main to start client in GUI display mode
     */
    public static void main(String args[]) {
        setCrossPlatformLook();
        ClientGui gui = new ClientGui();
        gui.InitConnection();
        gui.listenToServer();
    }

}