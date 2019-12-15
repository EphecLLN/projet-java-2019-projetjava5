package client;

import java.util.HashMap;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class is used to create, display and manipulate the GUI-interface of the game.
 */
public class GuiGrid {

	private static Font miniFont = new Font(Font.SANS_SERIF,Font.BOLD,10);
	private static final String[] row = {"A","B","C","D","E","F","G","H","I","J","K","L","M"};
	private static final String[] col = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
	public static HashMap<String,JButton> myGridButtons = new HashMap<>();
	public static HashMap<String,JButton> enemyGridButtons = new HashMap<>();
    static Color background = null;
	
	/**
	 * Method that creates and adds the user's grid to the GUI-interface 
	 * 
	 * @param grid {JPanel} - the panel in which the buttons need to be placed
	 */
	protected static void createYourGrid(JPanel grid) {
		for (int i=0; i<=row.length;i++) {
			for (int j=0;j<=col.length;j++) {
				if (i==0) {	
					if (j==0) {
					grid.add(mkButDisabled("  "));} 		//creates top left corner as a blank button
					else {
						grid.add(mkButDisabled(j+"  "));	//creates the column headers
					}
				}
				else{
					if (j==0) {
						grid.add(mkButDisabled(row[i-1] + " "));}	//creates the row headers
					else {
						String key = row[i-1] + j;					//creates the cells
						JButton currentButton = mkBut("");	
						currentButton.setName(key);
						grid.add(currentButton);
						myGridButtons.put(key,currentButton);
					}
				}
			}
		}
	}

	/**
	 * Method that creates and adds the enemy's grid to the GUI-interface 
	 * 
	 * @param grid {JPanel} - the panel in which the buttons need to be placed
	 */
	protected static void createEnnemyGrid(JPanel grid) {
		for (int i=0; i<=row.length;i++) {
			for (int j=0;j<=col.length;j++) {
				if (i==0) {	
					if (j==0) {
					grid.add(mkButDisabled("  "));}			//creates top left corner as a blank button
					else {
						grid.add(mkButDisabled(j+"  "));	//creates the column headers
					}
				}
				else{
					if (j==0) {
						grid.add(mkButDisabled(row[i-1] + " "));}	//creates the row headers
					else {
						String key = row[i-1] + j;					//creates the cells
						JButton currentButton = mkBut2("");
						currentButton.setName(key);
						grid.add(currentButton);
						enemyGridButtons.put(key,currentButton);
					}
				}
			}
		}
	}

	/**
	 * Method that creates a button with a specific name and properties.
	 * The button created is a user's grid button that represents a cell. 
	 * 
	 * @param text {String} - the text that should be displayed on the button
	 * 
	 * @return {JButton} - the just created button
	 */
	private static JButton mkBut (String text) {
   	 	JButton b = new JButton(text);
   	 	
   	 	b.setMargin(new Insets(0, 0, 0, 0));
   	 	b.setFont(miniFont);
   	 	b.setPreferredSize(new Dimension(25,25));
   	 	b.setFocusPainted(false);

		b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				ClientGui.addToClickedCoords(b.getName());
            }
         });

   	 	return b;
	}
	
	/**
	 * Method that creates a button with a specific name and properties.
	 * The button created is a enemy's grid button that represents a cell. 
	 * 
	 * @param text {String} - the text that should be displayed on the button
	 * 
	 * @return {JButton} - the just created button
	 */
	private static JButton mkBut2 (String text) {
   	 	JButton b = new JButton(text);
   	 	
   	 	b.setMargin(new Insets(0, 0, 0, 0));
   	 	b.setFont(miniFont);
   	 	b.setPreferredSize(new Dimension(25,25));
   	 	b.setFocusPainted(false);
		b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				ClientGui.clickedEnemyCoord = b.getName();
            }
         });
			
   	 	return b;
	}
	
	/**
	 * Method that creates a button with a specific name and properties.
	 * The button that is created is disabled!  
	 * 
	 * @param text {String} - the text that should be displayed on the button
	 * 
	 * @return {JButton} - the just created button
	 */
	private static JButton mkButDisabled (String text) {
		JButton b = new JButton(text);
		b.setMargin(new Insets(0, 0, 0, 0));
   	 	b.setFont(miniFont);
   	 	b.setPreferredSize(new Dimension(25,25));
   	 	b.setFocusPainted(false);
   	 	b.setContentAreaFilled(false);
   	 	b.setRolloverEnabled(false);
   	 	return b;
	}

	/**
	 * Method that displays elements on a given grid based on the given parameters:
	 * 
	 * @param val {String} - The type of the element to display, this will affect what the user will see
	 * @param data {String} - The coordinate of where the element should be placed 
	 * 						  and sometimes combined with the name of a unit 
	 * @param isOutGoing {boolean} - the grid in which the element should be placed,
	 * 								 true: enemyGrid, false: myGrid
	 */
	protected static void insertInGrid(String val, String data, boolean isOutGoing) {
		
		JButton thisButton;
		String coord = data.split("/")[0];

		// insert in the ennemy's grid
		if (isOutGoing) {
			thisButton = enemyGridButtons.get(coord);
		}
		// insert in your grid
		else {
			thisButton = myGridButtons.get(coord);
		}
		
		switch (val) {
			case "Unit":
				switch(data.split("/")[1]){
					case "Airport (2x4)":
						thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Units/Airport.png")));
						break;
					case "Radar Tower (2x3)":
						thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Units/Radar.png")));
						break;
					case "HeadQuarter (2x2)":
						thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Units/HQ.png")));
						break;
					case "Railway Gun (1x6)":
						thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Units/RailwayGun.png")));
						break;
					case "MMRL (2x2)":
						thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Units/MMRL.png")));
						break;
					case "Tank (1x2)":
						thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Units/Tank.png")));
						break;
				}
				//thisButton.setBackground(Color.GREEN);
				break;
			case "Hit":
				//thisButton.setBackground(Color.YELLOW);
				thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Shots/Hit.png")));
				break;
			case "noHit":
				//thisButton.setBackground(Color.BLUE);
				thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Shots/noHit.png")));
				break;
			case "Destroyed":
				//thisButton.setBackground(Color.RED);
				thisButton.setIcon(new ImageIcon(GuiGrid.class.getResource("/img/Shots/Dead.png")));
				break;
			default:
				break;
		}
	}

}
