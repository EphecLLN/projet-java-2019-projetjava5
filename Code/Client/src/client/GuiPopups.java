package client;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class regroups all the popups used in the GUI-interface.
 */
public class GuiPopups {

    private static final JPanel panel = new JPanel();
    private static JTextField name = new JTextField();
    private static JTextField port = new JTextField();
    private static JTextField ip = new JTextField();
    private static Object[] message = {
        "Your name:", name,
        "Port:", port,
        "Ip of server", ip
    };
    private static String[] directions = { 
        "Horizontal",
        "Vertical"
    };
    private static String[] quit = { 
        "Retry",
        "Quit"
    };
    
    /**
     * Method that generates a popup where the user can enter the connection information
     * to connect to the server
     *
     * @return {String} - All the data the user has input or an "error" string if the user closed the popup
     */
    public static String getConnectionInfo(){
        port.setText("5555");
        ip.setText("127.0.0.1");
        int option = JOptionPane.showConfirmDialog(panel, message, "Connect to Server", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return name.getText() + "-" +  port.getText() + "-" + ip.getText();
        }
        return "ER-ER-ER";
    }

    /**
     * Method that generates a popup where the user can choose between two buttons,
     * -> horizontal 
     * -> vertical
     * 
     * This method is called when the user needs to choose a shot direction for the airstrike.
     * 
     * @return {String} - "H" if the horizontal button is pressed, "V" if the vertical button is pressed
     */
    public static String getShotDirection(){
        int option = JOptionPane.showOptionDialog(panel,"Choose the direction of the airstrike","airstrike", 0,JOptionPane.INFORMATION_MESSAGE,null,directions,null);
        if (option == 0) {
            return "H";
        }
        return "V";
    }
    
    /**
     * Method that generates a popup to show that the user has won. 
     */
    public static void youWon(){
        JOptionPane.showMessageDialog(panel, "YOU WON!\nClick \"ok\" to close the game.");
    }
    
    /**
     * Method that generates a popup to show that the user has lost.
     */
    public static void youLost(){
        JOptionPane.showMessageDialog(panel, "YOU LOST...\nClick \"ok\" to close the game.");
    }

    /**
     * Method that generates a popup to show a given error message and 
     * gives the user the choice to retry or quit. 
     * 
     * @param errMsg {String} - the error message that should be displayed in the popup
     * 
     * @return {boolean} - true if retry button is pressed, false if quit button is pressed
     */
    public static boolean getQuit(String errMsg){
        int option = JOptionPane.showOptionDialog(panel,errMsg,"ERROR", 0,JOptionPane.INFORMATION_MESSAGE,null,quit,null);
        if (option == 0) {
            return true;
        }
        return false;
    }


}