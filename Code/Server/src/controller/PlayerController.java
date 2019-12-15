package controller;

import model.*;
import view.*;


/**
 * This class has the function of Controller in the MVC structure.
 * 
 * When a client sends a a crucial bit of information to the server, it goes 
 * through this class before going to the model.
 * 
 * If any wrong user inputs are detected, it re-ask the client to input correct data. 
 * The data only goes to the model if it has passed all the required checks. 
 *  
 * There are two main control methods:
 * -> PlaceUnitControl: checks for correct user input when he is placing the units
 * -> askForCoord: checks for correct user input when shooting 
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
     * Method that intercepts the incoming message from the client and checks for input errors.
     * If no errors are detected, the method returns the complete list of coordinates on which the unit is placed.
     * Otherwise, the user is asked to enter valid coordinates. 
     * 
     * @return {String[]} the complete list of coords of the unit that is being placed
     */
	public String[] PlaceUnitControl(){
		String unitName, userInput, coord1, coord2;
        String[] responseFromClient, unitCoords;
        int[] coord1Index, coord2Index;
        boolean isPlaced = false;
        int numberOfRows, numberOfCols, unitSize;
        int failCount = 0;
	
		while (!isPlaced) {
            responseFromClient = model.player.getFormClient().split("-");
            unitName = responseFromClient[1];
		    unitSize = Integer.valueOf(responseFromClient[2]);
		    unitCoords = new String[unitSize];
            userInput = model.player.getFormClient();

            try {
                coord1 = userInput.split(" ")[0]; //retrieves the top-left coordinate
                coord2 = userInput.split(" ")[1]; //retrieves the bottom-right coordinate
                coord1Index = model.player.getMyGrid().getCoordIndex(coord1);
                coord2Index = model.player.getMyGrid().getCoordIndex(coord2);
                numberOfRows = coord2Index[0] - coord1Index[0] + 1;
                numberOfCols = coord2Index[1] - coord1Index[1] + 1;
                // check if input is correct and add if place is empty:
                int k = 0;
                if (numberOfRows * numberOfCols == unitSize) {
                    for (int i = 0; i < numberOfRows; i++) {
                        for (int j = 0; j < numberOfCols; j++) {
                            unitCoords[k] = model.player.getMyGrid().getRowNames()[coord1Index[0] + i] + model.player.getMyGrid().getColNames()[coord1Index[1] + j];
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
                        return unitCoords;
                    } else {
                        model.player.sendToClient("Rem");
                        model.player.sendToClient(""+(failCount + 3));
						model.player.sendToClient("U-"+unitName+"-"+unitSize+"-Input not valid. Units can not overlap each other. Please enter valid input\n");
                        failCount = 1;
                    }
                } else {
                    isPlaced = false;
                    model.player.sendToClient("Rem");
                    model.player.sendToClient(""+(failCount + 3));
					model.player.sendToClient("U-"+unitName+"-"+unitSize+"-Input not valid. Please enter valid input\n");
                    failCount = 1;
                }
            } catch (Exception e) {
                isPlaced = false;
                model.player.sendToClient("Rem");
                model.player.sendToClient(""+(failCount + 3));
				model.player.sendToClient("U-"+unitName+"-"+unitSize+"-Input not valid. Please enter valid input\n");
                failCount = 1;
            }
        }
		return null;
    }
    

    /**
     * Method that ask the client a given question and checks if the input is 
     * valid depending on the shotType.
     * 
     * @param question {String} - The question that needs to be asked the client
     * @param shotType {String} - The type of shot that is currently used
     * @return {String} - The validated coordinate(s) (if more than 1, separated by ';')  
     */
    public String askForCoord(String shotType){
        boolean controlPassed = false;
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
                        model.player.sendToClient("Rem"); model.player.sendToClient("2");
                        model.player.sendToClient("S-C-ND-Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = model.player.getFormClient();
                        model.player.sendToClient("Rem"); model.player.sendToClient("1");
                    }
                    break;

                case "A":
                    coord = model.player.getMyGrid().getCoordIndex(shotCoord);
                    if(coord[0] >= 0 && coord[1] >= 0){  
                        model.player.sendToClient("S-D-ND-NC");
                        String direction = model.player.getFormClient();
                        model.player.sendToClient("Rem"); model.player.sendToClient("2");
                        shotCoord ="";
                        for(int i=-3;i<4;i++){
                            if(direction.equals("H")) {
                                try{
                                    shotCoord += model.player.getMyGrid().getRowNames()[coord[0]]+model.player.getMyGrid().getColNames()[coord[1]+i]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }
                            }
                            else {
                                try{
                                    shotCoord += model.player.getMyGrid().getRowNames()[coord[0]+i]+model.player.getMyGrid().getColNames()[coord[1]]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }       
                            }
                        }
                        controlPassed = true;
                    }
                    else{
                        model.player.sendToClient("Rem"); model.player.sendToClient("2");
                        model.player.sendToClient("S-C-ND-Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = model.player.getFormClient();
                        model.player.sendToClient("Rem"); model.player.sendToClient("1");
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
                                    shotCoord += model.player.getMyGrid().getRowNames()[coord[0]+i]+model.player.getMyGrid().getColNames()[coord[1]+j]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }  
                            }
                        }
                        controlPassed = true;
                    }
                    else{
                        model.player.sendToClient("Rem"); model.player.sendToClient("2");
                        model.player.sendToClient("S-C-ND-Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = model.player.getFormClient();
                        model.player.sendToClient("Rem"); model.player.sendToClient("1");
                    }
                    break;
            }
        }

        model.player.sendToClient("Rem"); model.player.sendToClient("2");
        return shotCoord;
    }

}