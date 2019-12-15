package model;

import java.util.HashMap;

/**
 * This class describes a unit and all its attributes and methods.
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
     * 
     * @param name {String} - the name of the unit
     * @param size {int} - the number of cells on the unit is placed 
     * @param counterBonus {int} - the amount of turns to wait in between 
     *                             two special shots associated with this unit
     */
    public Unit(String name, int size, int counterBonus) {
        this.name = name;
        this.size = size;
        this.isAlive = true;
        this.counterBonus = counterBonus+1;
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
    public void initCoordState(String [] coords) {
    	for(int i= 0; i < coords.length; i++){
            coordState.put(coords[i],true);
        }
    }

    /**
     * Method that returns the name of the unit.
     * 
     * @return {String} - The name of the unit
     */
    public String getName() {
        return name;
    }

    /**
     * Method that returns the size of the unit.
     * The size of the unit is the total number off cells on which the unit is placed.
     *  
     * @return {int} - the size of the unit 
     */
    public int getSize() {
        return size;
    }

    /**
     * Method that returns the general status of the unit. 
     * It can either be alive or dead.
     * 
     * @return {boolean} - returns true if the unit is alive, false otherwise
     */
    public boolean getIsAlive(){
    	return isAlive;
    }

    /**
     * Method that returns the state of one cell on which the unit is placed.
     * The state can either be true if the cell is not hit or false if the cell is hit.
     * 
     * @param key {String} - The coordinate of the cell of which you want to know the state
     * @return {boolean} - The state of the cell
     */
    public boolean getCoordState(String key) {
    	return coordState.get(key);
    }

    /**
     * Method that returns the state of the bonus of the unit.
     *  
     * @return {boolean} - the state of the bonus, true: enabled, false: disabled
     */
    public boolean getSwitchStateBonus(){
    	return stateBonus;
    }
    
    /**
     * Method that checks if the user can or cannot use the bonus. 
     * This method is called whenever the user shoots with the bonus associated with this unit,
     * the counter is therefor decremented at every call and reset if the bonus is available. 
     * 
     * @return {boolean} - the new state of the bonus 
     */
    public boolean getStateBonus(){
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

    /**
     * Method that changes the state of the given cell coordinate of the unit.
     * Checks if the unit is still alive after being shot, if not, sets the isAlive attribute to false.
     * 
     * @param key {String} - the coordinate of the cell of which the state needs to be changed
     */  
    public void setCoordState(String key) {
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
     * Method that sets the state of the bonus to false.
     */
    public void setSwitchStateBonus(){
    	stateBonus = false;
    }

}
