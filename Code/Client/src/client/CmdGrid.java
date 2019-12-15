package client;

/*
 ANSI terminal control: http://www.termsys.demon.co.uk/vtansi.htm
 Unicode chars: https://www.compart.com/fr/unicode/category/So
*/

/**
 * This class is used to create, display and manipulate the Cmd-line interface of the game.
 * 
 * !If user is on windows, be sure to follow the game instructions,
 * !otherwise this class will produce a lot of errors! 
 */
public class CmdGrid {


	protected final short rows = 13;
	protected final short cols = rows;

	private Grid grid = new Grid();

	protected final String[] rowNames = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M" };
	private final String gridHeader = "     1   2   3   4   5   6   7   8   9   10  11  12  13";
	private final String gridTop =    "   ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐";
	private final String gridLine =   "   ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤";
	private final String gridBottom = "   └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘";
	private final String gridCase = String.format(" %-2c|", ' ');

	protected final char escCode = 0x1B;
	protected final char fg_red = 31;
	protected final char fg_green = 32;
	protected final char bg_red = 41;
	protected final char bg_green = 42;

	//Escape characters tho control the cmdline display. => ! only works on unix systems !
	public static final String BLACK_FG     = "\u001B[30m";  
	public static final String RED_FG       = "\u001B[31m";
	public static final String GREEN_FG     = "\u001B[32m";
	public static final String BLUE_FG      = "\u001B[34m";
	public static final String PURPLE_FG    = "\u001B[35m";
	public static final String YELLOW_FG    = "\u001B[33m";
	public static final String RED_BG       = "\u001B[41m";
	public static final String GREEN_BG     = "\u001B[42m";
	public static final String BLUE_BG      = "\u001B[44m";
	public static final String PURPLE_BG    = "\u001B[45m";
	public static final String YELLOW_BG    = "\u001B[43m";
	public static final String RESET_COLOR  = "\u001B[0m";
	public static final String CLEAR_SCREEN = "\u001B[2J";
	public static final String CLEAR_DOWN   = "\u001B[J";
	public static final String CLEAR_LINE   = "\u001B[2K";
	public static final String HOME_CURSOR  = "\u001B[H";
	public static final String SAVE_CURSOR  = "\u001B7";
	public static final String TO_SAVED_CURSOR  = "\u001B8";
	public static final String MOVE_1_UP    = "\u001B[1A";

	/**
	 * Method that displays a AxA grid with row and column headers.
	 */
	protected void displayGrid() {
		System.out.print(CLEAR_SCREEN); 		//Clear the whole screen
		System.out.print(HOME_CURSOR);  		//set cursor to home 
		System.out.println("");
		System.out.println(GREEN_FG+"		     YOUR BATTLEGROUND"+RESET_COLOR); 	
		System.out.println(gridHeader);
		System.out.println(gridTop);
		for (int i = 0; i < rows; i++) {
			System.out.print(String.format("%s  |", rowNames[i]));
			for (int j = 0; j < cols - 1; j++) {
				System.out.print(gridCase);
			}
			System.out.println(gridCase);
			if (i != rows - 1) {
				System.out.println(gridLine);
			}
		}
		System.out.println(this.gridBottom);

		System.out.print(String.format("\u001B[%dA", 29)); 	// move cursor to row position
		System.out.print(String.format("\u001B[%dC", 60)); 	// move cursor to column position
		System.out.println(RED_FG+"		       ENEMY'S BATTLEGROUND"+RESET_COLOR);
		System.out.print(String.format("\u001B[%dC", 60)); 	// move cursor to column position
		System.out.println(gridHeader);
		System.out.print(String.format("\u001B[%dC", 60));	// move cursor to column position
		System.out.println(gridTop);
		System.out.print(String.format("\u001B[%dC", 60)); 	// move cursor to column position
		for (int i = 0; i < rows; i++) {
			System.out.print(String.format("%s  |", rowNames[i]));
			for (int j = 0; j < cols - 1; j++) {
				System.out.print(gridCase);
			}
			System.out.println(gridCase);
			System.out.print(String.format("\u001B[%dC", 60)); 	 // move cursor to column position
			if (i != rows - 1) {
				System.out.println(gridLine);
				System.out.print(String.format("\u001B[%dC", 60)); // move cursor to column position
			}
		}
		System.out.println(this.gridBottom);
		System.out.print(SAVE_CURSOR); // Save cursor position
	}

	/**
	 * Method that displays elements on a given grid based on the given parameters:
	 * 
	 * @param val {String} - The type of the element to display, this will affect what the user will see
	 * @param data {String} - The coordinate of where the element should be placed
	 * @param isOutGoing {boolean} - the grid in which the element should be placed,
	 * 								true: enemyGrid, false: myGrid
	 */
	protected void insertInGrid(String val, String data, boolean isOutGoing) {
		String coord = data.split("/")[0];
		
		int rowIndex = grid.getCoordIndex(coord)[0];
		int colIndex = grid.getCoordIndex(coord)[1];
		int gridSelect = 0;
		String str = String.format("");

		switch (val) {
			case "Unit":
				str = GREEN_BG + BLACK_FG + String.format(" %-2c", '۩');
				break;
			case "Hit":
				str = YELLOW_BG + BLACK_FG + String.format(" %-2c", '✠');
				break;
			case "noHit":
				str = BLUE_BG + BLACK_FG + String.format(" %-2c", '✽');
				break;
			case "Destroyed":
				str = RED_BG + BLACK_FG + String.format(" %-2c", '♰');
				break;
			default:
				break;
		}

		// insert in the enemy's grid
		if (isOutGoing) {
			gridSelect = 60;
		}
		// insert in your grid
		else {
			gridSelect = 0;
		}

		System.out.print(TO_SAVED_CURSOR); 									 				// move to saved cursor position
		System.out.print(String.format("\u001B[%dA", (2 * rows) - (2 * rowIndex)));    		// move cursor to row position
		System.out.print(String.format("\u001B[%dC", 4 + (4 * colIndex) + gridSelect)); 	// move cursor to column position
		System.out.print(str);
		System.out.print(RESET_COLOR); 														// reset color
		System.out.print(String.format("|"));
		System.out.print(TO_SAVED_CURSOR);													// move to saved cursor position
	}

	/**
     * Method that removes a given number of lines 
	 * starting from the bottom of the cmd-line interface.
	 * 
     * @param numberOfLines {int} - the number of lines to delete
     */
    protected void removeLines(int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {
            System.out.print(MOVE_1_UP); 	// Move Up 1 line
            System.out.print(CLEAR_LINE); 	// clear the current line
        }
	}
	
	/**
     * Method that deletes all lines from the saved cursor position 
	 * to the bottom of the cmd-line interface.
     */
    protected void clearDown() {
		System.out.print(TO_SAVED_CURSOR); 
		System.out.print(CLEAR_DOWN);
		System.out.print(TO_SAVED_CURSOR);
    }

}
