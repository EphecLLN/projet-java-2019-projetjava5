/**
 * @author Martin Perdaens
 */
package model;

import java.util.HashMap;

/**
 * This class discribes a unit with its name, size and status.
 */
public class Unit {

    private String name;
    private int size;
    private boolean isAlive;
    private int counterBonus;
    private boolean stateBonus;                                         
    private int counterBonusMax;
    protected HashMap<String, Boolean> coordState;

    /**
     * Constructor
     */
    public Unit(String name, int size, int counterBonus) {
        this.name = name;
        this.size = size;
        this.isAlive = true;
        this.counterBonus = counterBonus;
        this.counterBonusMax = counterBonus;
        this.stateBonus = true;
        this.coordState = new HashMap<String, Boolean>();
    }

    /**
     * Method that populates the unit's HashMap and sets all the values tu true.
     * This means that each cell of the unit is not hit.
     * 
     * @param coords {String[]} - Array of all the coordinates on which the unit is placed 
     */    
    protected void initCoordState(String [] coords) {
    	for(int i= 0; i < coords.length; i++){
            coordState.put(coords[i],true);
        }
    }

    /**
     * Method that returns the name of the unit.
     * 
     * @return {String} - The name of the unit
     */
    protected String getName() {
        return name;
    }

    /**
     * Method that returns the size of the unit.
     * The size of the unit is the total number off cells on which the unit is placed.
     *  
     * @return {int} - the size of the unit 
     */
    protected int getSize() {
        return size;
    }

    /**
     * Method that returns the state of one cell on which the unit is placed.
     * The state can either be true if the cell is not hit or false if the cell is hit.
     * 
     * @param key {String} - The coordinate of the cell of which you want to know the state
     * @return {boolean} - The state of the cell
     */
    protected boolean getCoordState(String key) {
    	return coordState.get(key);
    }

    /**
     * Method that changes the state of the given cell cooridante of the unit.
     * Checks if the unit is still alive after being shot, if not, sets the isAlive attribute to false.
     * 
     * @param key {String} - the coordinate of the cell of which the state needs to be changed
     */  
    protected void setCoordState(String key) {
    	coordState.replace(key, false);
    	for (boolean cellValue : coordState.values()) {
    		if(cellValue){
                isAlive = true;
                break;
            }
            isAlive = false;
        }
    }

    /**
     * Method that returns the general status of the unit. 
     * It can either be alive or dead.
     * 
     * @return {boolean} - returns true if the unit is alive, false otherwise
     */
    protected boolean getIsAlive(){
    	return isAlive;
    }

    /**
     * Faire la javaDoc //TODO
     * @return {boolean} - change the value of stateBonus
     */
    protected boolean setSwitchStateBonus(){
        return stateBonus = false;
    }
    
    /**
     * Method that dectect the status of bonus
     * It can either be active or disable
     * 
     * @return {boolean} - retrun true if the bonus is active, false not
     */
    protected boolean getStateBonus(){
            if(!stateBonus){
                counterBonus--;
                if(counterBonus == 0){
                    stateBonus = true;
                    counterBonus = counterBonusMax;
                }
                else{
                    stateBonus = false;
                }
            }
            else{
                stateBonus = true;
            }   
        return stateBonus;
    }

}
