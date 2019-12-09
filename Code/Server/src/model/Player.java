/**
 * @author Martin Michotte
 * @date 23/11/2019
 */

package model;

import java.io.*;
import java.net.*;

import controller.PlayerController;
import test.*;
import view.PlayerViewCmd;

import java.util.Arrays;

/**
 * This class discribes a Player with its attributes and methods. 
 * This class is created whenever a client connects to the server and it's asociated to that client. 
 * The game in itself is therefor a battle between two instances of this class. 
 * 
 * Once both instances are ready (all units have been placed), they autmatically start playing :
 * 
 * Beforhand: The turn is randombly given to one of the instances
 * -> The instance that has the turn ask the client to shoot while the other instance waits its turn
 * -> Once the shot is done, the instance gives his turn to the other
 * -> This process repaets itself until all the units of one of the instances are destroyed. 
 *  
 * This class is the actuel model of the game, since this class already inherits the Thread class, 
 * a workaround had to be implemented to ensure the model is Observable. -> SEE PlayerModel Class.
 * 
 */
public class Player extends Thread {

    PlayerModel model;

    String userName;
    private myGrid myGrid;
    private enemyGrid enemyGrid;
    private Unit Airport, RadarTower, HeadQuarter, RailwayGun, MMRL, Tank;
    private Unit[] units = new Unit[6];
    private final int NUMBER_OF_ROCKETS = 5;    //number of rockets to shoot on a rocket strike

    private String myKey = "";
    public boolean isReady = false;
    public boolean isMyTurn = false;

    private DataInputStream in; 
    private DataOutputStream out; 
    private Socket sock; 
    private static int numberOfClientsConnected=0;

    //Escape characters tho control the cmdline display. => ! only works on unix systems !
    public static final String RED_FG       = "\u001B[31m";
    public static final String GREEN_FG     = "\u001B[32m";
	public static final String BLUE_FG      = "\u001B[34m";
	public static final String PURPLE_FG    = "\u001B[35m";
	public static final String YELLOW_FG    = "\u001B[33m";
    public static final String RESET_COLOR  = "\u001B[0m";
    public static final String CLEAR_SCREEN = "\u001B[2J";
    public static final String HOME_CURSOR  = "\u001B[H";

    /**
     * Constructor
     * 
     * Creates all the necessary "tools" and  instantiate the required classes.
     * Associate this instance of the Player class to a client through the Players HashMap from the server
     * 
     * @param sock {Socket} - the socket on which the client is connected to the server
     * @param in {DataInputStream} - the inputstream on which we can retreive data from the client
     * @param out {DataOutputStream} - - the outputstream on which we send data to the client
     */
    public Player(Socket sock, DataInputStream in, DataOutputStream out)  
    { 

        model = new PlayerModel(this);
        PlayerController playerContr = new PlayerController(model);
        PlayerViewCmd cmd = new PlayerViewCmd(model, playerContr);
        playerContr.addView(cmd);
        
        //Creating both grids
        myGrid = new myGrid();
        enemyGrid = new enemyGrid();

        //Creating the units and adding them to the units array.
        Airport = new Unit("Airport (2x4)", 8, 7);
        RadarTower = new Unit("Radar Tower (2x3)", 6, 5);
        HeadQuarter = new Unit("HeadQuarter (2x2)", 4, 0);
        RailwayGun = new Unit("Railway Gun (1x6)", 6, 8);
        MMRL = new Unit("MMRL (2x2)", 4, 3);
        Tank = new Unit("Tank (1x2)", 2, 0);
        units[0] = Airport;
        units[1] = RadarTower;       
        units[2] = HeadQuarter;
        units[3] = RailwayGun;
        units[4] = MMRL;
        units[5] = Tank;

        //Retreiving connection information
        this.sock = sock;
        this.in = in; 
        this.out = out;
        numberOfClientsConnected++;

        //Get own identifier 
        if(Server.Players.get("P1") == null){
            myKey = "P1";
            Server.Players.replace("P1",this);
        }
        else{
            myKey = "P2";
            Server.Players.replace("P2",this);
        }
        
    } 

    /**
     * This method prints the client information associated to this instance of the Player class on the server cmdLine
     */
    private void getClientInfo(){
        long id = Thread.currentThread().getId(); 

        userName = getFormClient();
        System.out.println("A new "+ PURPLE_FG +"client"+ BLUE_FG +" \""+userName+"\""+ RESET_COLOR +" with id" + RED_FG +" ("+id+")"+
                            RESET_COLOR +" joined via " + YELLOW_FG + sock.getLocalAddress().toString().replaceAll("/", "")+ RESET_COLOR);
        System.out.println("-------------------------------------------------------------------------");
    }

    /**
     * Method that returns the reference of the other client's Player instance 
     * 
     * @return {Player} - The object Player of the other client 
     */
    public Player otherPlayer(){
        if(myKey.equals("P1")){
            return Server.Players.get("P2");
        }
        else{
            return Server.Players.get("P1");
        }
    }

    /**
     * Method that lets this instance sleep for X miliseconds.
     * 
     * @param ms {int} - the time this thread needs to sleep in miliseconds
     */
    private void sleep(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException e){
            System.out.println(e);
            System.out.println(RED_FG+ "Thread Error, game closed!" + RESET_COLOR);
        }
    }

    /**
     * Method that takes a string and tries to send it to the client.
     * 
     * @param str {String} - A String to send to the client 
     */
    public void sendToClient(String str){
        try{
            out.writeUTF(str);
        }
        catch(IOException e){
            System.out.println(e);
            System.out.println(RED_FG + "Oops the connection between the server and " + BLUE_FG + userName + RED_FG +" is broken, the game had to be closed!" + RESET_COLOR);
            System.exit(0);
        }
    }

    /**
     * Method that waits for string from the client and returns it when received. 
     * 
     * @return a string received from the server
     */
    public String getFormClient(){
        try{
            return in.readUTF();
        }
        catch(IOException e){
            System.out.println(RED_FG + "Oops the connection between the server and " + BLUE_FG + userName + RED_FG +" is broken, the game had to be closed!" + RESET_COLOR);
            System.exit(0);
        }
        return "";
    }


     /**
     * Function that asks the player to place a particular unit on the grid and saves its position.
     * 
     * @param unit {Unit} - The unit that needs to be placed 
     */
    private void unitPlacer(Unit unit) {
        String userInput, coord1, coord2;
        String[] unitCoords;
        int[] coord1Index, coord2Index;
        boolean isPlaced = false;
        int numberOfRows, numberOfCols;
        int failCount = 0;

        sendToClient("Q?");
        sendToClient("\nWhere do you want to place the " + unit.getName()+ "? Enter top-left and bottom-right coordinates separated by a whitespace.\n");

        unitCoords = new String[unit.getSize()];

        while (!isPlaced) {
            userInput = getFormClient();
            try {
                coord1 = userInput.split(" ")[0]; //retreives the top-left coordinate
                coord2 = userInput.split(" ")[1]; //retreives the bottom-rigth coordinate
                coord1Index = myGrid.getCoordIndex(coord1);
                coord2Index = myGrid.getCoordIndex(coord2);
                numberOfRows = coord2Index[0] - coord1Index[0] + 1;
                numberOfCols = coord2Index[1] - coord1Index[1] + 1;
                // check if input is correct and add if place is empty:
                int k = 0;
                if (numberOfRows * numberOfCols == unit.getSize()) {
                    for (int i = 0; i < numberOfRows; i++) {
                        for (int j = 0; j < numberOfCols; j++) {
                            unitCoords[k] = myGrid.rowNames[coord1Index[0] + i] + myGrid.colNames[coord1Index[1] + j];
                            k++;
                        }
                    }
                    for (int i = 0; i < unitCoords.length; i++) {
                        if (myGrid.getGridCell(unitCoords[i]) != null) {
                            isPlaced = false;
                            break;
                        } else {
                            isPlaced = true;
                        }
                    }

                    if (isPlaced) {
                        unit.initCoordState(unitCoords);
                        sendToClient("Rem");
                        sendToClient("3");
                        for (int i = 0; i < unitCoords.length; i++) {
                            myGrid.setGridCell(unitCoords[i], unit);
                            model.Changed();
                            model.toNotify();
                        }
                    } else {
                        sendToClient("Rem");
                        sendToClient(""+(failCount + 1));
                        sendToClient("Q?");
                        sendToClient("Input not valid. Units can not overlap eachother. Please enter valid input\n");
                        failCount = 1;
                    }
                } else {
                    isPlaced = false;
                    sendToClient("Rem");
                    sendToClient(""+(failCount + 1));
                    sendToClient("Q?");
                    sendToClient("Input not valid. Please enter valid input\n");
                    failCount = 1;
                }
            } catch (Exception e) {
                isPlaced = false;
                sendToClient("Rem");
                sendToClient(""+(failCount + 1));
                sendToClient("Q?");
                sendToClient("Input not valid. Please enter valid input\n");
                failCount = 1;
            }
        }
    }


    /**
     * Method that iterates through every unit of the player and sends it to the unitPlacer-method.
     * When all units are placed, the Player instance is ready to play. 
     */
    protected void placeUnits() {
        for(Unit u : units){
            if(u != null){                      //uniquement util de vérifier lorsque qu'on uttilise pas toutes les unités -> debug
                unitPlacer(u);
            }  
        }
        sendToClient("Q?");
        sendToClient("All units are placed, press 'enter' to start playing.\n");
        getFormClient();
        sendToClient("Rem");
        sendToClient("2");
        isReady = true;
    }


    /**
     * Method thet checks which sot-types are available for the player to use,
     * if a unit is destroyed or if it is too soon to re-use a certain shot-type, the shot is not available.
     * 
     * @return {String} - Returns a string containing the letters associated to the shot-types if they are available 
     * //TODO -> Implement shoot limtit 
     */
    protected String getAvailableShotTypes(){
        String availableShotTypes = "S ";
        if(Airport.getIsAlive()){
            if(Airport.getStateBonus()){
                availableShotTypes += "- A ";
            }
        }
        if(false){                // if(RadarTower.getIsAlive()){ //TODO
            if(RadarTower.getStateBonus()){
                availableShotTypes += "- D ";
            }
        }
        if(RailwayGun.getIsAlive()){
            if(RailwayGun.getStateBonus()){
                availableShotTypes += "- B ";
            }
        }
        if(MMRL.getIsAlive()){
            if(MMRL.getStateBonus()){
                availableShotTypes += "- R ";
            }
        }
        return availableShotTypes;
    }

    /**
     * Method that checks if the shot hits a unit of the other player 
     * and changes the model accordingly: //TODO - javadoc a complèter un peu plus 
     * 
     * @param shotCoord {String} - The coordinate of the shot
     */
    protected void checkForHit(String shotCoord){
        if(otherPlayer().myGrid.getGridCell(shotCoord) != null){            //their is a unit on the coordinate

            Unit enemyUnit = otherPlayer().myGrid.getGridCell(shotCoord);
            enemyUnit.setCoordState(shotCoord);

            if(enemyUnit.getIsAlive()){                                     //The unit is hit but not destroyed -> hit
                enemyGrid.setGridCell(shotCoord, 1);
                model.Changed();
                model.toNotify();
            }
            else{                                                           //The unit is hit and destroyed -> destroyed
                for ( String key : enemyUnit.coordState.keySet() ) {
                    enemyGrid.setGridCell(key, 2);
                    model.Changed();
                    model.toNotify();
                }
            }
        }
        else{                                                               //their is no unit on the coordinate -> no hit
            enemyGrid.setGridCell(shotCoord, -1);
            model.Changed();
            model.toNotify();
        }
    }



    /**
     * Method that ask the client a gicen question and checks if the input is 
     * valid depending on the shotType.
     * 
     * @param question {String} - The question that needs to be asked the client
     * @param shotType {String} - The type of shot that is currently used
     * @return {String} - The validated coordinate(s) (if more than 1, separated by ';')  
     */
    protected String askForCoord(String question,String shotType){
        boolean controlPassed = false;
        sendToClient("Rem"); sendToClient("3");
        sendToClient("Q?");
        sendToClient(question);
        String shotCoord = getFormClient();
        int[] coord;
    
        while(!controlPassed){
            switch(shotType){
                case "S":
                    coord = myGrid.getCoordIndex(shotCoord);
                    if(coord[0] >= 0 && coord[1] >= 0){ //Coord is in range
                        controlPassed = true;
                    }
                    else{
                        sendToClient("Q?");
                        sendToClient("Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = getFormClient();
                        sendToClient("Rem"); sendToClient("2");
                    }
                    break;

                case "A":
                    coord = myGrid.getCoordIndex(shotCoord);
                    if(coord[0] >= 0 && coord[1] >= 0){  
                        sendToClient("Q?");
                        sendToClient("Enter the direction of the airstrike. H : Horizontal;  any other key : Vertical \n");
                        String direction = getFormClient();
                        sendToClient("Rem"); sendToClient("2");
                        shotCoord ="";
                        for(int i=-3;i<4;i++){
                            if(direction.equals("H")) {
                                try{
                                    shotCoord += myGrid.rowNames[coord[0]]+myGrid.colNames[coord[1]+i]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }
                            }
                            else {
                                try{
                                    shotCoord += myGrid.rowNames[coord[0]+i]+myGrid.colNames[coord[1]]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }       
                            }
                        }
                        controlPassed = true;
                    }
                    else{
                        sendToClient("Q?");
                        sendToClient("Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = getFormClient();
                        sendToClient("Rem"); sendToClient("2");
                    }
                    break;

                case "D":
                    break;

                case "B":
                    coord = myGrid.getCoordIndex(shotCoord);
                    if(coord[0] >= 0 && coord[1] >= 0){ //Coord is in range
                        shotCoord ="";
                        for(int i =-1; i<2;i++){
                            for(int j =-1; j<2;j++){
                                try{
                                    shotCoord += myGrid.rowNames[coord[0]+i]+myGrid.colNames[coord[1]+j]+";";
                                }
                                catch(IndexOutOfBoundsException e){
                                    //Some of the shots will be outside of the grid (doesn't matter)
                                }  
                            }
                        }
                        controlPassed = true;
                    }
                    else{
                        sendToClient("Q?");
                        sendToClient("Coordinate out of range, please enter correct coordinate:\n");
                        shotCoord = getFormClient();
                        sendToClient("Rem"); sendToClient("2");
                    }
                    break;
            }
        }

        sendToClient("Rem"); sendToClient("2");
        return shotCoord;
    }


    /**
     * Method that asks the client to choose a shot-type and execute the shot.
     * Asks for valid shot-type if client inpur is not valid.  
     * 
     */
    protected void shoot() {
        String shotType, shotCoord, coords;
        String[] coordsArray;
        boolean shotExecuted = false;
        int failCount = 0;

        sendToClient("Q?");
        sendToClient("What type of shot do you want to use?     Available: "+ getAvailableShotTypes() +"\n"+
        "S : Singleshot; A : Airstrike; D : Radar discovery; B : Bigshot; R : Rocketstrike\n");
        shotType = getFormClient();
        
        while(!shotExecuted){
            if(getAvailableShotTypes().contains(shotType)){
                switch (shotType) {
                    case "S":
                        checkForHit(askForCoord("Enter the coordinate of the shot. Ex: H4 \n",shotType));
                        shotExecuted = true;
                        break;
        
                    case "A":
                        coords = askForCoord("Enter the coordinate of the center of the airstrike. Ex: H4 \n",shotType);
                        coordsArray = coords.split(";");
                        for(String coord : coordsArray){
                            checkForHit(coord);
                            sleep(150);
                        }
                        shotExecuted = true;
                        Airport.setSwitchStateBonus();               
                        break;
        
                    case "D":
                        //TODO -> pas urgent! 
                        shotExecuted = true;
                        RadarTower.setSwitchStateBonus();               
                        break;
        
                    case "B":
                        coords = askForCoord("Enter the coordinate of the central shot. Ex: H4 \n",shotType);
                        coordsArray = coords.split(";");
                        for(String coord : coordsArray){
                            checkForHit(coord);
                        }
                        shotExecuted = true;
                        RailwayGun.setSwitchStateBonus();     
                        break;
        
                    case "R":
                        sendToClient("Rem"); sendToClient("3");
                        for(int i = 0; i<NUMBER_OF_ROCKETS;i++){ 
                            shotCoord = myGrid.rowNames[(int)(Math.random()*(myGrid.rowNames.length-1))]+myGrid.colNames[(int)(Math.random()*(myGrid.rowNames.length-1))];
                            checkForHit(shotCoord);
                            sleep(500);
                        }
                        shotExecuted = true;
                        MMRL.setSwitchStateBonus(); 
                        break;
                }

            }
            else{
                String types = "S A D B R";
                if(types.contains(shotType)){
                    sendToClient("Rem"); sendToClient(""+(failCount + 2));
                    sendToClient("Q?");
                    sendToClient("The shot-type you entered is not available. Use another one.\n");
                    shotType = getFormClient();
                }
                else{
                    sendToClient("Rem"); sendToClient(""+(failCount + 2));
                    sendToClient("Q?");
                    sendToClient("Invalid input. Please enter valid shot-type.\n");
                    shotType = getFormClient();
                }
                failCount = 1;
            }

        }
    }
    

    /**
     * Method that checks if every unit of the adversary is destroyed, 
     * if yes -> this client has won and the game is terminated 
     * if no  -> the game continues 
     */
    protected void checkForWin(){
        boolean won = false;
        for(Unit u : otherPlayer().units){
            if(u != null){                  //uniquement util de vérifier lorsque qu'on uttilise pas toutes les unités -> debug
                if(u.getIsAlive()){
                    won = false;
                    break;
                }
                else{won=true;}
            }
        }
        if(won){
            sendToClient(GREEN_FG + "\n    YOU WON!    \n\n"+ RESET_COLOR);
            sendToClient("CLOSE");
            otherPlayer().sendToClient(RED_FG + "\n    YOU LOST!    \n\n"+ RESET_COLOR);
            otherPlayer().sendToClient("CLOSE");
            System.exit(0);
        }
    }

    /**
     * This Method is the actual game-management,
     * The truns are being handed and the active-player is alowed to shoot.
     * 
     * Be aware -> this is method starts an infinite loop that can only be stopped if one of the players wins!
     * 
     * //TODO -> check if a player disconnects
     */
    protected void play(){
        while(true){
            if(isMyTurn){
                otherPlayer().sendToClient("It's not your turn, waiting for "+ this.userName +" to play.");
                shoot();
                otherPlayer().sendToClient("\u001B[2K");
                otherPlayer().sendToClient("\u001B8");
                checkForWin();
                this.isMyTurn = false;
                otherPlayer().isMyTurn = true;
            }
            else{
                sleep(100);
            }
        }
    }

    /**
     * Method that is called on the start command from the server, 
     * this method launches the game in four phases:
     * 
     *  1) Initialisation of the UI 
     *  2) Initialisation -> let the client place his units on the grid
     *  3) Waits until both clients are ready to battle 
     *  4) Start the actual game between the two clients
     *  
     */
    @Override
    public void run()  { 
        getClientInfo();
        sendToClient("displayGrid");
        placeUnits();
        sendToClient("Waiting for other player\n");
        while(!Server.allPlayerConnected){
            try{Thread.sleep(100);}catch(InterruptedException ex){}
        };
        sendToClient("Rem");sendToClient("1");
        play();
    }

    
    //// Getters and setters : 

    /**
     * Method tht returns the myGrid instance
     * 
     * @return {myGrid} - returns the myGrid instancte
     */
    public myGrid getMyGrid(){
        return myGrid;
    }
    
    /**
     * Method tht returns the enemyGrid instance
     * 
     * @return {enemyGrid} - returns the enemyGrid instancte
     */
    public enemyGrid getEnemyGrid(){
        return enemyGrid;
    }


}
