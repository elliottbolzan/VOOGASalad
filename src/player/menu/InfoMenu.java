package player.menu;

import java.util.ResourceBundle;

import game_data.Game;
import javafx.scene.web.*;
import javafx.stage.Stage;
import player.MediaManager;
import player.PlayerView;
import polyglot.Polyglot;

public class InfoMenu extends PlayerView {

	public InfoMenu(Stage stage, Game game, MediaManager loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(polyglot, IOResources);
		setup(game);
	}
	
	private void setup(Game game){
		WebView view = new WebView();
		WebEngine engine = view.getEngine();
		if(game.getInfo() != null){
			engine.loadContent(game.getInfo());
		}
		
		this.setCenter(view);
	}
}
