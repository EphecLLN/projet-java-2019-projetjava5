package client;

import java.io.*; 
import java.net.*;

/**
  //TODO
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
     * 
     * @param str {String} - A String to send to the server 
     */
    protected void sendToServer(String str){
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
    protected String getFromServer(){
        try{
            return in.readUTF();
        }
        catch(IOException e){
            //TODO
        }
        return "";
    }
     
    /**
     * Method that lets this instance sleep for X miliseconds.
     * 
     * @param ms {int} - the time this thread needs to sleep in miliseconds
     */
    protected void sleep(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException e){
            System.out.println(e);
            System.out.println("Thread Error, game closed!");
        }
    }

}