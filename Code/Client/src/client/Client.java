package client;

import java.io.*; 
import java.net.*;
import java.util.Scanner; 
  
/**
 * @author Morgan Valentin
 * @Source code used to understand => https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
*/

/**
 * //TODO
 */
public class Client  { 

    private String name;
    private Scanner scn;
    private int port;
    private String ip;
    private Socket sock;
    protected DataInputStream in;
    protected DataOutputStream out;

    private CmdGridDisplay gridDisplay = new CmdGridDisplay();

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
     */
    public Client(){
        scn = new Scanner(System.in); 
    }
	
    /**
     * Method that checks if name (input from user) is between 4 and 12 chracters in length
     * @param name {String} - //TODO
     * @param scanner {Scanner} - //TODO
     */
	private void checkNameClient(String name,Scanner scanner) {
        while (name.length()<4 || name.length() >13) {
            System.out.println("Invalid input. Please re-enter a name (min 4 - max 12 :");
            name = scanner.nextLine();
        }
    }
    
    /**
     * Method thet initialises a connection with the server
     * 
     */
    private void InitConnection(){

        System.out.print(CLEAR_SCREEN);
        System.out.print(HOME_CURSOR);

        // Retrieving client's name from user input :
        System.out.println("What's your name ?");
        name = scn.nextLine();
        checkNameClient(name,scn);

        // Retrieving port from user input :
        System.out.println(PURPLE_FG+"Port number ? (press enter for default port: 5555)"+RESET_COLOR);
        String userStr = scn.nextLine();
            if(userStr.equals("")){
                port = 5555;
            }
            else{
                port = Integer.valueOf(userStr);
            }
            
        System.out.println(BLUE_FG+name+"> "+PURPLE_FG+port+RESET_COLOR);
        

        //Retrieving ip adress from user input :
        System.out.println(RED_FG+"IP address ? (nothing + enter = localhost)"+RESET_COLOR);
        ip = scn.nextLine();
        if (ip.length()==0) {ip = "localhost";}
        System.out.println(BLUE_FG+name+"> "+RED_FG+ip+RESET_COLOR);

        try{
        //Establish the connection with the server with IP and Port from user input
        System.out.println(BLUE_FG+"Waiting to connect to server..."+RESET_COLOR);
        sock = new Socket(ip, port);
        System.out.println(RED_FG+"Connected !"+RESET_COLOR);
        System.out.println("__________________________");

        // In and out streams => Information received (inputStream) and sent (outputStream) :
        in = new DataInputStream(sock.getInputStream()); 
        out = new DataOutputStream(sock.getOutputStream()); 

        out.writeUTF(name);//Sends client name to server

        }
        catch(Exception e){}
    }
    
    /**
     * Method that takes a string and tries to send it to the server.
     * 
     * @param str {String} - A String to send to the server 
     */
    private void sendToServer(String str){
        try{
            out.writeUTF(str);
        }
        catch(IOException e){
            //TODO
        }
    }

    /**
     * Method that waits for string from the server and returns it when received. 
     * 
     * @return a string received from the server
     */
    private String getFromServer(){
        try{
            return in.readUTF();
        }
        catch(IOException e){
            //TODO
        }
        return "";
    }


    /**
     * //TODO
     */
    private void listenToServer(){
        String strFromServer ="";
        String strToServer ="";

        while (true)  { 
            strFromServer = getFromServer(); 
            switch(strFromServer){
                case "displayGrid":
                    System.out.print("disp"); 
                    gridDisplay.displayGrid();
                    break;
                    
                case "Q?": //Server ask a question
                    String command = getFromServer();
                    if(command.split("-")[0].equals("U")){
                        System.out.print(getFromServer()); //print the question 
                        sendToServer(command);
                        strToServer = scn.nextLine();
                        sendToServer(strToServer);
                    }
                    else{
                        System.out.print(getFromServer()); //print the question 
                        strToServer = scn.nextLine();
                        sendToServer(strToServer);
                    }
                    break;

                case "insertUnit":
                    strFromServer = getFromServer();
                    gridDisplay.insertInGrid("Unit", strFromServer, false);
                    break;
                
                case "Hit": 
                    strFromServer = getFromServer();
                    gridDisplay.insertInGrid("Hit", strFromServer, true);
                    break;

                case "noHit":
                    strFromServer = getFromServer();
                    gridDisplay.insertInGrid("noHit", strFromServer, true);
                    break;
                
                case "Destroyed":
                    strFromServer = getFromServer();
                    gridDisplay.insertInGrid("Destroyed", strFromServer, true);
                    break;
                
                case "myDestroyed":
                    strFromServer = getFromServer();
                    gridDisplay.insertInGrid("Destroyed", strFromServer, false);
                    break;
                
                case "myHit": 
                    strFromServer = getFromServer();
                    gridDisplay.insertInGrid("Hit", strFromServer, false);
                    break;

                case "myNoHit":
                    strFromServer = getFromServer();
                    gridDisplay.insertInGrid("noHit", strFromServer, false);
                    break;

                case "Rem": //remove lines
                    strFromServer = getFromServer();
                    gridDisplay.removeLines(Integer.parseInt(strFromServer));
                    break;

                case "CLOSE": //remove lines
                    System.exit(0);
                    break;

                default:
                    System.out.print(strFromServer);
            }
        }

    }
    public static void main(String[] args){
    	try {
    		Client client = new Client();
            client.InitConnection();
            client.listenToServer();
    	}
        catch(NumberFormatException a) {
        	System.out.println(RED_FG+"FATAL ERROR :"+PURPLE_FG+" IP and Port must be Integer"+RESET_COLOR);
        }
    	catch(NullPointerException a) {
            System.out.println(RED_FG+"Can't reach server...Check Ip/port and connectivity"+RESET_COLOR);
    	}
    } 

}