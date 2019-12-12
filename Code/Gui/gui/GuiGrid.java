package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GuiGrid {

	private static Font miniFont = new Font(Font.SANS_SERIF,Font.BOLD,10);
	private static final String[] row = {"A","B","C","D","E","F","G","H","I","J","K","L","M"};
    private static final String[] col = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
    static Color background = null;
	private static void testGrid() {
		
		/* Example :
		 * -------------
	     *   1  2  3  4
	     * A A1 A2 A3 A4
	     * B B1 B2 B3 B4
	     * C C1 C2 C3 C4
	     * */
		//---------------
		for (int i=0; i<=row.length;i++) {
			if (i!=0) {
				System.out.println("");
			}
			for (int j=0;j<=col.length;j++) {
				if (i==0) {if ((i==0)&&(j==0)) {System.out.print("  ");}else {System.out.print(j+"  ");}}
				if (i==1) {if ((i==1)&&(j==0)) {System.out.print("A ");}else {System.out.print("A"+j+" ");}}
				if (i==2) {if ((i==2)&&(j==0)) {System.out.print("B ");}else {System.out.print("B"+j+" ");}}
				if (i==3) {if ((i==3)&&(j==0)) {System.out.print("C ");}else {System.out.print("C"+j+" ");}}
				if (i==4) {if ((i==4)&&(j==0)) {System.out.print("D ");}else {System.out.print("D"+j+" ");}}
				if (i==5) {if ((i==5)&&(j==0)) {System.out.print("E ");}else {System.out.print("E"+j+" ");}}
				if (i==6) {if ((i==6)&&(j==0)) {System.out.print("F ");}else {System.out.print("F"+j+" ");}}
				if (i==7) {if ((i==7)&&(j==0)) {System.out.print("G ");}else {System.out.print("G"+j+" ");}}
				if (i==8) {if ((i==8)&&(j==0)) {System.out.print("H ");}else {System.out.print("H"+j+" ");}}
				if (i==9) {if ((i==9)&&(j==0)) {System.out.print("I ");}else {System.out.print("I"+j+" ");}}
				if (i==10) {if ((i==10)&&(j==0)) {System.out.print("J ");}else {System.out.print("J"+j+" ");}}
				if (i==11) {if ((i==11)&&(j==0)) {System.out.print("K ");}else {System.out.print("K"+j+" ");}}
				if (i==12) {if ((i==12)&&(j==0)) {System.out.print("L ");}else {System.out.print("L"+j+" ");}}
				if (i==13) {if ((i==13)&&(j==0)) {System.out.print("M ");}else {System.out.print("M"+j+" ");}}
			}
		}
	}
	protected static void createYourGrid(JPanel grid) {
		
		for (int i=0; i<=row.length;i++) {
			if (i!=0) {
				System.out.println("");
			}
			for (int j=0;j<=col.length;j++) {
				if (i==0) {if ((i==0)&&(j==0))   {grid.add(mkButDisabled("  "));}else {grid.add(mkButDisabled(j+"  "));}}
				if (i==1) {if ((i==1)&&(j==0))   {grid.add(mkButDisabled("A "));}else {grid.add(mkBut("A"+j+" "));}}
				if (i==2) {if ((i==2)&&(j==0))   {grid.add(mkButDisabled("B "));}else {grid.add(mkBut("B"+j+" "));}}
				if (i==3) {if ((i==3)&&(j==0))   {grid.add(mkButDisabled("C "));}else {grid.add(mkBut("C"+j+" "));}}
				if (i==4) {if ((i==4)&&(j==0))   {grid.add(mkButDisabled("D "));}else {grid.add(mkBut("D"+j+" "));}}
				if (i==5) {if ((i==5)&&(j==0))   {grid.add(mkButDisabled("E "));}else {grid.add(mkBut("E"+j+" "));}}
				if (i==6) {if ((i==6)&&(j==0))   {grid.add(mkButDisabled("F "));}else {grid.add(mkBut("F"+j+" "));}}
				if (i==7) {if ((i==7)&&(j==0))   {grid.add(mkButDisabled("G "));}else {grid.add(mkBut("G"+j+" "));}}
				if (i==8) {if ((i==8)&&(j==0))   {grid.add(mkButDisabled("H "));}else {grid.add(mkBut("H"+j+" "));}}
				if (i==9) {if ((i==9)&&(j==0))   {grid.add(mkButDisabled("I "));}else {grid.add(mkBut("I"+j+" "));}}
				if (i==10) {if ((i==10)&&(j==0)) {grid.add(mkButDisabled("J "));}else {grid.add(mkBut("J"+j+" "));}}
				if (i==11) {if ((i==11)&&(j==0)) {grid.add(mkButDisabled("K "));}else {grid.add(mkBut("K"+j+" "));}}
				if (i==12) {if ((i==12)&&(j==0)) {grid.add(mkButDisabled("L "));}else {grid.add(mkBut("L"+j+" "));}}
				if (i==13) {if ((i==13)&&(j==0)) {grid.add(mkButDisabled("M "));}else {grid.add(mkBut("M"+j+" "));}}
			}
		}
	}
protected static void createEnnemyGrid(JPanel grid) {
		
		for (int i=0; i<=row.length;i++) {
			if (i!=0) {
				System.out.println("");
			}
			for (int j=0;j<=col.length;j++) {
				if (i==0) {if ((i==0)&&(j==0))   {grid.add(mkButDisabled("  "));}else {grid.add(mkButDisabled(j+"  "));}}
				if (i==1) {if ((i==1)&&(j==0))   {grid.add(mkButDisabled("A "));}else {grid.add(mkBut2("A"+j+" "));}}
				if (i==2) {if ((i==2)&&(j==0))   {grid.add(mkButDisabled("B "));}else {grid.add(mkBut2("B"+j+" "));}}
				if (i==3) {if ((i==3)&&(j==0))   {grid.add(mkButDisabled("C "));}else {grid.add(mkBut2("C"+j+" "));}}
				if (i==4) {if ((i==4)&&(j==0))   {grid.add(mkButDisabled("D "));}else {grid.add(mkBut2("D"+j+" "));}}
				if (i==5) {if ((i==5)&&(j==0))   {grid.add(mkButDisabled("E "));}else {grid.add(mkBut2("E"+j+" "));}}
				if (i==6) {if ((i==6)&&(j==0))   {grid.add(mkButDisabled("F "));}else {grid.add(mkBut2("F"+j+" "));}}
				if (i==7) {if ((i==7)&&(j==0))   {grid.add(mkButDisabled("G "));}else {grid.add(mkBut2("G"+j+" "));}}
				if (i==8) {if ((i==8)&&(j==0))   {grid.add(mkButDisabled("H "));}else {grid.add(mkBut2("H"+j+" "));}}
				if (i==9) {if ((i==9)&&(j==0))   {grid.add(mkButDisabled("I "));}else {grid.add(mkBut2("I"+j+" "));}}
				if (i==10) {if ((i==10)&&(j==0)) {grid.add(mkButDisabled("J "));}else {grid.add(mkBut2("J"+j+" "));}}
				if (i==11) {if ((i==11)&&(j==0)) {grid.add(mkButDisabled("K "));}else {grid.add(mkBut2("K"+j+" "));}}
				if (i==12) {if ((i==12)&&(j==0)) {grid.add(mkButDisabled("L "));}else {grid.add(mkBut2("L"+j+" "));}}
				if (i==13) {if ((i==13)&&(j==0)) {grid.add(mkButDisabled("M "));}else {grid.add(mkBut2("M"+j+" "));}}
			}
		}
	}/*
	private static JButton makeButtonFlat(JButton button) {
   	 button.setBorderPainted(false);
   	 button.setFocusPainted(false);
   	 button.setContentAreaFilled(false);
   	 return button;
    }*/

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
            b.setBackground(Color.GREEN);}
        @Override
        public void mouseExited(MouseEvent e) {b.setBackground(background);}
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
            b.setBackground(Color.RED);}
        @Override
        public void mouseExited(MouseEvent e) {b.setBackground(background);}
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
	public static void main(String [] args) {
		testGrid();
	}
}
