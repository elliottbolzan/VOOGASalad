package player;

import java.io.File;
import java.util.ResourceBundle;

import authoring.components.ComponentMaker;
import engine.game.gameloop.GameLoop;
import game_data.Game;
import game_data.GameData;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Environment for playing the game. The Player instantiates the engine GameLoop and loads it with data
 * from a chosen file using the GameData interface.
 * 
 * @author Jay Doherty
 * @author Jesse
 */
public class Player extends BorderPane {

	private static final int CONTROLS_HEIGHT = 42;
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	private String stylesheetPath = resources.getString("StylesheetPath");
	private MediaPlayer songPlayer;

	private Stage stage;
	private Scene scene;
	private GameLoop gameLoop;
	private String dataFolderPath;
	private Button playButton;
	private boolean isPaused;
	private ObservableList<String> saveStates;
	private GameData gameData;
	private Game game;
	private ComponentMaker factory;
	private int count = 0;
	private ImageView playImage;
	private ImageView pauseImage;

	public Player(String dataFolderPath, ObservableList<String> saveStates) {
		this.dataFolderPath = dataFolderPath;
		this.saveStates = saveStates;
		factory = new ComponentMaker(resources);
		this.buildStage();

		this.playSong();
		this.buildGameView();
		this.buildControlBar();

		this.togglePlayPause(true);
	}

	private void buildStage() {
		stage = new Stage();
		stage.setTitle(resources.getString("PlayerTitle"));
		stage.setMinWidth(600);
		stage.setMinHeight(600);
		stage.setOnCloseRequest(e -> this.exit());

		scene = new Scene(this, 600, 600);
		scene.getStylesheets().add(stylesheetPath);

		stage.setScene(scene);
		stage.show();
	}

	private void playSong() {
		try {
			gameData = new GameData();
			game = gameData.loadGame(dataFolderPath);
			String path = game.getSongPath();
			String uriString = new File(path).toURI().toString();
			songPlayer = new MediaPlayer(new Media(uriString));
			songPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			songPlayer.play();
		} catch (Exception e) {

		}
	}

	private void buildGameView() {
		gameLoop = new GameLoop(scene, new GameData().loadGame(dataFolderPath));
		Overlay scorebar = gameLoop.getGameScorebar();
		StackPane pane = new StackPane();
		pane.getChildren().addAll(gameLoop.getGameView(), scorebar.display());
		this.setCenter(pane);
	}

	private void buildControlBar() {
		HBox controls = new HBox();
		controls.setMaxSize(Double.MAX_VALUE, CONTROLS_HEIGHT);
		

		pauseImage = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(resources.getString("PausePath"))));
		playImage = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(resources.getString("PlayPath"))));
		playButton = factory.makeImageButton("PauseButtonText", pauseImage, e -> this.togglePlayPause(isPaused), true);
		playButton.setPrefHeight(CONTROLS_HEIGHT);

		ImageView restartImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(resources.getString("RestartPath"))));
		Button restartButton = factory.makeImageButton("RestartButtonText", restartImage, e -> this.restart(), true);
		restartButton.setPrefHeight(CONTROLS_HEIGHT);

		Button saveButton = factory.makeButton("SaveButtonText", e -> this.save(), true);
		saveButton.setPrefHeight(CONTROLS_HEIGHT);

		Button exitButton = factory.makeButton("ExitButtonText", e -> this.exit(), true);
		exitButton.setPrefHeight(CONTROLS_HEIGHT);

		Separator separator = new Separator();
		HBox.setHgrow(separator, Priority.ALWAYS);

		ToolBar toolbar = new ToolBar(playButton, restartButton, saveButton, exitButton);

		controls.getChildren().addAll(playButton, restartButton, saveButton, exitButton, separator);

		this.setTop(toolbar);
	}

	private void togglePlayPause(boolean play) {
		if (play) {
			isPaused = false;
			playButton.setText(resources.getString("PauseButtonText"));
			playButton.setGraphic(this.pauseImage);
			gameLoop.startTimeline();
		} else {
			isPaused = true;
			playButton.setText(resources.getString("PlayButtonText"));
			playButton.setGraphic(this.playImage);
			gameLoop.pauseTimeline();
		}
	}

	private void restart() {
		gameLoop.pauseTimeline();
		this.buildGameView();
		this.buildControlBar();
		this.togglePlayPause(true);
	}

	private void exit() {
		gameLoop.pauseTimeline();
		if (songPlayer != null)
			songPlayer.pause();
		stage.close();
	}

	private void save() {
		count++;
		String path = "";
		String outputFolder = new File(resources.getString("GamesPath")).getAbsolutePath();
		DirectoryChooser chooser = factory.makeDirectoryChooser(outputFolder, "GameSaverTitle");
		File selectedDirectory = chooser.showDialog(getScene().getWindow());
		if (selectedDirectory != null) {
			path = selectedDirectory.getAbsolutePath();
		}
		if (!path.equals("")) {
			game.setName("save"+count);
			gameData.saveGame(game, path);
		}
		saveStates.add(path+File.separator+game.getName());
	}
	
}
