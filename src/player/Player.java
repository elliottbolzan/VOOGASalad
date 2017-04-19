package player;

import java.util.ResourceBundle;

import authoring.components.ComponentMaker;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polyglot.Polyglot;

/**
 * Environment for playing the game. The Player instantiates the engine GameLoop
 * and loads it with data from a chosen file using the GameData interface.
 * 
 * @author Jay Doherty
 * @author Jesse
 */
public class Player extends AbstractPlayer {

	private static final int CONTROLS_HEIGHT = 42;

	private ResourceBundle IOResources;
	private Polyglot polyglot;

	private Button playButton;
	private boolean isPaused;
	private ObservableList<String> saveStates;
	private ComponentMaker factory;
	private int count = 0;
	private ImageView playImage;
	private ImageView pauseImage;
	private Loader loader;

	public Player(ObservableList<String> saveStates, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(loader, polyglot, IOResources);
		this.loader = loader;
		this.saveStates = saveStates;
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		factory = new ComponentMaker(polyglot, IOResources.getString("StylesheetPath"));
		this.buildControlBar();

		this.togglePlayPause(true);
	}

	private void buildControlBar() {
		pauseImage = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(IOResources.getString("PausePath"))));
		playImage = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(IOResources.getString("PlayPath"))));
		playButton = factory.makeImageButton("PauseButtonText", pauseImage, e -> this.togglePlayPause(isPaused), true);
		playButton.setPrefHeight(CONTROLS_HEIGHT);

		ImageView restartImage = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(IOResources.getString("RestartPath"))));
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
			playButton.textProperty().bind(polyglot.get("PauseButtonText"));
			playButton.setGraphic(this.pauseImage);
			this.getRunningGameLoop().startTimeline();
		} else {
			isPaused = true;
			playButton.textProperty().bind(polyglot.get("PlayButtonText"));
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
		String saveName = "save"+"_"+count+".xml";
		loader.loadData().saveGameState(loader.loadGame(), loader.getGamePath(), saveName);
		saveStates.add(saveName);
	}

}
