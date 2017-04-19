package starter;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import authoring.AuthoringEnvironment;
import authoring.components.ComponentMaker;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import player.Loader;
import player.MainMenu;
import polyglot.Case;
import polyglot.Polyglot;
import polyglot.PolyglotException;

public class StartMenu extends BorderPane {

	private static final String KEY = "AIzaSyCOWQRgYSfbiNnOdIRPBcuY6iLTqwfmOc4";

	private Stage stage;
	private Polyglot polyglot;
	private ResourceBundle IOResources = ResourceBundle.getBundle("resources/IO");
	private ComponentMaker maker;
	private List<String> languages;

	public StartMenu(Stage primaryStage) throws PolyglotException {
		this.stage = primaryStage;
		this.polyglot = new Polyglot(KEY, "resources/Strings");
		this.maker = new ComponentMaker(polyglot, IOResources.getString("StylesheetPath"));
		this.languages = polyglot.languages();
		this.setIcon();
		this.buildStage();
	}

	private void setIcon() {
		String iconPath = IOResources.getString("IconPath");
		URL path = getClass().getResource(iconPath);
		if (isOSX()) {
			new OSXIconLoader(path);
		} else {
			this.stage.getIcons().add(new Image(iconPath));
		}
	}

	private void buildStage() {
		stage.titleProperty().bind(polyglot.get("StartMenuTitle", Case.TITLE));
		stage.setMinWidth(380);
		stage.setMinHeight(300);
		stage.setOnCloseRequest(e -> System.exit(0));
		stage.setScene(this.buildScene());
		this.buildView();
		stage.show();
	}

	private Scene buildScene() {
		Scene scene = new Scene(this, 380, 300);
		scene.getStylesheets().addAll(IOResources.getString("StylesheetPath"), IOResources.getString("ToolStylePath"));
		return scene;
	}

	private void buildView() {
		playSong();
		ImageView logo = createLogo();
		MenuBar menuBar = createMenu();
		this.setTop(menuBar);
		this.setCenter(logo);
	}

	private ImageView createLogo() {
		ImageView imageView = new ImageView(new Image(IOResources.getString("LogoPath")));
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(300);
		RotateTransition rt = new RotateTransition(Duration.millis(600), imageView);
		rt.setByAngle(360);
		rt.setCycleCount(2);
		rt.setAutoReverse(true);
		playIn(1, e -> rt.play());
		return imageView;
	}

	private MenuBar createMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menuFile = makeMenu("GameMenu");
		menuFile.getItems().addAll(makeMenuItem("NewButton", e -> newGame()),
				makeMenuItem("EditButton", e -> editGame()), makeMenuItem("PlayButton", e -> playGame()));
		Menu languageMenu = makeLanguageMenu();
		menuBar.getMenus().addAll(menuFile, languageMenu);
		menuBar.setOpacity(0);
		FadeTransition ft = new FadeTransition(Duration.millis(300), menuBar);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		playIn(3.5, e -> ft.play());
		return menuBar;
	}
	
	private void playIn(double seconds, EventHandler<ActionEvent> handler) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds), handler));
		timeline.play();
	}

	private void playSong() {
		Media media = new Media(new File("src/resources/intro.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		playIn(1, e -> mediaPlayer.play());
	}

	private void newGame() {
		new AuthoringEnvironment(polyglot, IOResources);
	}

	private String chooseGame() {
		DirectoryChooser chooser = maker.makeDirectoryChooser(
				System.getProperty("user.dir") + IOResources.getString("DefaultDirectory"), "ChooserTitle");
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
			new AuthoringEnvironment(chosen, polyglot, IOResources);
		}

	}

	private void playGame() {
		String chosen = chooseGame();
		if (isSelected(chosen)) {
			new MainMenu(new Loader(chosen), polyglot, IOResources);
		}
	}

	private MenuItem makeMenuItem(String titleProperty, EventHandler<ActionEvent> handler) {
		MenuItem item = new MenuItem();
		item.textProperty().bind(polyglot.get(titleProperty, Case.TITLE));
		item.setOnAction(handler);
		return item;
	}

	private Menu makeMenu(String titleProperty) {
		Menu menu = new Menu();
		menu.textProperty().bind(polyglot.get(titleProperty, Case.TITLE));
		return menu;
	}

	private boolean isOSX() {
		return System.getProperty("os.name").equals("Mac OS X");
	}

	private Menu makeLanguageMenu() {
		Menu languageMenu = makeMenu("LanguageMenu");
		MenuItem pickLanguage = makeMenuItem("PickLanguageItem",
				e -> new LanguagePicker(polyglot, IOResources, languages));
		languageMenu.getItems().add(pickLanguage);
		return languageMenu;
	}

}
