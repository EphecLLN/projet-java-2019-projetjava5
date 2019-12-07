package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants; 
public class Gui {  
	
     Gui() {  
        JFrame f= new JFrame("Battleground");
        f.setSize(1200,930);    
        f.setLayout(null);
        
        JPanel panel=new JPanel();  
        panel.setBounds(5,5,200,200);    
        panel.setBackground(Color.gray);  
        
        JButton b1=new JButton("Button 1");     
        b1.setBounds(50,100,80,30);    
        b1.setBackground(Color.yellow);   
        JButton b2=new JButton("Button 2");   
        b2.setBounds(100,100,80,30);    
        b2.setBackground(Color.green);

        JPanel outputPane = new JPanel();
        outputPane.setBounds(10, 600, 1180, 280);
        outputPane.setBackground(Color.RED);
        
        JTextArea outputText = new JTextArea();
        Font font = new Font(Font.SANS_SERIF,Font.PLAIN,20);
        outputText.setFont(font);
        outputText.setForeground(Color.YELLOW);
        outputText.setPreferredSize(new Dimension(1170, 270));
        outputText.setBackground(Color.BLACK);
        outputText.setText("Zone de texte pour relier output du code (ligne de commande) ici (GUI)\n\n");
        outputText.append("Text added avec retour a la ligne (TODO :implementer fonction pour Martin)");
        outputPane.add(outputText);
        
        JPanel weaponsPane = new JPanel();
        weaponsPane.setBounds(1060, 0, 180, 550);
        //weaponsPane.setBackground(Color.BLUE);
        
        JPanel separationPane = new JPanel();
        separationPane.setBounds(1054, 0, 5, 515);
        separationPane.setBackground(Color.BLACK);
        
        JToolBar weaponsBar = new JToolBar();
        weaponsBar.setFloatable(false);
        weaponsBar.setOrientation(SwingConstants.VERTICAL);
        JTextField text = new JTextField("            WEAPONS :");
        text.setEditable(false);
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
        weaponsBar.add( btnMissile );
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
        
        
        panel.add(b1); panel.add(b2);  
        f.add(panel);
        f.add(outputPane);
        f.add(weaponsPane);
        f.add(separationPane); 
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
     public static void main(String args[]) {  
    	 new Gui();
    	 }  
    } 
