/**
 * @author Martin Michotte
 * @date 23/11/2019
 */

package view;

import java.util.Observer;

import controller.PlayerCmdController;
import model.PlayerModel;

/**
 * Abstract class that will be used in the multiple views.
 * Creates a reference to the model and controller objects.
 */
public abstract class PlayerView implements Observer{
	
	protected PlayerModel model;
	protected PlayerCmdController controller;
	
	public PlayerView(PlayerModel model, PlayerCmdController controller) {
		this.model = model;
		this.controller = controller;
		model.addObserver(this); // Connexion entre la vue et le modele
	}

}