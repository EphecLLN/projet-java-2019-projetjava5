/**
 * @author Martin Michotte
 * @date 23/11/2019
 */

package controller;

import java.io.Console;

import model.*;
import view.*;


/**
 * Controller class //TODO
 * 
 */
public class PlayerController {
	private PlayerModel model;
    private PlayerView view = null;

	public PlayerController(PlayerModel model) {
		this.model = model;
	}

	public void addView(PlayerView view) {
		this.view = view;
	}

    /**
     * Method that intercepts the incomming message from the client and checks for input errors.
     * If no errors are detected, the method returns the complete list of coordinates on which the unit is placed.
     * Otherwise, the user is asked to enter valid coordinates. 
     * 
     * @return {String[]} the complete list of coords of the unit that is being placed
     */
	public String[] PlaceUnitControl(){
		String command, userInput, coord1, coord2;
        String[] unitCoords;
        int[] coord1Index, coord2Index;
        boolean isPlaced = false;
        int numberOfRows, numberOfCols, unitSize;
        int failCount = 0;
		
		command = model.player.getFormClient();
		unitSize = Integer.valueOf(command.split("-")[1]);
		unitCoords = new String[unitSize];
	
		while (!isPlaced) {
            userInput = model.player.getFormClient();
            try {
                coord1 = userInput.split(" ")[0]; //retreives the top-left coordinate
                coord2 = userInput.split(" ")[1]; //retreives the bottom-rigth coordinate
                coord1Index = model.player.getMyGrid().getCoordIndex(coord1);
                coord2Index = model.player.getMyGrid().getCoordIndex(coord2);
                numberOfRows = coord2Index[0] - coord1Index[0] + 1;
                numberOfCols = coord2Index[1] - coord1Index[1] + 1;
                // check if input is correct and add if place is empty:
                int k = 0;
                if (numberOfRows * numberOfCols == unitSize) {
                    for (int i = 0; i < numberOfRows; i++) {
                        for (int j = 0; j < numberOfCols; j++) {
                            unitCoords[k] = model.player.getMyGrid().rowNames[coord1Index[0] + i] + model.player.getMyGrid().colNames[coord1Index[1] + j];
                            k++;
                        }
                    }
                    for (int i = 0; i < unitCoords.length; i++) {
                        if (model.player.getMyGrid().getGridCell(unitCoords[i]) != null) {
                            isPlaced = false;
                            break;
                        } else {
                            isPlaced = true;
                        }
                    }

                    if (isPlaced) {
						model.player.sendToClient("Rem");
						model.player.sendToClient("3");
						break;
                    } else {
                        model.player.sendToClient("Rem");
                        model.player.sendToClient(""+(failCount + 1));
						model.player.sendToClient("Q?");
						model.player.sendToClient("C-");
                        model.player.sendToClient("Input not valid. Units can not overlap eachother. Please enter valid input\n");
                        failCount = 1;
                    }
                } else {
                    isPlaced = false;
                    model.player.sendToClient("Rem");
                    model.player.sendToClient(""+(failCount + 1));
					model.player.sendToClient("Q?");
					model.player.sendToClient("C-");
                    model.player.sendToClient("Input not valid. Please enter valid input\n");
                    failCount = 1;
                }
            } catch (Exception e) {
                isPlaced = false;
                model.player.sendToClient("Rem");
                model.player.sendToClient(""+(failCount + 1));
				model.player.sendToClient("Q?");
				model.player.sendToClient("C-");
                model.player.sendToClient("Input not valid. Please enter valid input\n");
                failCount = 1;
            }
        }
		return unitCoords;
    }
    

    /**
     * Method that ask the client a gicen question and checks if the input is 
     * valid depending on the shotType.
     * 
     * @param question {String} - The question that needs to be asked the client
     * @param shotType {String} - The type of shot that is currently used
     * @return {String} - The validated coordinate(s) (if more than 1, separated by ';')  
     */
    public String askForCoord(String question,String shotType){
        boolean controlPassed = false;
        model.player.sendToClient("Rem"); model.player.sendToClient("3");
        model.player.sendToClient("Q?");
        model.player.sendToClient("C-");
        model.player.sendToClient(question);
        String shotCoord = model.player.getFormClient();
        int[] coord;
    
        while(!controlPassed){
            switch(shotType){
                case "S":
                    coord = model.player.getMyGrid().getCoordIndex(shotCoord);
                    if(coord[0] >= 0 && coord[1] >= 0){ //Coord is in range
                        controlPassed = true;
                    }
                    else{
                        model.player.sendToClient("Q?");
                        model.player.sendToClient("C-");
                        model.player.sendToClient("Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = model.player.getFormClient();
                        model.player.sendToClient("Rem"); model.player.sendToClient("2");
                    }
                    break;

                case "A":
                    coord = model.player.getMyGrid().getCoordIndex(shotCoord);
                    if(coord[0] >= 0 && coord[1] >= 0){  
                        model.player.sendToClient("Q?");
                        model.player.sendToClient("C-");
                        model.player.sendToClient("Enter the direction of the airstrike. H : Horizontal;  any other key : Vertical \n");
                        String direction = model.player.getFormClient();
                        model.player.sendToClient("Rem"); model.player.sendToClient("2");
                        shotCoord ="";
                        for(int i=-3;i<4;i++){
                            if(direction.equals("H")) {
                                try{
                                    shotCoord += model.player.getMyGrid().rowNames[coord[0]]+model.player.getMyGrid().colNames[coord[1]+i]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }
                            }
                            else {
                                try{
                                    shotCoord += model.player.getMyGrid().rowNames[coord[0]+i]+model.player.getMyGrid().colNames[coord[1]]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }       
                            }
                        }
                        controlPassed = true;
                    }
                    else{
                        model.player.sendToClient("Q?");
                        model.player.sendToClient("C-");
                        model.player.sendToClient("Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = model.player.getFormClient();
                        model.player.sendToClient("Rem"); model.player.sendToClient("2");
                    }                
                    break;

                case "D":
                    break;

                case "B":
                    coord = model.player.getMyGrid().getCoordIndex(shotCoord);
                    if(coord[0] >= 0 && coord[1] >= 0){ //Coord is in range
                        shotCoord ="";
                        for(int i =-1; i<2;i++){
                            for(int j =-1; j<2;j++){
                                try{
                                    shotCoord += model.player.getMyGrid().rowNames[coord[0]+i]+model.player.getMyGrid().colNames[coord[1]+j]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }  
                            }
                        }
                        controlPassed = true;
                    }
                    else{
                        model.player.sendToClient("Q?");
                        model.player.sendToClient("C-");
                        model.player.sendToClient("Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = model.player.getFormClient();
                        model.player.sendToClient("Rem"); model.player.sendToClient("2");
                    }
                    break;
            }
        }

        model.player.sendToClient("Rem"); model.player.sendToClient("2");
        return shotCoord;
    }

}