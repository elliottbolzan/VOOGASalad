package player.menu;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Interface for all the internal menus of the player
 * 
 * @author Jesse
 * 
 */
public interface Menu {
	/**
	 * Returns the user back to the main menu
	 * 
	 * @param stage
	 */
	public void back(Stage stage);

	/**
	 * Displays the scene to the screen
	 * 
	 * @return
	 */
	public Scene createScene();

}
