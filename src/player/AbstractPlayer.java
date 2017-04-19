package player;

import java.util.ResourceBundle;

import engine.game.gameloop.GameLoop;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import polyglot.Polyglot;

public abstract class AbstractPlayer extends BorderPane {

	private Stage stage;
	private Polyglot polyglot;
	private ResourceBundle IOResources;
	private MediaPlayer songPlayer;
	
	private Loader loader;
	private GameLoop gameLoop;
	private Scene gameScene;
	private Scene loadScene;

	public AbstractPlayer(Stage primaryStage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		this.stage = primaryStage;
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		this.loader = loader;
		
		playSong();
		buildStage();
		buildGameView();
		gameLoop.startTimeline();
	}

	private void buildStage() {
		loadScene = stage.getScene();
		
		gameScene = new Scene(this, 600, 600);
		gameScene.getStylesheets().add(IOResources.getString("StylesheetPath"));

		stage.setScene(gameScene);
	}

	protected void buildGameView() {
		if (!loader.getGamePath().equals("")) {
			Overlay scorebar = new Overlay(polyglot, IOResources);
			gameLoop = new GameLoop(gameScene, loader.loadGame(), scorebar);
			
			StackPane pane = new StackPane();
			pane.getChildren().addAll(gameLoop.getGameView(), scorebar.display());
			this.setCenter(pane);
		}
	}
	
	private void playSong() {
		try {
			songPlayer = loader.getMediaPlayer();
			songPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			songPlayer.play();
		} catch (Exception e) {
			//TODO
		}
	}
	
	protected void exit() {
		if (!loader.getGamePath().equals("")) {
			gameLoop.pauseTimeline();
			if (songPlayer != null) { 
				songPlayer.pause();
			}
		}
		stage.setScene(loadScene);
	}

	protected GameLoop getRunningGameLoop() {
		return this.gameLoop;
	}
	
	protected Loader getLoader() {
		return this.loader;
	}
	
	protected ResourceBundle getResources() {
		return IOResources;
	}
	
	protected Polyglot getPolyglot() {
		return polyglot;
	}
}
