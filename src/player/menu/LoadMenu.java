package player.menu;

import java.util.ResourceBundle;

import game_data.Game;
import game_data.GameData;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import player.MediaManager;
import player.launcher.FullPlayer;
import polyglot.Case;
import polyglot.Polyglot;

/**
 * 
 * @author Jesse
 *
 */
public class LoadMenu extends AbstractMenu {

	private ObservableList<String> saveStates;
	private ObservableList<Tile> saveTiles;
	private Game game;
	private Tile playTile;

	private Polyglot polyglot;
	private ResourceBundle IOResources;

	public LoadMenu(Stage stage, Game game, MediaManager loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, loader, "LoadTitle", polyglot, IOResources);
		this.game = game;
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		saveStates = FXCollections.observableArrayList();
		loader.setSaves(saveStates);
		saveTiles = FXCollections.observableArrayList();
		saveTiles.add(playTile);
		setupScene(stage);
	}

	private void loadNewGame(Stage stage) {
		Stage stage2 = new Stage();
		new FullPlayer(stage2, game, getLoader(), polyglot, IOResources);
		stage2.show();
	}

	private void loadSaveState(Stage stage, String saveName) {
		GameData data = new GameData();
		try {
			Game game = data.loadGameState(getLoader().getGamePath(), saveName);
			MediaManager loader = new MediaManager(game, getLoader().getGamePath(), saveStates);
			new FullPlayer(stage, game, loader, polyglot, IOResources);
		} catch (Exception e) {
			// Game couldn't be loaded, perhaps a wrong Game selected. Might
			// want to tell user!
		}
	}

	private void setupScene(Stage stage) {

		saveStates.addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				if (saveStates.size() < 11) {
					Tile save = new Tile(getPolyglot().get(Integer.toString(saveStates.size()), Case.TITLE), 
							"blue", e -> loadSaveState(stage, saveStates.get(saveStates.size()-1)));
					saveTiles.add(save);
					addSaveTiles(true, saveTiles);
				} else {
					ReplaceSaveMenu replacer = new ReplaceSaveMenu(polyglot, IOResources);
					replacer.display(e -> {
						int index = replacer.getButtonID();
						// Changes button action to load new save
						saveTiles.get(index).setOnAction(e1 -> {
							loadSaveState(stage, saveStates.get(saveStates.size()-1));
						});
						replacer.close();
					});
				}

			}
		});
	}

	@Override
	public void addElements() {
		playTile = new Tile(getPolyglot().get("NewGameButton", Case.TITLE), "red", e -> loadNewGame(getStage()));
		addTiles(true, playTile);
	}
}
