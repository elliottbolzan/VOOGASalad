package player.menu;

import java.util.ResourceBundle;

import game_data.Game;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

public class AchievementsMenu extends AbstractMenu {

	public AchievementsMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, mediaManager, "AchievmentsTitle", polyglot, IOResources);
	}

	@Override
	public void addElements() {
		// TODO Auto-generated method stub
		
	}
}
