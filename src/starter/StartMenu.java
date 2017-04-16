package starter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import authoring.AuthoringEnvironment;
import authoring.components.ComponentMaker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import player.MainMenu;

public class StartMenu extends BorderPane {

	private Stage stage;
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Starter");
	private String stylesheetPath = resources.getString("StylesheetPath");
	private String toolbarPath = resources.getString("ToolStylePath");
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
		this.buildView();
		stage.show();
	}

	private Scene buildScene() {
		Scene scene = new Scene(this, 380, 300);
		scene.getStylesheets().addAll(stylesheetPath, toolbarPath);
		return scene;
	}

	private void buildView() {
		ImageView imageView = new ImageView(new Image(logoPath));
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(300);

		HBox buttonBar = new HBox();
		buttonBar.getStyleClass().setAll("segmented-button-bar");
		Region spacer = new Region();
		spacer.getStyleClass().setAll("spacer");

		MenuBar menuBar = new MenuBar();
		
		Menu menuFile = new Menu("File");
		menuFile.getItems().addAll(makeMenuItem("NewButton", e -> newGame()), makeMenuItem("EditButton", e -> editGame()), makeMenuItem("PlayButton", e-> playGame()));

		Menu menuEdit = new Menu("Help");
		Menu menuView = new Menu("About");
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

		this.setTop(menuBar);
		this.setCenter(imageView);
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
		String chosen = chooseGame();
		if (isSelected(chosen)) {
			new MainMenu(chosen);
		}
	}
	
	private MenuItem makeMenuItem(String titleProperty, EventHandler<ActionEvent> handler) {
		MenuItem item = new MenuItem(resources.getString(titleProperty));
		item.setOnAction(handler);
		return item;
	}

	private boolean isOSX() {
		return System.getProperty("os.name").equals("Mac OS X");
	}

}
