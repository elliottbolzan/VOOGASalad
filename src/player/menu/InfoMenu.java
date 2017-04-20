package player.menu;

import java.util.ResourceBundle;

import game_data.Game;
import javafx.scene.web.*;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

public class InfoMenu extends AbstractMenu {
	private Game game;

	public InfoMenu(Stage stage, Game game, MediaManager loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, loader, "GameInfoTitle", polyglot, IOResources);
		this.game = game;
	}

	@Override
	public void addElements() {
		WebView view = new WebView();
		WebEngine engine = view.getEngine();
		if(game.getInfo() != null){
			engine.loadContent(game.getInfo());
		}
		
		this.setCenter(view);
	}

}
