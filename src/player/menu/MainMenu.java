package player.menu;

import java.util.ResourceBundle;

import javafx.stage.Stage;
import player.AchievementsMenu;
import player.HighscoreMenu;
import player.Loader;
import player.OptionsMenu;
import polyglot.Case;
import polyglot.Polyglot;

public class MainMenu extends AbstractMenu {

	public MainMenu(Stage stage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, loader, "PlayerTitle", polyglot, IOResources);
		stage.setScene(createScene());
		stage.setTitle(getLoader().loadGame().getName());
		stage.show();
	}

	public MainMenu(Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		this(new Stage(), loader, polyglot, IOResources);
	}

	@Override
	public void addElements() {
		Tile playTile = new Tile(getPolyglot().get("StartButton", Case.TITLE), "red", e -> getStage()
				.setScene(new LoadMenu(getStage(), getLoader(), getPolyglot(), getResources()).createScene()));
		Tile scoresTile = new Tile(getPolyglot().get("Highscores", Case.TITLE), "orange", e -> getStage()
				.setScene(new HighscoreMenu(getStage(), getLoader(), getPolyglot(), getResources()).createScene()));
		Tile achievementsTile = new Tile(getPolyglot().get("AchievementsButton", Case.TITLE), "yellow", e -> getStage()
				.setScene(new AchievementsMenu(getStage(), getLoader(), getPolyglot(), getResources()).createScene()));
		Tile optionsTile = new Tile(getPolyglot().get("OptionsButton", Case.TITLE), "green", e -> getStage()
				.setScene(new OptionsMenu(getStage(), getLoader(), getPolyglot(), getResources()).createScene()));
		Tile infoTile = new Tile(getPolyglot().get("InfoButton", Case.TITLE), "blue", e -> getStage()
				.setScene(new OptionsMenu(getStage(), getLoader(), getPolyglot(), getResources()).createScene()));
		addTiles(false, playTile, scoresTile, achievementsTile, optionsTile, infoTile);
	}

}
