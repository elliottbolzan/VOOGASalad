package player.menu;

import java.util.ResourceBundle;

import javafx.stage.Stage;
import player.MediaManager;
import player.PlayerView;
import polyglot.Polyglot;

public class AchievementsMenu extends PlayerView {

	public AchievementsMenu(Stage stage, MediaManager loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(polyglot, IOResources);
	}
}
