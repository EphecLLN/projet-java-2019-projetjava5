/**
 * @author Martin Michotte
 * @date 12/11/2019
 */

package junit_tests;

import model.*;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * 
 */
class enemyGridTest {

    private enemyGrid testenemyGrid = new enemyGrid();
        
    /**
	 * Test method for the getgridCell() method from the enemyGrid class.
     * 
	 */
    @Test
    void test_getgridCell() {
        assertEquals(0,testenemyGrid.getGridCell("A1"));
        assertEquals(0,testenemyGrid.getGridCell("A13"));
        assertEquals(0,testenemyGrid.getGridCell("M1"));
        assertEquals(0,testenemyGrid.getGridCell("M13"));
        assertEquals(0,testenemyGrid.getGridCell("F6"));
        assertEquals(0,testenemyGrid.getGridCell("D8"));

        testenemyGrid.setGridCell("A1", 0);
        testenemyGrid.setGridCell("A13", 1);
        testenemyGrid.setGridCell("M1", -1);
        testenemyGrid.setGridCell("M13", -1);
        testenemyGrid.setGridCell("F6", 1);

        assertEquals(0,testenemyGrid.getGridCell("A1"));
        assertEquals(1,testenemyGrid.getGridCell("A13"));
        assertEquals(-1,testenemyGrid.getGridCell("M1"));
        assertEquals(-1,testenemyGrid.getGridCell("M13"));
        assertEquals(1,testenemyGrid.getGridCell("F6"));
        assertEquals(0,testenemyGrid.getGridCell("D8"));

    }
    
	/**
	 * Test method for the setgridCell() method from the enemyGrid class.
     * 
	 */
    @Test
    void test_setGrdiCell() {
        testenemyGrid.setGridCell("B2", 0);
        testenemyGrid.setGridCell("C12", 1);
        testenemyGrid.setGridCell("K4", -1);
        testenemyGrid.setGridCell("J8", -1);
        testenemyGrid.setGridCell("E5", 1);
        

        assertEquals(0,testenemyGrid.getGridCell("B2"));
        assertEquals(1,testenemyGrid.getGridCell("C12"));
        assertEquals(-1,testenemyGrid.getGridCell("K4"));
        assertEquals(-1,testenemyGrid.getGridCell("J8"));
        assertEquals(1,testenemyGrid.getGridCell("E5"));
    }

}