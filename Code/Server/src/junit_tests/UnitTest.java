/**
 * @author Martin Perdaens
 * @date 15/11/2019
 */

package junit_tests;

import model.*;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class UnitTest{

	/**
	 * Test qui permet de v�rifier la taille des unit�s
	 * on teste 2 unit�s qui correpondent aux 2 extremes
	 * */
	@Test
	void testGetSize() {
		Unit testUnit = new Unit("testUnit",2, 4);
		Unit testUnit2 = new Unit("testUnit",8, 4);
		assertEquals(2, testUnit.getSize());
		assertEquals(8, testUnit2.getSize());
	}
	
	/**
	* Test that verifies the name of the units
	* Expected value for "GetName":
	* ==> The name of the unit
	 * */
	@Test
	void testGetname() {
		Unit testUnit = new Unit("testUnit",2, 4);
		Unit testUnit2 = new Unit("testUnit2",8, 4);
		assertEquals("testUnit", testUnit.getName());
		assertEquals("testUnit2", testUnit2.getName());
	}
	
	/**
	* Test which allows to check if the boxes of a unit are "true"
	* Expected value for "GetCoordState"
	* ==> true = case alive
	 * */
	@Test
	void testGetCoordState() {
		Unit testUnit = new Unit("testUnit",4, 4);	
		String[] coordsU1 = {"B2","B3"};
		testUnit.initCoordState(coordsU1);
		assertEquals(true, testUnit.getCoordState("B2"));
		assertEquals(true, testUnit.getCoordState("B3"));		
	}
	
	/**
	* Test to check if "boxes" of a unit have been initiated to "true"
	* Expected value for "initCoordState":
	* ==> true = case alive
	 * */
	@Test
	void testinitCoordState() {
		Unit testUnit = new Unit("testUnit",4, 4);	
		String[] coordsU = {"D2","D3","E2","E3"};
		testUnit.initCoordState(coordsU);
		assertEquals(true, testUnit.getCoordState("D2"));
		assertEquals(true, testUnit.getCoordState("D3"));
		assertEquals(true, testUnit.getCoordState("E2"));
		assertEquals(true, testUnit.getCoordState("E3"));
	}
	
	/**
	* Test which allows to check the "setCoordState" function has put a box at "false"
	* Expected value for "setCoordState":
	* ==> false = box destroyed
	 * */
	@Test
	void testsetCoordState() {
		Unit testUnit = new Unit("testUnit",4, 4);
		String[] coordsU1 = {"C2","C3","D2","D3"};
		testUnit.initCoordState(coordsU1);
		testUnit.setCoordState("D2");
		assertEquals(true, testUnit.getCoordState("C2"));
		assertEquals(true, testUnit.getCoordState("C3"));
		assertEquals(false, testUnit.getCoordState("D2"));
		assertEquals(true, testUnit.getCoordState("D3"));
        }
	
	/**
	* Test to check if a unit is still alive or destroyed
	* Expected value for "getIsAlive":
	* ==> true = Alive
	* ==> false = Destroyed
	 * */
	@Test
	void testGetIsAlive() {
		Unit testUnit = new Unit("testUnit",4, 4);
		String[] coordsU1 = {"C2","C3","D2","D3"};
		testUnit.initCoordState(coordsU1);
		testUnit.setCoordState("C2");
		testUnit.setCoordState("C3");
		testUnit.setCoordState("D2");
		testUnit.setCoordState("D3");
		assertEquals(false, testUnit.getIsAlive());
		
		Unit testUnit2 = new Unit("testUnit2",8,4);
		String[] coordsU2 = {"A2","A3","A4","A5","B2","B3","B4","B5"};
		testUnit.initCoordState(coordsU2);
		testUnit.setCoordState("A2");
		testUnit.setCoordState("A3");
		testUnit.setCoordState("A4");
		testUnit.setCoordState("A5");
		testUnit.setCoordState("B2");
		testUnit.setCoordState("B3");
		testUnit.setCoordState("B4");
		testUnit.setCoordState("B5");
		assertEquals(true, testUnit2.getIsAlive());
	}

    /**
	 * Test to check if "stateBonus" change of true to false
	 *  */                              
	@Test
	void setSwitchStateBonus() {
		Unit testUnit2 = new Unit("testUnit",4, 4);
		testUnit2.setSwitchStateBonus();
		 assertEquals(false,testUnit2.getSwitchStateBonus() );
                                        
	}
	/**
	 * Test to check if "stateBonus" change of false to true
	 * after "counterBonus" lap
	 *  */ 
	@Test
	void getStateBonus() {
		Unit testUnit3 = new Unit("testUnit",4, 4);
		Unit testUnit4 = new Unit("testUnit",4, 4);

		assertEquals(true,testUnit3.getStateBonus() );

		testUnit4.setSwitchStateBonus();
		assertEquals(false,testUnit4.getSwitchStateBonus());
		
		testUnit4.getStateBonus();
		assertEquals(false,testUnit4.getSwitchStateBonus());
		
		testUnit4.getStateBonus();
		assertEquals(false,testUnit4.getSwitchStateBonus());
		
		testUnit4.getStateBonus();
		assertEquals(false,testUnit4.getSwitchStateBonus());
		
		testUnit4.getStateBonus();
		assertEquals(false,testUnit4.getSwitchStateBonus());
		
		testUnit4.getStateBonus();
		assertEquals(true,testUnit4.getSwitchStateBonus());

	}

	
}

