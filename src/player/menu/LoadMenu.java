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
	private Tile playTile;

	public LoadMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, mediaManager, null, polyglot, IOResources);
		saveStates = FXCollections.observableArrayList();
		mediaManager.setSaves(saveStates);
		saveTiles = FXCollections.observableArrayList();
		saveTiles.add(playTile);
		setupScene(stage);
	}

	private void loadNewGame(Stage stage) {
		new FullPlayer(stage, this.getGame(), this.getMediaManager(), this.getPolyglot(), this.getResources());
	}

	private void loadSaveState(Stage stage, String saveName) {
		GameData data = new GameData();
		try {
			Game game = data.loadGameState(this.getMediaManager().getGamePath(), saveName);
			MediaManager loader = new MediaManager(game, getMediaManager().getGamePath(), saveStates);
			new FullPlayer(stage, game, loader, this.getPolyglot(), this.getResources());
		} catch (Exception e) {
			// Game couldn't be loaded, perhaps a wrong Game selected. Might
			// want to tell user!
			//TODO
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
					ReplaceSaveMenu replacer = new ReplaceSaveMenu(getPolyglot(), getResources());
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
