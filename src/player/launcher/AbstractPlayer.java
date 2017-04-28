package player.launcher;

import java.util.ResourceBundle;
import engine.game.gameloop.GameLoop;
import engine.graphics.GraphicsEngine;
import game_data.Game;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import player.MediaManager;
import player.PlayerView;
import player.score.Overlay;
import polyglot.Polyglot;

/**
 * This class encapsulates the essential elements necessary to load and start playing a game.
 * @author Jay Doherty
 *
 */
public abstract class AbstractPlayer extends PlayerView {
	
	public static final int DEFAULT_WIDTH = 1000;
	public static final int DEFAULT_HEIGHT = 600;

	private Stage stage;
	private Scene gameScene;
	private Scene loadScene;
	
	private Game game;
	private GameLoop gameLoop;
	private MediaManager mediaManager;
	private Polyglot polyglot;
	private ResourceBundle IOResources;

	public AbstractPlayer(Stage primaryStage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(polyglot, IOResources);
		this.stage = primaryStage;
		this.game = game;
		this.mediaManager = mediaManager;
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		
		this.buildStage();
		this.buildGameView();
	}
	
	protected GameLoop getRunningGameLoop() {
		return this.gameLoop;
	}
	
	protected Game getGame() {
		return this.game;
	}
	
	protected void buildGameView() {
		Overlay scorebar = new Overlay(this.getPolyglot(), this.getResources());
		gameLoop = new GameLoop(gameScene, game, new GraphicsEngine(game, scorebar, stage, mediaManager, polyglot, IOResources));
		
		StackPane pane = new StackPane();
		pane.getChildren().addAll(gameLoop.getGameView(), scorebar.display());
		this.setCenter(pane);
	}
	
	protected void exit() {
		gameLoop.pauseTimeline();
		this.returnToLoadScreen();
	}
	
	private void buildStage() {
		loadScene = stage.getScene();
		gameScene = this.createScene(DEFAULT_WIDTH, DEFAULT_HEIGHT);	//TODO? Might be ok with resizing the game view and leaving this as is
		
		stage.setScene(gameScene);
		stage.centerOnScreen();
		stage.setOnCloseRequest(e -> this.exit());
	}
	
	private void returnToLoadScreen() {
		if(loadScene != null) {
			stage.setScene(loadScene);
		} else {
			stage.close();
		}
	}
}
