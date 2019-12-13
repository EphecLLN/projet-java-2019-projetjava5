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

   private boolean enterKey = false;
   JTextArea outputText = new JTextArea();

   // !---------------------------------------------------------------------------------
   // !                                  Constructor
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
      outputPane.setBounds(10, 600, 1180, 280);
      outputPane.setBackground(Color.RED);

      // outputText declarer avant pour pouvoir y acceder avec setters
      outputText.setFont(font);
      outputText.setForeground(Color.YELLOW);
      outputText.setPreferredSize(new Dimension(1170, 270));
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

      JButton btnPrecision = new JButton(new ImageIcon(getClass().getResource("/img/precision.png")));
      btnPrecision.setText(" Precision shoot (S) ");
      buttonEvent(btnPrecision);
      textCenter(btnPrecision);
      weaponsBar.add(btnPrecision);

      JButton btnMissile = new JButton(new ImageIcon(getClass().getResource("/img/missile.jpg")));
      btnMissile.setText(" Missile barage (R)   ");
      textCenter(btnMissile);
      buttonEvent(btnMissile);
      weaponsBar.add(btnMissile);
      weaponsBar.addSeparator();

      JButton btnAirstrike = new JButton(new ImageIcon(getClass().getResource("/img/airstrike.png")));
      btnAirstrike.setText(" Airstrike (A)             ");
      textCenter(btnAirstrike);
      buttonEvent(btnAirstrike);
      weaponsBar.add(btnAirstrike);
      weaponsBar.addSeparator();

      JButton btnRadar = new JButton(new ImageIcon(getClass().getResource("/img/radar.png")));
      btnRadar.setText(" Radar discovery (D)");
      textCenter(btnRadar);
      buttonEvent(btnRadar);
      btnRadar.setEnabled(false);
      weaponsBar.add(btnRadar);

      JButton btnBigshoot = new JButton(new ImageIcon(getClass().getResource("/img/bigShoot.png")));
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
   // !                                 Button manager
   // !---------------------------------------------------------------------------------

   private void buttonEvent(JButton but) {
      but.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            JFrame f = new JFrame();
            switch (but.getText()) {
            case " Precision shoot (S) ":
               JOptionPane.showMessageDialog(f, "Precision shoot");
               break;
            case " Missile barage (R)   ":
               JOptionPane.showMessageDialog(f, "Missile barage");
               but.setEnabled(false);
               break;
            case " Airstrike (A)             ":
               JOptionPane.showMessageDialog(f, "You called for an airstrike !");
               but.setEnabled(false);
               break;
            case " Radar discovery (D)":
               JOptionPane.showMessageDialog(f, "Radar discovery => Sending ennemy position");
               break;

            case " Big Shoot (B)           ":
               JOptionPane.showMessageDialog(f, "Big shoot => Explosion in 3..2..1..");
               but.setEnabled(false);
               break;
            }
         }
      });
   }

   private void textCenter(JButton x) {
      x.setHorizontalTextPosition(JButton.CENTER);
      x.setVerticalTextPosition(JButton.BOTTOM);
   }

   // !---------------------------------------------------------------------------------
   // !                                Textfield manager
   // !---------------------------------------------------------------------------------

   protected void setText(String text) {
      outputText.setText(text + "\n");
      int len = outputText.getDocument().getLength();
      outputText.setCaretPosition(len);
   }

   protected void addText(String text) {
      outputText.append("\n" + text);
   }

   //!---------------------------------------------------------------------------------
   //!                          Initialize Cleint <-> Server Connection 
   //!---------------------------------------------------------------------------------

   /**
     * Method that checks if name (input from user) is between 4 and 12 chracters in length
     * @param name {String} - //TODO
     * @param scanner {Scanner} - //TODO
     */
	private void checkNameClient(String name) {
      while (name.length()<4 || name.length() >13) {
          setText("Invalid input. Please re-enter a name (min 4 - max 12 :\n");
          name = "Martin"; //TODO getUserIput();
      }
  }
  
  /**
   * Method thet initialises a connection with the server
   * 
   */
  private void InitConnection(){


      // Retrieving client's name from user input :
      setText("What's your name ?");
      name = "Martin"; //TODO getUserIput();
      checkNameClient(name);

      // Retrieving port from user input :
      setText("Port number ? (press enter for default port: 5555)");
      String userStr = "5555"; //TODO getUserIput();
          if(userStr.equals("")){
              port = 5555;
          }
          else{
              port = Integer.valueOf(userStr);
          }
          
      setText(name+"> "+port);
      

      //Retrieving ip adress from user input :
      setText("IP address ? (nothing + enter = localhost)");
      ip = "127.0.0.1"; //TODO getUserIput();
      if (ip.length()==0) {ip = "localhost";}
      setText(name+"> "+ip);

      try{
      //Establish the connection with the server with IP and Port from user input
      setText("Waiting to connect to server...");
      sock = new Socket(ip, port);
      setText("Connected !");
      setText("__________________________");

      // In and out streams => Information received (inputStream) and sent (outputStream) :
      in = new DataInputStream(sock.getInputStream()); 
      out = new DataOutputStream(sock.getOutputStream()); 

      out.writeUTF(name);//Sends client name to server

      }
      catch(Exception e){}
   }

   //!---------------------------------------------------------------------------------
   //!                          Cleint <-> Server Communication 
   //!---------------------------------------------------------------------------------




   
   //!---------------------------------------------------------------------------------
   //!                                 Other Methods
   //!---------------------------------------------------------------------------------

   private static void setCrossPlatformLook(){
      try {
         // Set cross-platform Java L&F (also called "Metal")
         UIManager.setLookAndFeel(
         UIManager.getCrossPlatformLookAndFeelClassName());
      } 
      catch (UnsupportedLookAndFeelException e) {
         // handle exception
      }
      catch (ClassNotFoundException e) {
         // handle exception
      }
      catch (InstantiationException e) {
         // handle exception
      }
      catch (IllegalAccessException e) {
         // handle exception
      }
   } 

   //!---------------------------------------------------------------------------------
   //!                                       MAIN
   //!---------------------------------------------------------------------------------

   public static void main(String args[]) {  
      setCrossPlatformLook();
      ClientGui gui = new ClientGui();
      gui.InitConnection();
   }

} 