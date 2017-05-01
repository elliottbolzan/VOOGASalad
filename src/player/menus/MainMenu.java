package player.menus;

import java.util.ResourceBundle;

import data.Game;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Case;
import polyglot.Polyglot;

/**
 * Main menu that leads to all other menus
 * 
 * @author Jesse, Jay Doherty
 *
 */
public class MainMenu extends AbstractMenu {

	public MainMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, mediaManager, null, polyglot, IOResources);
		stage.setScene(createScene(420, 600));
		stage.setTitle(game.getName());
		stage.show();
	}

	public MainMenu(Game game, MediaManager loader, Polyglot polyglot, ResourceBundle IOResources) {
		this(new Stage(), game, loader, polyglot, IOResources);
	}

	@Override
	public void addElements() {
		Tile playTile = new Tile(getPolyglot().get("StartButton", Case.TITLE), "red",
				e -> getStage()
						.setScene(new LoadMenu(getStage(), getGame(), getMediaManager(), getPolyglot(), getResources())
								.createScene(420, 600)));
		Tile scoresTile = new Tile(getPolyglot().get("HighscoresButton", Case.TITLE), "orange",
				e -> getStage().setScene(
						new HighscoreMenu(getStage(), getGame(), getMediaManager(), getPolyglot(), getResources())
								.createScene(420, 600)));
		Tile achievementsTile = new Tile(getPolyglot().get("AchievementsButton", Case.TITLE), "yellow",
				e -> getStage().setScene(
						new AchievementsMenu(getStage(), getGame(), getMediaManager(), getPolyglot(), getResources())
								.createScene(420, 600)));
		Tile optionsTile = new Tile(getPolyglot().get("OptionsButton", Case.TITLE), "green",
				e -> getStage().setScene(
						new OptionsMenu(getStage(), getGame(), getMediaManager(), getPolyglot(), getResources())
								.createScene(420, 600)));
		Tile infoTile = new Tile(getPolyglot().get("InfoButton", Case.TITLE), "blue",
				e -> getStage()
						.setScene(new InfoMenu(getStage(), getGame(), getMediaManager(), getPolyglot(), getResources())
								.createScene(420, 600)));
		addTiles(false, playTile, scoresTile, achievementsTile, optionsTile, infoTile);
	}

}
