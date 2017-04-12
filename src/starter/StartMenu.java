package starter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import authoring.AuthoringEnvironment;
import authoring.components.ComponentMaker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import player.PlayerMenu;

public class StartMenu extends BorderPane {

	private Stage stage;
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Starter");
	private String stylesheetPath = resources.getString("StylesheetPath");
	private String iconPath = resources.getString("IconPath");
	private String logoPath = resources.getString("LogoPath");

	public StartMenu(Stage primaryStage) {
		this.stage = primaryStage;
		this.setIcon();
		this.buildStage();
	}

	private void setIcon() {
		URL path = getClass().getResource(iconPath);
		if (isOSX()) {
			new OSXIconLoader(path);
		} else {
			this.stage.getIcons().add(new Image(iconPath));
		}
	}

	private void buildStage() {
		stage.setTitle(resources.getString("Title"));
		stage.setMinWidth(380);
		stage.setMinHeight(300);
		stage.setOnCloseRequest(e -> System.exit(0));
		stage.setScene(this.buildScene());
		this.setCenter(this.buildView());
		stage.show();
	}

	private Scene buildScene() {
		Scene scene = new Scene(this, 380, 300);
		scene.getStylesheets().add(stylesheetPath);
		return scene;
	}

	private StackPane buildView() {

		StackPane pane = new StackPane();

		ImageView imageView = new ImageView(new Image(logoPath));
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(300);
		StackPane.setAlignment(imageView, Pos.CENTER);

		Button newButton = makeButton("NewButton", e -> this.newGame());
		Button editButton = makeButton("EditButton", e -> this.editGame());
		Button playButton = makeButton("PlayButton", e -> this.playGame());

		HBox editOrPlayButtons = new HBox(0);
		editOrPlayButtons.getChildren().addAll(editButton, playButton);

		VBox buttonBox = new VBox(newButton, editOrPlayButtons);
		buttonBox.setMaxHeight(60);
		buttonBox.setPadding(new Insets(20));
		StackPane.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
		
		pane.getChildren().addAll(imageView, buttonBox);

		return pane;
	}

	private void newGame() {
		new AuthoringEnvironment();
	}

	private String chooseGame() {
		// Check if games are valid from here?
		ComponentMaker maker = new ComponentMaker(resources);
		DirectoryChooser chooser = maker.makeDirectoryChooser(
				System.getProperty("user.dir") + resources.getString("DefaultDirectory"), "ChooserTitle");
		File selectedDirectory = chooser.showDialog(stage);
		if (selectedDirectory == null) {
			return "";
		} else {
			return selectedDirectory.getAbsolutePath();
		}
	}

	private boolean isSelected(String selectedDirectory) {
		if (selectedDirectory == "") {
			return false;
		} else {
			return true;
		}
	}

	private void editGame() {
		String chosen = chooseGame();
		if (isSelected(chosen)) {
			new AuthoringEnvironment(chosen);
		}

	}

	private void playGame() {
		// String chosen = chooseGame();
		// if(isSelected(chosen)){
		// new PlayerMenu(chosen);
		// }
		new PlayerMenu(chooseGame());
	}

	private Button makeButton(String label, EventHandler<ActionEvent> handler) {
		Button button = new Button(resources.getString(label));
		button.setOnAction(handler);
		HBox.setHgrow(button, Priority.ALWAYS);
		button.setMaxWidth(Double.MAX_VALUE);
		return button;
	}

	private boolean isOSX() {
		return System.getProperty("os.name").equals("Mac OS X");
	}

}
