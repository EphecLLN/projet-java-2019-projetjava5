package client;

import java.io.*; 
import java.net.*;

/**
 * This class represents a client and its basic methods such:
 *  -> getting information from the server 
 *  -> sending information to the server
 *  -> sleeping for a certain amount of time
 * 
 * This class is a super-class of which a cmd-line and Gui client will inherit.
 * 
 */
public class Client {

    protected String name;
    protected int port;
    protected String ip;
    protected Socket sock;
    protected DataInputStream in;
    protected DataOutputStream out;

    /**
     * Method that takes a string and tries to send it to the server.
     * If an error occurs, it will be displayed on the cmd-line with no further action.
     * 
     * @param str {String} - A String to send to the server 
     */
    protected void sendToServer(String str){
        try{
            out.writeUTF(str);
        }
        catch(IOException e){
            System.out.println(e);
            System.out.println("ERROR - unable to send information to the server");
        }
    }

    /**
     * Method that waits for string from the server and returns it when received. 
     * If an error occurs, it will be displayed on the cmd-line with no further action.
     * 
     * @return a string received from the server
     */
    protected String getFromServer(){
        try{
            return in.readUTF();
        }
        catch(IOException e){
            System.out.println(e);
            System.out.println("ERROR - unable to receive information from the server");
        }
        return "";
    }
     
    /**
     * Method that lets this instance sleep for X milliseconds.
     * If an error occurs, it will be displayed on the cmd-line with no further action.
     * 
     * @param ms {int} - the time this thread needs to sleep in milliseconds
     */
    protected void sleep(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException e){
            System.out.println(e);
            System.out.println( "ERROR - Thread could not sleep");
        }
    }

}