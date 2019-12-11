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
    PlayerController playerContr;
    PlayerViewCmd cmd;

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

    //!---------------------------------------------------------------------------------
    //!                                  Constructor
    //!---------------------------------------------------------------------------------

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
        playerContr = new PlayerController(model);
        cmd  = new PlayerViewCmd(model, playerContr);
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


    //!---------------------------------------------------------------------------------
    //!                         Communication with Client
    //!---------------------------------------------------------------------------------

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

    //!---------------------------------------------------------------------------------
    //!                                Placing of units
    //!---------------------------------------------------------------------------------

     /**
     * Function that asks the player to place a particular unit on the grid and saves its position.
     * 
     * @param unit {Unit} - The unit that needs to be placed 
     */
    private void unitPlacer(Unit unit) {
    
        sendToClient("Q?");
        sendToClient("U-"+unit.getSize());
        sendToClient("\nWhere do you want to place the " + unit.getName()+ "? Enter top-left and bottom-right coordinates separated by a whitespace.\n");
        String[] unitCoords = playerContr.PlaceUnitControl();
        
        unit.initCoordState(unitCoords);
        for (int i = 0; i < unitCoords.length; i++) {
            myGrid.setGridCell(unitCoords[i], unit);
            model.Changed();
            model.toNotify();
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
        sendToClient("C-");
        sendToClient("All units are placed, press 'enter' to start playing.\n");
        getFormClient();
        sendToClient("Rem");
        sendToClient("2");
        isReady = true;
    }

    //!---------------------------------------------------------------------------------
    //!                                 Shooting
    //!---------------------------------------------------------------------------------

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
        if(false){                // if(RadarTower.getIsAlive()){ 
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
        sendToClient("C-");
        sendToClient("What type of shot do you want to use?     Available: "+ getAvailableShotTypes() +"\n"+
        "S : Singleshot; A : Airstrike; D : Radar discovery; B : Bigshot; R : Rocketstrike\n");
        shotType = getFormClient();
        
        while(!shotExecuted){
            if(getAvailableShotTypes().contains(shotType)){
                switch (shotType) {
                    case "S":
                        checkForHit(playerContr.askForCoord("Enter the coordinate of the shot. Ex: H4 \n",shotType));
                        shotExecuted = true;
                        break;
        
                    case "A":
                        coords = playerContr.askForCoord("Enter the coordinate of the center of the airstrike. Ex: H4 \n",shotType);
                        coordsArray = coords.split(";");
                        for(String coord : coordsArray){
                            checkForHit(coord);
                            sleep(150);
                        }
                        shotExecuted = true;
                        Airport.setSwitchStateBonus();               
                        break;
        
                    case "D":
                        /////TODO => Will not be implemented
                        shotExecuted = true;
                        RadarTower.setSwitchStateBonus();               
                        break;
        
                    case "B":
                        coords = playerContr.askForCoord("Enter the coordinate of the central shot. Ex: H4 \n",shotType);
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
                    sendToClient("C-");
                    sendToClient("The shot-type you entered is not available. Use another one.\n");
                    shotType = getFormClient();
                }
                else{
                    sendToClient("Rem"); sendToClient(""+(failCount + 2));
                    sendToClient("Q?");
                    sendToClient("C-");
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

    //!---------------------------------------------------------------------------------
    //!                                    Playing  
    //!---------------------------------------------------------------------------------

    /**
     * This Method is the actual game-management,
     * The truns are being handed and the active-player is alowed to shoot.
     * 
     * Be aware -> this is method starts an infinite loop that can only be stopped if one of the players wins!
     * 
     * //TODO -> check if a player disconnects => will not be impemented 
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

    
    //!---------------------------------------------------------------------------------
    //!                               Getters & Setters
    //!---------------------------------------------------------------------------------

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

    //!---------------------------------------------------------------------------------
    //!                              Other methods
    //!---------------------------------------------------------------------------------

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


}
