package chat;

import java.io.*; 
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner; 
  
// Client class 
public class Client  
{ 
	//Colours on ouput => ONLY WORKS ON LINUX (maybe mac ?) NOT WINDOWS
	protected static final String red    = "\u001B[31m";
	protected static final String blue   = "\u001B[34m";
	protected static final String reset  = "\u001B[0m";
	protected static final String purple = "\u001B[35m";
	
    public static void main(String[] args) throws IOException  
    { 
        try
        { 
            Scanner scn = new Scanner(System.in); 
              
            // Retrieving port and IP address from user input:
            System.out.println(purple+"Port number ?"+reset);
    		int port = scn.nextInt();
    		System.out.println("Client> "+purple+port);
            System.out.println(red+"IP address ?"+reset);
    		String ip = scn.nextLine();
    		if (ip.length()==0) {
    			ip = scn.nextLine();
    		}
    		System.out.println("Client> "+red+ip+reset);
    		
      
            // establish the connection with the server with IP and Port from user input
            System.out.println(blue+"Waiting to connect to server..."+reset);
    		Socket sock = new Socket(ip, port);
    		System.out.println(red+"Connected !"+reset);
    		System.out.println("__________________________");
      
            // In and out streams => Information received (inputStream) and sent (outputStream) :
            DataInputStream in = new DataInputStream(sock.getInputStream()); 
            DataOutputStream out = new DataOutputStream(sock.getOutputStream()); 
      
            
            //Infinite loop => "Until user input (you) types "end", read whatever
            while (true)  
            { 

                System.out.println(in.readUTF());//Reads the server's output (stream) 
                String sendToServer = scn.nextLine();//Holds user input in a variale "tosend"
                out.writeUTF(sendToServer);//Sends to server characters contained in String "tosend"
                System.out.println(blue+"Client> "+red+sendToServer+reset);
                 
                // If the client (you) sends "end" => Close the connection
                if(sendToServer.equals("end")) 
                { 
                    System.out.println("Closing this connection : " + sock); 
                    sock.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                // Displays the answer from the server (Date-Time-Number of connected clients,...etc)
                String receivedFromServer = in.readUTF(); 
                System.out.println(receivedFromServer); 
            } 
              
            // closing resources 
            scn.close(); 
            in.close(); 
            out.close();
            System.exit(0);
            
        }
        catch(IOException e){ 
            //e.printStackTrace(); => To show errors (I don't want users to see thoses...)
            System.out.println(red+"Can't reach server...Check Ip/port and connectivity"+reset);
        }
        catch(InputMismatchException a) {
        	System.out.println(red+"FATAL ERROR :"+reset+purple+" IP and Port must be Integer"+reset);
        }
    } 
} 
