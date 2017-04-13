package player;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadMenu extends AbstractMenu {

	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	private ObservableList<String> saveStates;
	private ObservableList<Button> saveButtons;
	private VBox saveButtonContainer;

	public LoadMenu(Stage stage, String gameFolderPath) {
		super(stage, gameFolderPath, "LoadTitle");
		saveStates = FXCollections.observableArrayList();
		saveButtons = FXCollections.observableArrayList();
		setupScene(stage, gameFolderPath);
	}

	private void loadGame(Stage stage, String gameFolderPath) {
		new Player(gameFolderPath, saveStates);
	}

	private void setupScene(Stage stage, String gameFolderPath) {
		saveButtonContainer = new VBox(5);

		VBox container = new VBox(20);
		container.setAlignment(Pos.TOP_CENTER);
		container.maxWidthProperty().bind(stage.widthProperty().multiply(0.3));
		saveButtonContainer.maxWidthProperty().bind(container.maxWidthProperty());

		Button newGameButton = this.getFactory().makeButton("NewGameButton", e -> this.loadGame(stage, gameFolderPath),
				true);

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
		this.getRoot().setCenter(container);

	}

}
