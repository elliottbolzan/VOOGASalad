package player;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Jesse
 *
 */
public class LoadMenu extends AbstractMenu {

	private ObservableList<String> saveStates;
	private ObservableList<Button> saveButtons;
	private VBox saveButtonContainer;

	public LoadMenu(Stage stage, Loader loader) {
		super(stage, loader, "LoadTitle");
		saveStates = FXCollections.observableArrayList();
		saveButtons = FXCollections.observableArrayList();
		setupScene(stage);
	}

	private void loadGame(Stage stage, String gameFolderPath) {
		//stage.close();
		new Player(gameFolderPath, saveStates, getLoader());
	}

	private void setupScene(Stage stage) {
		saveButtonContainer = new VBox(5);

		VBox container = new VBox(20);
		container.setAlignment(Pos.TOP_CENTER);
		container.maxWidthProperty().bind(stage.widthProperty().multiply(0.3));
		saveButtonContainer.maxWidthProperty().bind(container.maxWidthProperty());

		Button newGameButton = this.getFactory().makeButton("NewGameButton", e -> this.loadGame(stage, getLoader().getGamePath()),
				true);
		newGameButton.setTranslateY(50.0);

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

		container.getChildren().addAll(newGameButton, saveButtonContainer);
		this.setCenter(container);

	}

}
