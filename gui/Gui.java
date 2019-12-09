package gui;

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
public class Gui {  
	
	JTextArea outputText = new JTextArea();
     Gui() {  
        JFrame f= new JFrame("Battleground");
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,20);
        Font font2 = new Font(Font.SANS_SERIF,Font.BOLD,16);
        f.setSize(1215,930);    
        f.setLayout(null);
        
        JPanel myGrid=new JPanel();  
        myGrid.setBounds(5,25,520,515);
        //myGrid.setBackground(Color.gray);
        
        myGrid.setLayout(new GridLayout(14, 14));
        GuiGrid.createYourGrid(myGrid);
        
        JTextPane myGridTitle = new JTextPane();
        myGridTitle.setBounds(180, 0, 231, 25);
        myGridTitle.setText("Your Battleground :");
        myGridTitle.setFont(font);
        myGridTitle.setForeground(Color.GREEN);
        myGridTitle.setEditable(false);
        
        JPanel ennemyGrid=new JPanel();  
        ennemyGrid.setBounds(540,25,510,515);    
        //ennemyGrid.setBackground(Color.gray);
        
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
        
        //outputText declarer avant pour pouvoir y acceder avec setters
        outputText.setFont(font);
        outputText.setForeground(Color.YELLOW);
        outputText.setPreferredSize(new Dimension(1170, 270));
        outputText.setBackground(Color.BLACK);
        outputText.setText("Zone de texte \n\n");
        outputText.append("Text added with 'back to line' (fonction 'addText'=> Protected)");
        outputPane.add(outputText);
        
        JPanel weaponsPane = new JPanel();
        weaponsPane.setBounds(1060, 0, 200, 550);
        //weaponsPane.setBackground(Color.BLUE);
        
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
        
        JButton btnPrecision = new JButton( new ImageIcon("img/precision.png"));
        btnPrecision.setText(" Precision shoot (S) ");
        buttonEvent(btnPrecision);
        textCenter(btnPrecision);
        weaponsBar.add(btnPrecision);
        
        JButton btnMissile = new JButton( new ImageIcon("img/missile.jpg"));
        btnMissile.setText(" Missile barage (R)   ");
        textCenter(btnMissile);
        buttonEvent(btnMissile);
        weaponsBar.add(btnMissile);
        weaponsBar.addSeparator();

        JButton btnAirstrike = new JButton( new ImageIcon("img/airstrike.png"));
        btnAirstrike.setText(" Airstrike (A)             ");
        textCenter(btnAirstrike);
        buttonEvent(btnAirstrike);
        weaponsBar.add(btnAirstrike);
        weaponsBar.addSeparator();
        
        JButton btnRadar = new JButton( new ImageIcon("img/radar.png"));
        btnRadar.setText(" Radar discovery (D)");
        textCenter(btnRadar);
        buttonEvent(btnRadar);
        btnRadar.setEnabled(false);
        weaponsBar.add(btnRadar);
        
        JButton btnBigshoot = new JButton( new ImageIcon("img/bigShoot.png"));
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
        }  
     
     private void textCenter(JButton x) {
     	x.setHorizontalTextPosition(JButton.CENTER);
     	x.setVerticalTextPosition(JButton.BOTTOM);
     }
     private void buttonEvent(JButton but) {
    	 but.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				switch (but.getText()) {
				case " Precision shoot (S) ":
					JOptionPane.showMessageDialog(f,"Precision shoot");
					break;
				case " Missile barage (R)   ":
					JOptionPane.showMessageDialog(f,"Missile barage");
					but.setEnabled(false);
					break;
				case " Airstrike (A)             ":
					JOptionPane.showMessageDialog(f,"You called for an airstrike !");
					but.setEnabled(false);
					break;
				case " Radar discovery (D)":
					JOptionPane.showMessageDialog(f,"Radar discovery => Sending ennemy position");
					break;
					
				case " Big Shoot (B)           ":
					JOptionPane.showMessageDialog(f,"Big shoot => Explosion in 3..2..1..");
					but.setEnabled(false);
					break;
				}
				
			}
		});
     }
     protected void setText(String text) {
    	 outputText.setText(text+ "\n");
     }
     protected void addText(String text) {
    	 outputText.append("\n" +text);
     }
     public static void main(String args[]) {  
    	 Gui gui = new Gui();
    	 gui.addText("Coucou!");
     }  
} 