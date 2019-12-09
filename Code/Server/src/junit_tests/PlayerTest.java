/**
 * @author Martin Michotte
 * @date 15/11/2019
 */

package junit_tests;

import model.*;
import test.*;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.Socket;


/**
 * 
 */
class PlayerTest{

    /**
	 * Test method for the gtestotherPlayer() method from the Player class.
	 * 
	 * The otherPlayer method should return the other player when called on the actual player.
     * 
	 */
	@Test
	void testotherPlayer(){
        DataInputStream stdin = new DataInputStream(System.in);
        DataOutputStream stdout = new DataOutputStream(System.out);
        Socket sock = new Socket();

        Server serv = new Server();
        Player p1 = new Player(sock,stdin,stdout);
        Player p2 = new Player(sock,stdin,stdout);
        
        assertEquals(p2,p1.otherPlayer());
        assertEquals(p1,p2.otherPlayer());
       
    }


    

} 