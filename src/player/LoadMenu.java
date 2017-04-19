package player;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.menu.AbstractMenu;
import player.menu.Tile;
import polyglot.Case;
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

	private void loadGame(Stage stage, String gameFolderPath) {
		new Player(gameFolderPath, saveStates, getLoader(), polyglot, IOResources);
	}

	private void setupScene(Stage stage) {
		saveButtonContainer = new VBox(5);

		VBox container = new VBox(20);
		container.setAlignment(Pos.TOP_CENTER);
		container.maxWidthProperty().bind(stage.widthProperty().multiply(0.3));
		saveButtonContainer.maxWidthProperty().bind(container.maxWidthProperty());

		saveStates.addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				if (saveStates.size() < 11) {
					Button save = getFactory().makeButton(Integer.toString(saveStates.size()),
							e -> loadGame(stage, saveStates.get(saveStates.size() - 1)), true);
					saveButtons.add(save);
					saveButtonContainer.getChildren().add(save);
				} else {
					ReplaceSaveMenu replacer = new ReplaceSaveMenu();
					replacer.display(e -> {
						int index = replacer.getButtonID();
						// Changes button action to load new save
						saveButtons.get(index).setOnAction(e1 -> {
							loadGame(stage, saveStates.get(saveStates.size() - 1));
						});
						replacer.close();
					});
				}

			}
		});

		container.getChildren().addAll(saveButtonContainer);
		//this.setCenter(container);
	}

	@Override
	public void addElements() {
		Tile playTile = new Tile(getPolyglot().get("NewGameButton", Case.TITLE), "red",
				e -> loadGame(getStage(), getLoader().getGamePath()));
		addTiles(true, playTile);
	}

}
