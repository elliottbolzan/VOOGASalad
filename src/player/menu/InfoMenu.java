package player.menu;

import java.util.ResourceBundle;

import javafx.stage.Stage;
import player.MediaManager;
import player.PlayerView;
import polyglot.Polyglot;

public class InfoMenu extends PlayerView {

	public InfoMenu(Stage stage, MediaManager loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(polyglot, IOResources);
	}

}
