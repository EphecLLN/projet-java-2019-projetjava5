package view;

import java.util.Observer;

import controller.PlayerController;
import model.PlayerModel;

/**
 * Abstract class that will be used in the multiple views.
 * Creates a reference to the model and controller objects.
 */
public abstract class PlayerView implements Observer{
	
	protected PlayerModel model;
	protected PlayerController controller;
	
	/**
	 * Constructor
	 * 
	 * @param model {PlayerModel} - the model of the game
	 * @param controller {PlayerController} - the controller of the game
	 */
	public PlayerView(PlayerModel model, PlayerController controller) {
		this.model = model;
		this.controller = controller;
		model.addObserver(this); // Connexion entre la vue et le modele
	}

}