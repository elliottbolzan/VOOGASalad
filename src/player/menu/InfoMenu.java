package player.menu;

import java.util.ResourceBundle;

import game_data.Game;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

public class InfoMenu extends AbstractMenu {

	public InfoMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, mediaManager, "InfoTitle", polyglot, IOResources);
	}

	@Override
	public void addElements() {
		// TODO Auto-generated method stub
		
	}

}
