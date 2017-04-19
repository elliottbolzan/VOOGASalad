package usecases;

import javafx.scene.control.Button;
import player.Menu;

/**
 * 
 * @author Jesse
 *
 *	This is an implementation of the use case for exiting a game
 */
public class ExitAGame {
	
	public ExitAGame(Menu mainmenu){
		//Button is created for exiting
		Button exit = new Button("Exit");
		//Upon clicking, user is taken back to the player main menu
		exit.setOnAction(e -> mainmenu.createScene());
	}

}
