package player;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoadMenu extends AbstractMenu{
		
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	
	public LoadMenu(Stage stage, String gameFolderPath){
		super(stage, gameFolderPath);
		
		//TODO: make a better menu for launching the game
		Button playButton = this.getFactory().makeButton("StartButton", e -> this.loadGame(stage, gameFolderPath), false);
		this.getRoot().setCenter(playButton);
	}
	
	private void loadGame(Stage stage, String gameFolderPath) {
		new Player(gameFolderPath);
	}

}
