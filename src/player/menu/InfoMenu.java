package player.menu;

import java.util.ResourceBundle;

import game_data.Game;
import javafx.scene.web.*;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

/**
 * 
 * @author Jesse
 *
 */
public class InfoMenu extends AbstractMenu {

	public InfoMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, mediaManager, "GameInfoTitle", polyglot, IOResources);
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

	@Override
	public void addElements() {
		this.setBottom(this.makeBackButton());	
	}
}
