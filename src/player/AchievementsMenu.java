package player;

import java.util.ResourceBundle;

import javafx.stage.Stage;
import polyglot.Polyglot;

public class AchievementsMenu extends AbstractMenu {

	public AchievementsMenu(Stage stage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, loader, "AchievementsTitle", polyglot, IOResources);
	}
}
