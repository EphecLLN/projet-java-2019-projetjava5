/**
 * @author Morgan Valentin
 * @date 15/11/2019
 */

package server;

import java.util.Arrays;

/**
 * This class is a super class for creating different grid objects. 
 * A general grid object is only represented by rows and columns with apropriate names.
 * Each cell is therefor represented by a combination of a row-name and a column-name wich correspond to a row-index and column-index.
 * 
 * Example: cell "H4" is located in row "H" at column "4" wich are respectiveley at row index 7 and column-index 3 (index start at 0)
 *  
 */
public class Grid {

    protected final String[] rowNames = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M" };
    protected final String[] colNames = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" };

    /**
     * Method that converts a userfriendly cell coordinate such as "H4"
     * into a uusable cell-indexes such as [7,3].
     *
     * @param str {String} - cell coordinate in userfriendly format. ex: "H4"
     * @return {int[]} - cell coordinate in array format. ex: [7,3]
     */
    protected int[] getCoordIndex(String str) {

        String rowName = str.substring(0, 1);// "H4" => Takes from index 0 intil 1 not included => "H"
        String colName = str.substring(1);// "H4" => Takes from index 1 until end => "4"
        int[] coordIndex = { -1, -1 };// initialised to {-1,-1} => if user enters incorrect row and colum => Error catched if coordIndex has -1 in it.

        for (int i = 0; i < rowNames.length; i++) {
            if (rowName.compareTo(rowNames[i]) == 0) {//If "H" == line "H" (example)
                coordIndex[0] = i;
                break;
            }
        }

        for (int i = 0; i < colNames.length; i++) {
            if (colName.compareTo(colNames[i]) == 0) {//if "4" == col "4" (example)
                coordIndex[1] = i;
                break;
            }
        }

        return coordIndex;
    }

    // for debugging only - do nut run if not for debugging
    public static void main(String[] args) {
        Grid g = new Grid();
        System.out.println(Arrays.toString(g.getCoordIndex("H4")));
    }

}
