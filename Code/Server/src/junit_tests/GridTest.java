/**
 * @author Morgan Valentin
 * @date 15/11/2019
 */

package junit_tests;

import model.*;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

/**
 * 
 */
class GridTest {

	

	/**
	 * Test method for the getCoordIndex() method from the Grid class.
	 * 
	 * Test Cases: 
	 * inputs	outputs		Comment
	 *   A1		 [0,0]		Top-left corner
	 *   A13	 [0,12]		Top-right corner
	 *   M1		 [12,0]		Bottom-left corner
	 *   M13	 [12,12]	Bottom-right corner
	 *   D4		 [3,3]		Random cell
	 *   K12	 [10,11]	Random cell
	 * 
	 */
	@Test
	void testgetCoordIndex(){
		Grid testGrid = new Grid();
		
		int[] testvalue = new int[]{0,0};
		Assert.assertArrayEquals(testvalue, testGrid.getCoordIndex("A1"));
		//Assert.assertArrayEquals(expectedOutput, methodOutput);
		
		testvalue = new int[]{0,12};
		Assert.assertArrayEquals(testvalue, testGrid.getCoordIndex("A13"));
		
		testvalue = new int[]{12,0};
		Assert.assertArrayEquals(testvalue, testGrid.getCoordIndex("M1"));
		
		testvalue = new int[]{12,12};
		Assert.assertArrayEquals(testvalue, testGrid.getCoordIndex("M13"));
		
		testvalue = new int[]{3,3};
		Assert.assertArrayEquals(testvalue, testGrid.getCoordIndex("D4"));
		
		testvalue = new int[]{10,11};
		Assert.assertArrayEquals(testvalue, testGrid.getCoordIndex("K12"));
	}

}
