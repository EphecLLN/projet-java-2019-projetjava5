package server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UnitTest2{

	/**
	 * Test qui permet de vérifier la taille des unités
	 * on teste 2 unités qui correpondent aux 2 extremes
	 * */
	@Test
	void testGetSize() {
		Unit testUnit = new Unit("testUnit",2);
		Unit testUnit2 = new Unit("testUnit2",8);
		assertEquals(2, testUnit.getSize());
		assertEquals(8, testUnit2.getSize());
	}
	
	/**
	 * Test qui permet de vérifier le nom des unités
	 * Valeur attendue pour "GetName":
	 * ==> Le nom de l'unité
	 * */
	@Test
	void testGetname() {
		Unit testUnit = new Unit("testUnit",2);
		Unit testUnit2 = new Unit("testUnit2",8);
		assertEquals("testUnit", testUnit.getName());
		assertEquals("testUnit2", testUnit2.getName());
	}
	
	/**
	 * Test qui permet de vérifier si les cases d'un unité sont à "true"
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
	 * Test qui permet de vérifier si "les cases" d'une unité ont bien été initiées a "true"
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
	 * Test qui permet de vérifier la fonction "setCoordState" a bien mis une case à "false"
	 *Valeur attendue pour "setCoordState":
	 * ==> false = case détruite
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
	 * Teste qui permet de voir si une unité est toujours en vie ou détruite
	 * Valeur attendue pour "getIsAlive" :
	 * ==> true = En vie
	 * ==> false = Détruite
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
	
}

