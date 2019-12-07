/**
 * @author Martin Michotte
 * @date 12/11/2019
 */

package junit_tests;

import model.*;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;


class myGridTest {

    private myGrid testmyGrid = new myGrid();
    private Unit testUnit_1 = new Unit("testUnit_1",6);
    private Unit testUnit_2 = new Unit("testUnit_2",8);

	/**
	 * Test method for the getGridCell() method from the myGrid class.
	 * 
     * If no units have been placed -> the expected result should be = null
     * if some units have been places, 
     * only the gird-cells in which a unit is placed should return the unit.
     * 
	 */
    @Test
    void test_getgridCell() {
        assertEquals(null,testmyGrid.getGridCell("A1"));
        assertEquals(null,testmyGrid.getGridCell("A13"));
        assertEquals(null,testmyGrid.getGridCell("M1"));
        assertEquals(null,testmyGrid.getGridCell("M13"));
        assertEquals(null,testmyGrid.getGridCell("F10"));
        
        testmyGrid.setGridCell("A1", testUnit_1);
        testmyGrid.setGridCell("A13", testUnit_1);
        testmyGrid.setGridCell("M1", testUnit_1);
        testmyGrid.setGridCell("M13", testUnit_1);
        testmyGrid.setGridCell("D5", testUnit_2);

        assertEquals(testUnit_1,testmyGrid.getGridCell("A1"));
        assertEquals(testUnit_1,testmyGrid.getGridCell("A13"));
        assertEquals(testUnit_1,testmyGrid.getGridCell("M1"));
        assertEquals(testUnit_1,testmyGrid.getGridCell("M13"));
        assertEquals(testUnit_2,testmyGrid.getGridCell("D5")); 
        assertEquals(null,testmyGrid.getGridCell("F10"));
    }


        

	/**
	 * Test method for the setGridCell() method from the myGrid class.
	 */
    @Test
    void test_setGrdiCell() {

        testmyGrid.setGridCell("B2", testUnit_1);
        testmyGrid.setGridCell("C12", testUnit_1);
        testmyGrid.setGridCell("K4", testUnit_1);
        testmyGrid.setGridCell("J8", testUnit_1);
        testmyGrid.setGridCell("E5", testUnit_2);

        assertEquals(testUnit_1,testmyGrid.getGridCell("B2"));
        assertEquals(testUnit_1,testmyGrid.getGridCell("C12"));
        assertEquals(testUnit_1,testmyGrid.getGridCell("K4"));
        assertEquals(testUnit_1,testmyGrid.getGridCell("J8"));
        assertEquals(testUnit_2,testmyGrid.getGridCell("E5")); 
    }

}