package player;

import java.util.ResourceBundle;

import authoring.components.ComponentMaker;
import engine.game.gameloop.GameLoop;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * 
 * Environment for playing the game
 *
 */
public class Player extends BorderPane {
	
	private static final int CONTROLS_HEIGHT = 60;
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	private String stylesheetPath = resources.getString("StylesheetPath");
	
	private Stage stage;
	private Scene scene;
	private GameLoop gameLoop;
	private String dataFolderPath;
	private Button playButton;
	private boolean isPaused;
	private ObservableList<String> saveStates;
	
	public Player(String dataFolderPath, ObservableList<String> saveStates) {
		this.dataFolderPath = dataFolderPath;
		this.saveStates = saveStates;
		
		this.buildStage();
		
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
	
	private void buildGameView() {
		//TODO: pass in the file name of the game/level you want to play
		gameLoop = new GameLoop(scene, dataFolderPath);
		this.setCenter(gameLoop.getGameView());
	}
	
	private void buildControlBar() {
		HBox controls = new HBox();
		controls.setMaxSize(Double.MAX_VALUE, CONTROLS_HEIGHT);
		
		ComponentMaker factory = new ComponentMaker(resources);
		
		ImageView playImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(resources.getString("PlayPausePath"))));
		playButton = factory.makeImageButton("PauseButtonText", playImage, e -> this.togglePlayPause(isPaused), true);
		playButton.setPrefHeight(CONTROLS_HEIGHT);
		
		Button restartButton = factory.makeButton("RestartButtonText", e -> this.restart(), true);
		restartButton.setPrefHeight(CONTROLS_HEIGHT);
		
		Button saveButton = factory.makeButton("SaveButtonText", e -> this.save(), true);
		saveButton.setPrefHeight(CONTROLS_HEIGHT);
		
		Button exitButton = factory.makeButton("ExitButtonText", e -> this.exit(), true);
		exitButton.setPrefHeight(CONTROLS_HEIGHT);
		
		Separator separator = new Separator();
		HBox.setHgrow(separator, Priority.ALWAYS);
		
		Label scorebar = gameLoop.getGameScorebar();
		
		controls.getChildren().addAll(playButton, restartButton, saveButton, exitButton, separator, scorebar);
		
		this.setTop(controls);
	}
	
	private void togglePlayPause(boolean play) {
		if(play) {
			isPaused = false;
			playButton.setText(resources.getString("PauseButtonText"));
			gameLoop.startTimeline();
		} else {
			isPaused = true;
			playButton.setText(resources.getString("PlayButtonText"));
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
		stage.close();
	}
	
	private void save(){
		//TODO: make actually save
		saveStates.add("yay saved game");
	}
}
