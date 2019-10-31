package unit�;

import java.util.Scanner;

public class Unit� {
	//D�claration des diff�rentes variable
	static int nombresUnite = 6;
	String nom;
	int longueurCase;
	int LargeurCase;
	int aireTotal;
	Boolean detruit;
	Boolean bonus;
	String nomBonus;
	int coordonneesEnXY1;
	int coordonneesEnXY2;
	
	/**
	 * Constructeur pour les unit� avec un bonus
	 * */
	
	public Unit�( String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus, String nomBonus) {
		this.nom = nom;
		this.longueurCase = longueurCase;
		this.LargeurCase = LargeurCase;
		this.aireTotal = aireTotal;
		this.detruit = detruit;
		this.bonus = bonus;
		this.nomBonus = nomBonus;
	}
	
	/**
	 * Constructeur pour les unit� sans le bonus
	 * */
	
	public Unit�( String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus) {
		this.nom = nom;
		this.longueurCase = longueurCase;
		this.LargeurCase = LargeurCase;
		this.aireTotal = aireTotal;
		this.detruit = detruit;
		this.bonus = bonus;
	}
	
	public static void Bonus(Boolean Bonus,String element) {
		if(Bonus == true) {
			System.out.println(element +" a bien un bonus");
		}
		else {
			System.out.println(element +" n'a pas de bonus");
		}
	}
	public static void Detruit(int aire,String element, Boolean detruit) {
		if(aire == 0) {
			System.out.println(element +" a �t� d�truit");
			detruit = false;
			nombresUnite--;
		}
		else {
			System.out.println(element +" est toujours en vie");
			detruit = true;
		}
	}
		
	public static void main( String [] args) {
		
	   String TableauUnite[][] = new String[6][3];
	   int i = 0;
	   while(i < 6) {
		   
		    //Notre objet Scanner
		    Scanner NomUnite = new Scanner(System.in);
		    Scanner Coordonee1 = new Scanner(System.in);
		    Scanner Coordonee2 = new Scanner(System.in);
		    String nomUnite;
		    String coordonee1;
		    String coordonee2;
		    
			System.out.println("El�ments pour placer les diff�rentes unit�es");
			System.out.println("---------------------------");
			System.out.println("Entrer le nom de l'unit�");
			System.out.println("Entrer la coordonn� du d�but");
			System.out.println("Entrer la coordonn� de fin");

		    nomUnite = NomUnite.nextLine();
		    coordonee1 = Coordonee1.nextLine();
		    coordonee2 = Coordonee2.nextLine();
		    
		    TableauUnite[i][0] = nomUnite;
		    TableauUnite[i][1] = coordonee1;	
		    TableauUnite[i][2] = coordonee2;	   
		    
		    i++;

	   }	
	   System.out.println(TableauUnite[0][0]); 
	    

		// String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus, String nomBonus
		//String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus
		Unit� batiment1 = new Unit�("Airport", 2, 4, 8, false, true, "Air-Strike");
		Unit� batiment2 = new Unit�("Radar Tower", 2, 3, 6, false, true, "Radar Discovery");
		Unit� batiment3 = new Unit�("Headquarter", 2, 2, 4, false, false);
		Unit� vehicule1 = new Unit�("Railway Gun", 1, 6, 6, false, true, "Big-shot");
		Unit� vehicule2 = new Unit�("MMRL", 2, 2, 4, false, true, "Rocket-strike");
		Unit� vehicule3 = new Unit�("Tank", 1, 2, 2, false, false);
				
		//while( nombresUnite > 0) {
						
			System.out.println("Batiments :\n");
			Bonus(batiment1.bonus, batiment1.nom);
			Bonus(batiment2.bonus, batiment2.nom);
			Bonus(batiment3.bonus, batiment3.nom);
			System.out.println("\n");
			Detruit(batiment1.aireTotal, batiment1.nom, batiment1.detruit);
			Detruit(batiment2.aireTotal, batiment2.nom, batiment2.detruit);
			Detruit(batiment3.aireTotal, batiment3.nom, batiment3.detruit);
			
			System.out.println("--------------------------------------\nV�hicule : \n");
			
			Bonus(vehicule1.bonus, vehicule1.nom);
			Bonus(vehicule2.bonus, vehicule2.nom);
			Bonus(vehicule3.bonus, vehicule3.nom);
			System.out.println("\n");
			Detruit(vehicule1.aireTotal, vehicule1.nom, vehicule1.detruit);
			Detruit(vehicule2.aireTotal, vehicule2.nom, vehicule2.detruit);
			Detruit(vehicule3.aireTotal, vehicule3.nom, vehicule3.detruit);
			
			//nombresUnite--;
			
		//}
			//System.out.println("\n La partie est fini et le gagnant est :");


	}


}
