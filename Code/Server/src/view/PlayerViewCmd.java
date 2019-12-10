/**
 * @author Martin Michotte
 * @date 23/11/2019
 */

package view;

import java.util.Observable;
import java.util.Observer;

import controller.PlayerController;
import model.PlayerModel;

/**
 * //TODO
 */
public class PlayerViewCmd extends PlayerView implements Observer {
	
	public PlayerViewCmd(PlayerModel model, PlayerController controller) {
		super(model, controller);
	}

    /**
     * Method that is called whenever this view is notified of a change in the model.
     * It will check for a change compared to the initial state of the model and send aproriate commands to the clients
     * 
     * Note -> To send those commands it uses a method from the model itself. 
     *         This is not a conventional (MVC) way to do it but we did'nt found an other (simpler) way to do it since
     *         we need acces to DataInput- and DataOutput- Streams!
     */
    @Override
	public void update(Observable o, Object arg) {

        //If the player is not ready, the changes to the model are unit-plcement related
        //otherwhise the changes are shots.
        if(!model.player.isReady){
            for (String key : model.player.getMyGrid().getGridCells().keySet()) {
                if(model.player.getMyGrid().getGridCells().get(key) != null){
                    model.player.sendToClient("insertUnit");
                    model.player.sendToClient(key);
                }
            }

        }
        else{
            for (String key : model.player.getEnemyGrid().getGridCells().keySet()) {
                if(model.player.getEnemyGrid().getGridCells().get(key) == 1){
                    model.player.sendToClient("Hit");
                    model.player.sendToClient(key);
                    model.player.otherPlayer().sendToClient("myHit");
                    model.player.otherPlayer().sendToClient(key);
                }
                else if(model.player.getEnemyGrid().getGridCells().get(key) == -1){
                    model.player.sendToClient("noHit");
                    model.player.sendToClient(key);
                    model.player.otherPlayer().sendToClient("myNoHit");
                    model.player.otherPlayer().sendToClient(key);
                }
                else if(model.player.getEnemyGrid().getGridCells().get(key) == 2){
                    model.player.sendToClient("Destroyed");
                    model.player.sendToClient(key);
                    model.player.otherPlayer().sendToClient("myDestroyed");
                    model.player.otherPlayer().sendToClient(key);
                }
            }
        }
		
    }
    
}