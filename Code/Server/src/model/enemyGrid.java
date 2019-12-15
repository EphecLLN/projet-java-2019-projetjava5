package model;

import java.util.HashMap;

/**
 * This class inherits from the super class "Grid".
 * This class lets us create a grid that is representing the opponent's grid 
 * in which the player will be shooting and observing his potential hits or misses.
 * 
 * Each grid cell will by default be populate with an integer equal to 0.
 * When the player shoots in the grid, 3 outcomes are possible:
 * hit -> cell value is set to 1
 * destroyed -> cell value is set to 1
 * no hit -> cell value is set to -1 
 * 
 * A cell value can therefor have 3 values :
 * 0  -> not shot yet  
 * 1  -> shot & hit
 * 2  -> destroyed
 * -1 -> shot but no hit
 *  
 */
public class enemyGrid extends Grid {

    private HashMap<String, Integer> gridCells = new HashMap<String, Integer>();

    /**
     * Constructor - populate hashmap with the grid cells 
     * and set all cell-values to 0 -> not shot
     */
    public enemyGrid() {
        for (String rowName : super.getRowNames()) {
            for (String colName : super.getColNames()) {
                gridCells.put(rowName + colName, 0);
            }
        }
    }

    /**
     * Method that sets the value of a given grid cell to a certain state. 
     * The state is represented by an integer, see class description for more information
     * 
     * @param coord {String} - The coordinate of the grid cell, ex: "H4"
     * @param shot  {int} : 0, 1, 2 or -1 representing the state of the given cell
     */
    public void setGridCell(String coord, int shot) {
        gridCells.replace(coord, shot);
    }

    /**
     * Method that returns the state of a given grid cell.
     * returns 0 if the cell has not been shot,
     * returns 1 if the cell has been shot and hit
     * return 2 if the cell has been destroyed
     * returns -1 if the cell has been shot but no hit
     * 
     * @param coord {String} - The coordinate of the grid cells, ex: "H4"
     * @return {int} - The state of the given cell
     */
    public int getGridCell(String coord) {
        return gridCells.get(coord);
    }


    /**
     * Method that returns the whole gridCells HashMap 
     * @return {HashMap<String, Integer>} - returns ridCells HashMap 
     */
    public HashMap<String, Integer> getGridCells(){
        return gridCells;
    }

}