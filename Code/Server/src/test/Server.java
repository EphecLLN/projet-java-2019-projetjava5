/**
 * @author Morgan Valentin
 * @date 30/10/2019
 * @source code used to understand => https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
*/

package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Player;

/**
 * This class 
 */
public class Server  { 	
    
    private final Scanner userInput = new Scanner(System.in);
    private int port;
    private ServerSocket servSock;
    private Socket sock;
    protected DataInputStream in;
    protected DataOutputStream out;
    public static boolean allPlayerConnected = false;
    public static HashMap<String,Player> Players = new HashMap<>();
    
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
     */
    public Server(){
        Players.put("P1",null);
        Players.put("P2",null);
    }
     
    private void getIpv4Adress() throws SocketException {
    	 Enumeration e = NetworkInterface.getNetworkInterfaces();
    	 while (e.hasMoreElements()) {
    		 NetworkInterface n = (NetworkInterface) e.nextElement();
    		 Enumeration ee = n.getInetAddresses();
    		 while (ee.hasMoreElements()) {
    			 InetAddress i = (InetAddress) ee.nextElement();
    			 if (i instanceof Inet4Address) {
    				 System.out.println("IP: "+YELLOW_FG+i.getHostAddress()+RESET_COLOR);
    				 }
    		 }
    	 }
    }
    /**
     * Method that clears the cmdline Screen and sets the cursor to home.
     */
    private void clearScreen(){
        System.out.print(CLEAR_SCREEN);
        System.out.print(HOME_CURSOR);  	
    }
    
    /**
     * Method that randomly gives the first turn to one of the players
     */
    protected void giveFirstTurn() {
        int chance = ((int) (Math.random() * 50 + 1)) % 2;
        if (chance == 0) {
            Players.get("P1").isMyTurn = true;
        } else {
            Players.get("P2").isMyTurn = true;
        }
    }


    /**
     * Method that initialisez the server and waits for clients to connect. 
     * 
     * Only 2 clients can be connected.
     * Once bothe clients have placed their units, the server randomly choses one to be the first to shoot 
     * and let them start the game.
     * 
     */
    protected void initServer(){
        
    	try {
            clearScreen();
            System.out.println("Which port-number do you want to use? (press enter for default port: 5555)");       
            //port = userInput.nextInt();
            String userStr = userInput.nextLine();
            if(userStr.equals("")){
                port = 5555;
            }
            else{
                port = Integer.valueOf(userStr);
            }
            servSock = new ServerSocket(port);                          //Creating a new serverSocket with port given by user. 

            InetAddress inetAddress = InetAddress.getLocalHost();       //Get IP-address of server => not working properly on Linux //TODO
            clearScreen();
            System.out.println(GREEN_FG+"Server started!\n"+RESET_COLOR);
            System.out.println("Players can connect whit the following information: ");
            System.out.println("\nPort: "+ PURPLE_FG + port + RESET_COLOR);
            getIpv4Adress();
            System.out.println("\nWaiting for player(s) to connect\n");


            int clientCount =0;
            while (true)  { 
                sock = null;                  //reset the socket 
                try { 
                    sock = servSock.accept(); //Wait for a client to connect to the server with the right socket

                    //creating input and output streams to enable communication between client and server 
                    in = new DataInputStream(sock.getInputStream());
                    out = new DataOutputStream(sock.getOutputStream()); 
                    
                    Thread t = new Player(sock, in, out); //creating a new thread object which indirectly associates the client to a Player object
                    t.start();                            //starting the thread, executes the the run() method in the Player object

                    clientCount ++;
                    if(clientCount == 2){
                        while(!(Players.get("P1").isReady && Players.get("P2").isReady)){} //Wait until both players have placed their units
                        giveFirstTurn();
                        allPlayerConnected = true;
                        System.out.println(GREEN_FG + "All players connected, game started\n" + RESET_COLOR);
                        break;
                    } 
                    
                } 
                catch (IOException e){ 
                	servSock.close();
                	userInput.close();
                	sock.close();
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e){ 
            //TODO
        }
    	catch(InputMismatchException a) {
        	System.out.println(RED_FG + "FATAL ERROR :" + PURPLE_FG +" IP and Port must be Integer" + RESET_COLOR);
        }	 
    } 


    /**
     * Run main to start the server
     */
    public static void main(String[] args){
    	try {
            Server server = new Server();
            server.initServer();
    	}
        catch(NumberFormatException a) {
        	System.out.println(RED_FG+"FATAL ERROR :"+PURPLE_FG+" IP and Port must be Integer"+RESET_COLOR);
        }
    }
    	
}


  
