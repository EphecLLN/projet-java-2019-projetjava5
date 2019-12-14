package client;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    private static String[] options = { 
        "Horizontal",
        "Vertical"
    };
    
    public static String getConnectionInfo(){
        port.setText("5555");
        ip.setText("127.0.0.1");
        int option = JOptionPane.showConfirmDialog(panel, message, "Connect to Server", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return name.getText() + "-" +  port.getText() + "-" + ip.getText();
        }
        return "ER-ER-ER";
    }


    public static String getShotDirection(){
        int option = JOptionPane.showOptionDialog(panel,"Choose the direction of the airstrike","airstrike", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
        if (option == 0) {
            return "H";
        }
        return "V";
    }
    
    public static void youWon(){
        JOptionPane.showMessageDialog(panel, "YOU WON!\nClick \"ok\" to close the game.");
    }
    
    public static void youLost(){
        JOptionPane.showMessageDialog(panel, "YOU LOST...\nClick \"ok\" to close the game.");
    }

}