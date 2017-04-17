package player;

import java.io.File;
import java.util.ResourceBundle;

import authoring.components.ComponentMaker;
import engine.game.gameloop.GameLoop;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

/**
 * Environment for playing the game. The Player instantiates the engine GameLoop and loads it with data
 * from a chosen file using the GameData interface.
 * 
 * @author Jay Doherty
 * @author Jesse
 */
public class Player extends AbstractPlayer {

	private static final int CONTROLS_HEIGHT = 42;
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");

	private Button playButton;
	private boolean isPaused;
	private ObservableList<String> saveStates;
	private ComponentMaker factory;
	private int count = 0;
	private ImageView playImage;
	private ImageView pauseImage;
	private Loader loader;

	public Player(String dataFolderPath, ObservableList<String> saveStates, Loader loader) {
		super(loader.loadGame(), loader.getGamePath());
		this.loader = loader;
		this.saveStates = saveStates;
		factory = new ComponentMaker(resources);
		this.buildControlBar();

		this.togglePlayPause(true);
	}


	private void buildControlBar() {
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

		ToolBar toolbar = new ToolBar(playButton, restartButton, saveButton, exitButton);

		this.setTop(toolbar);
	}

	private void togglePlayPause(boolean play) {
		if (play) {
			isPaused = false;
			playButton.setText(resources.getString("PauseButtonText"));
			playButton.setGraphic(this.pauseImage);
			this.getRunningGameLoop().startTimeline();
		} else {
			isPaused = true;
			playButton.setText(resources.getString("PlayButtonText"));
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
			loader.loadGame().setName("save"+count);
			loader.loadData().saveGame(loader.loadGame(), path);
		}
		saveStates.add(path+File.separator+loader.loadGame().getName());
	}
	
}
