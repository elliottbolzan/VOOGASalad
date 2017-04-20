package player.menu;

import java.util.ResourceBundle;

import game_data.Game;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Case;
import polyglot.Polyglot;

public class MainMenu extends AbstractMenu {

	public MainMenu(Stage stage, Game game, MediaManager loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, loader, "PlayerTitle", polyglot, IOResources);
		stage.setScene(createScene(420, 600));
		stage.setTitle(game.getName());
		stage.show();
	}

	public MainMenu(Game game, MediaManager loader, Polyglot polyglot, ResourceBundle IOResources) {
		this(new Stage(), game, loader, polyglot, IOResources);
	}

	@Override
	public void addElements() {
		Tile playTile = new Tile(getPolyglot().get("StartButton", Case.TITLE), "red", e -> getStage()
				.setScene(new LoadMenu(getStage(), getGame(), getLoader(),  getPolyglot(), getResources()).createScene(420, 600)));
		Tile scoresTile = new Tile(getPolyglot().get("Highscores", Case.TITLE), "orange", e -> getStage()
				.setScene(new HighscoreMenu(getStage(), getGame(), getLoader(), getPolyglot(), getResources()).createScene(420, 600)));
		Tile achievementsTile = new Tile(getPolyglot().get("AchievementsButton", Case.TITLE), "yellow", e -> getStage()
				.setScene(new AchievementsMenu(getStage(), getGame(), getLoader(), getPolyglot(), getResources()).createScene(420, 600)));
		Tile optionsTile = new Tile(getPolyglot().get("OptionsButton", Case.TITLE), "green", e -> getStage()
				.setScene(new OptionsMenu(getStage(), getGame(), getLoader(), getPolyglot(), getResources()).createScene(420, 600)));
		Tile infoTile = new Tile(getPolyglot().get("InfoButton", Case.TITLE), "blue", e -> getStage()
				.setScene(new OptionsMenu(getStage(), getGame(), getLoader(), getPolyglot(), getResources()).createScene(420, 600)));
		addTiles(false, playTile, scoresTile, achievementsTile, optionsTile, infoTile);
	}

}
