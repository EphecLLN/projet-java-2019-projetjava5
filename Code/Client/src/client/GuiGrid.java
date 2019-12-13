package client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GuiGrid {

	private static Font miniFont = new Font(Font.SANS_SERIF,Font.BOLD,10);
	private static final String[] row = {"A","B","C","D","E","F","G","H","I","J","K","L","M"};
    private static final String[] col = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
    static Color background = null;
	
	protected static void createYourGrid(JPanel grid) {
		for (int i=0; i<=row.length;i++) {
			if (i!=0) {
				System.out.println("");
			}
			for (int j=0;j<=col.length;j++) {
				if (i==0) {	
					if (j==0) {
					grid.add(mkButDisabled("  "));}
					else {
						grid.add(mkButDisabled(j+"  "));
					}
				}
				else{
					if (j==0) {
						grid.add(mkButDisabled(row[i-1] + " "));}
					else {
						grid.add(mkBut(row[i-1] + j +" "));
					}
				}
			}
		}
	}

	protected static void createEnnemyGrid(JPanel grid) {
		for (int i=0; i<=row.length;i++) {
			if (i!=0) {
				System.out.println("");
			}
			for (int j=0;j<=col.length;j++) {
				if (i==0) {	
					if (j==0) {
					grid.add(mkButDisabled("  "));}
					else {
						grid.add(mkButDisabled(j+"  "));
					}
				}
				else{
					if (j==0) {
						grid.add(mkButDisabled(row[i-1] + " "));}
					else {
						grid.add(mkBut2(row[i-1] + j +" "));
					}
				}
			}
		}
	}

	private static JButton mkBut (String text) {
   	 	JButton b = new JButton(text);
   	 	
   	 	b.setMargin(new Insets(0, 0, 0, 0));
   	 	b.setFont(miniFont);
   	 	b.setPreferredSize(new Dimension(25,25));
   	 	b.setFocusPainted(false);
   	 	b.addMouseListener(new MouseAdapter() {

   	 		@Override
        	public void mouseEntered(MouseEvent e) {
   	 			background = b.getBackground();
				b.setBackground(Color.GREEN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				b.setBackground(background);
			}

		});

		b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				System.out.println(b.getText()); 
            }
         });

   	 	return b;
	}
	

	private static JButton mkBut2 (String text) {
   	 	JButton b = new JButton(text);
   	 	
   	 	b.setMargin(new Insets(0, 0, 0, 0));
   	 	b.setFont(miniFont);
   	 	b.setPreferredSize(new Dimension(25,25));
   	 	b.setFocusPainted(false);
   	 	b.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				background = b.getBackground();
				b.setBackground(Color.RED);
			}
				
			@Override
			public void mouseExited(MouseEvent e) {
				b.setBackground(background);
			}
		});

		b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				System.out.println(b.getText()); 
            }
         });
			
   	 	return b;
	}
	
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

}
