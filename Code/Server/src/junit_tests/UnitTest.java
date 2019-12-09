/**
 * @author Martin Perdaens
 * @date 15/11/2019
 */

package junit_tests;

import model.*;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class UnitTest2{

	/**
	 * Test qui permet de v�rifier la taille des unit�s
	 * on teste 2 unit�s qui correpondent aux 2 extremes
	 * */
	@Test
	void testGetSize() {
		Unit testUnit = new Unit("testUnit",2);
		Unit testUnit2 = new Unit("testUnit2",8);
		assertEquals(2, testUnit.getSize());
		assertEquals(8, testUnit2.getSize());
	}
	
	/**
	 * Test qui permet de v�rifier le nom des unit�s
	 * Valeur attendue pour "GetName":
	 * ==> Le nom de l'unit�
	 * */
	@Test
	void testGetname() {
		Unit testUnit = new Unit("testUnit",2);
		Unit testUnit2 = new Unit("testUnit2",8);
		assertEquals("testUnit", testUnit.getName());
		assertEquals("testUnit2", testUnit2.getName());
	}
	
	/**
	 * Test qui permet de v�rifier si les cases d'un unit� sont � "true"
	 * Valeur attendue pour "GetCoordState"
	 * ==> true = case en vie
	 * */
	@Test
	void testGetCoordState() {
		Unit testUnit = new Unit("testUnit",2);	
		String[] coordsU1 = {"B2","B3"};
		testUnit.initCoordState(coordsU1);
		assertEquals(true, testUnit.getCoordState("B2"));
		assertEquals(true, testUnit.getCoordState("B3"));		
	}
	
	/**
	 * Test qui permet de v�rifier si "les cases" d'une unit� ont bien �t� initi�es a "true"
	 * Valeur attendue pour "initCoordState":
	 * ==> true = case en vie
	 * */
	@Test
	void testinitCoordState() {
		Unit testUnit = new Unit("testUnit",4);	
		String[] coordsU = {"D2","D3","E2","E3"};
		testUnit.initCoordState(coordsU);
		assertEquals(true, testUnit.getCoordState("D2"));
		assertEquals(true, testUnit.getCoordState("D3"));
		assertEquals(true, testUnit.getCoordState("E2"));
		assertEquals(true, testUnit.getCoordState("E3"));
	}
	
	/**
	 * Test qui permet de v�rifier la fonction "setCoordState" a bien mis une case � "false"
	 *Valeur attendue pour "setCoordState":
	 * ==> false = case d�truite
	 * */
	@Test
	void testsetCoordState() {
		Unit testUnit = new Unit("testUnit",4);
		String[] coordsU1 = {"C2","C3","D2","D3"};
		testUnit.initCoordState(coordsU1);
		testUnit.setCoordState("D2");
		assertEquals(true, testUnit.getCoordState("C2"));
		assertEquals(true, testUnit.getCoordState("C3"));
		assertEquals(false, testUnit.getCoordState("D2"));
		assertEquals(true, testUnit.getCoordState("D3"));
        }
	
	/**
	 * Teste qui permet de voir si une unit� est toujours en vie ou d�truite
	 * Valeur attendue pour "getIsAlive" :
	 * ==> true = En vie
	 * ==> false = D�truite
	 * */
	@Test
	void testGetIsAlive() {
		Unit testUnit = new Unit("testUnit",4);
		String[] coordsU1 = {"C2","C3","D2","D3"};
		testUnit.initCoordState(coordsU1);
		testUnit.setCoordState("C2");
		testUnit.setCoordState("C3");
		testUnit.setCoordState("D2");
		testUnit.setCoordState("D3");
		assertEquals(false, testUnit.getIsAlive());
		
		Unit testUnit2 = new Unit("testUnit2",8);
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

