package player;

import java.util.ResourceBundle;

import game_data.Game;
import game_data.GameData;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import polyglot.Polyglot;

/**
 * 
 * @author Jesse
 *
 */
public class LoadMenu extends AbstractMenu {

	private ObservableList<String> saveStates;
	private ObservableList<Button> saveButtons;
	private VBox saveButtonContainer;
	
	private Polyglot polyglot;
	private ResourceBundle IOResources;

	public LoadMenu(Stage stage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, loader, "LoadTitle", polyglot, IOResources);
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		saveStates = FXCollections.observableArrayList();
		saveButtons = FXCollections.observableArrayList();
		setupScene(stage);
	}

	private void loadNewGame(Stage stage) {
		//stage.close();
		new Player(saveStates, getLoader(), polyglot, IOResources);
	}
	
	private void loadSaveState(Stage stage, String saveName){
		GameData data = new GameData();
		Game game = data.loadGameState(getLoader().getGamePath(), saveName);
		Loader loader = new Loader(getLoader().getGamePath());
		loader.setData(data);
		loader.setGame(game);
		new Player(saveStates, loader, polyglot, IOResources);
	}

	private void setupScene(Stage stage) {
		saveButtonContainer = new VBox(5);

		VBox container = new VBox(20);
		container.setAlignment(Pos.TOP_CENTER);
		container.maxWidthProperty().bind(stage.widthProperty().multiply(0.3));
		saveButtonContainer.maxWidthProperty().bind(container.maxWidthProperty());

		Button newGameButton = this.getFactory().makeButton("NewGameButton", e -> this.loadNewGame(stage), true);
		newGameButton.setTranslateY(50.0);

		saveStates.addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				if (saveStates.size() < 11) {
					Button save = getFactory().makeButton(Integer.toString(saveStates.size()),
							e -> loadSaveState(stage, saveStates.get(saveStates.size() - 1)), true);
					saveButtons.add(save);
					saveButtonContainer.getChildren().add(save);
				} else {
					ReplaceSaveMenu replacer = new ReplaceSaveMenu();
					replacer.display(e -> {
						int index = replacer.getButtonID();
						// Changes button action to load new save
						saveButtons.get(index).setOnAction(e1 -> {
							loadSaveState(stage, saveStates.get(saveStates.size() - 1));
						});
						replacer.close();
					});
				}

			}
		});

		container.getChildren().addAll(newGameButton, saveButtonContainer);
		this.setCenter(container);

	}

}
