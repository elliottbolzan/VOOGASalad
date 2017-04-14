package player;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class HighscoreMenu extends AbstractMenu{
	
	private ObservableList<String> scores;
	

	public HighscoreMenu(Stage stage, String gameFolderPath) {
		super(stage, gameFolderPath, "Highscores");
	}
	
	

}
