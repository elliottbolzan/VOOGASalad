package player.launcher;

import java.util.ResourceBundle;

import authoring.components.ComponentMaker;
import game_data.Game;
import game_data.GameData;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import player.Loader;
import polyglot.Polyglot;

/**
 * Environment for playing the game. The Player instantiates the engine GameLoop
 * and loads it with data from a chosen file using the GameData interface.
 * 
 * @author Jay Doherty
 * @author Jesse
 */
public class FullPlayer extends AbstractPlayer {

	private static final int CONTROLS_HEIGHT = 42;

	private Button playButton;
	private boolean isPaused;
	private ImageView playImage;
	private ImageView pauseImage;

	private Loader mediaManager;

	public FullPlayer(Stage primaryStage, Game game, Loader mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(primaryStage, game, polyglot, IOResources);
		this.mediaManager = mediaManager;

		this.buildControlBar();
		this.playSong();

		this.togglePlayPause(true);
	}

	private void buildControlBar() {
		ComponentMaker factory = this.getFactory();

		pauseImage = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(this.getResources().getString("PausePath"))));
		playImage = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(this.getResources().getString("PlayPath"))));
		playButton = factory.makeImageButton("PauseButtonText", pauseImage, e -> this.togglePlayPause(isPaused), true);
		playButton.setPrefHeight(CONTROLS_HEIGHT);

		ImageView restartImage = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream(this.getResources().getString("RestartPath"))));
		Button restartButton = factory.makeImageButton("RestartButtonText", restartImage, e -> this.restart(), true);
		restartButton.setPrefHeight(CONTROLS_HEIGHT);

		Button saveButton = factory.makeButton("SaveButtonText", e -> this.save(), true);
		saveButton.setPrefHeight(CONTROLS_HEIGHT);

		Button exitButton = factory.makeButton("ExitButtonText", e -> this.exit(), true);
		exitButton.setPrefHeight(CONTROLS_HEIGHT);

		ToolBar toolbar = new ToolBar(playButton, restartButton, saveButton, exitButton);
		this.setTop(toolbar);
	}
	
	private void togglePlayPause(boolean play) {
		if (play) {
			isPaused = false;
			playButton.textProperty().bind(this.getPolyglot().get("PauseButtonText"));
			playButton.setGraphic(this.pauseImage);
			this.getRunningGameLoop().startTimeline();
		} else {
			isPaused = true;
			playButton.textProperty().bind(this.getPolyglot().get("PlayButtonText"));
			playButton.setGraphic(this.playImage);
			this.getRunningGameLoop().pauseTimeline();
		}
	}
	
	private void restart() {
		this.getRunningGameLoop().pauseTimeline();
		this.buildGameView();
		this.buildControlBar();
		this.togglePlayPause(true);
	}
	
	private void playSong() {
		mediaManager.playSong();
	}
	
	private void save() {
		mediaManager.saveGame();
	}
	
	protected void exit() {
		super.exit();
		mediaManager.pauseSong();
	}
}
