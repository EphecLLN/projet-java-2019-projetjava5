package model;

import java.util.HashMap;

/**
 * This class inherits from the super class "Grid".
 * This class lets us create a grid that is representing the player's own grid 
 * in which he will place his own units and observe the shots from his opponent. 
 * 
 * Each grid cell will by default be populate with a null object.
 * When the player will be placing his units, each unit will be placed in each cell it is made of. 
 * 
 * Example: an Airport unit (which is 2x4 cells) is placed in cells : 
 * B3 B4 B5 B6
 * C3 C4 C5 C6 
 * Each of these cells will contain the Airport unit (and therefor its properties). 
 *  
 */
public class myGrid extends Grid {

    private HashMap<String, Unit> gridCells = new HashMap<String, Unit>();

    /**
     * Constructor - populate hashmap with each grid cell as key and 
     * null for every value (no units are placed on the grid).
     */
    public myGrid() {
        for (String rowName : super.getRowNames()) {
            for (String colName : super.getColNames()) {
                gridCells.put(rowName + colName, null);
            }
        }
    }

    /**
     * Method that sets the value of a given grid cell to a specified Unit 
     * 
     * @param coord {String} - The coordinate of the grid cell, ex: "H4"
     * @param unit  {Unit} - The unit that is placed on that cell
     */
    public void setGridCell(String coord, Unit unit) {
        gridCells.replace(coord, unit);
    }

    /**
     * Method that returns the unit that is placed in a grid cell,
     * returns null if no unit is present. 
     * 
     * @param coord {String} - The coordinate of the grid cell, ex: "H4"
     * @return {Unit} - The unit that is positioned in that cell
     */
    public Unit getGridCell(String coord) {
        return gridCells.get(coord);
    }

    /**
     * Method that returns the whole gridCells HashMap 
     * @return {HashMap<String, Integer>} - returns ridCells HashMap 
     */
    public HashMap<String, Unit> getGridCells(){
        return gridCells;
    }

}
