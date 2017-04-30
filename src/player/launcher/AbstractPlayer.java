package player.launcher;

import java.util.ResourceBundle;

import engine.game.gameloop.GameLoop;
import engine.game.gameloop.Scorebar;
import engine.graphics.GraphicsEngine;
import game_data.Game;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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
	private Polyglot polyglot;
	private ResourceBundle resources;

	public AbstractPlayer(Stage primaryStage, Game game, Polyglot polyglot, ResourceBundle IOResources, boolean firstTimeLoading) {
		super(polyglot, IOResources);
		
		this.stage = primaryStage;
		this.game = game;
		this.polyglot = polyglot;
		this.resources = IOResources;
		
		this.buildStage();
		this.buildGameView(firstTimeLoading);
	}
	
	public void endGame(Scorebar scorebar) {
		//Do nothing by default (Null Object Design Pattern)
	}
	
	protected GameLoop getRunningGameLoop() {
		return this.gameLoop;
	}
	
	protected Game getGame() {
		return this.game;
	}
	
	protected Stage getStage() {
		return this.stage;
	}
	
	protected void buildGameView(boolean firstTimeLoading) {
		Overlay overlay = new Overlay(this.getPolyglot(), this.getResources());
		gameLoop = new GameLoop(gameScene, game, new GraphicsEngine(game, this, overlay, polyglot, resources), firstTimeLoading);
		
		StackPane pane = new StackPane();
		pane.getChildren().addAll(gameLoop.getGameView(), overlay.display());

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
		//stage.setMaxHeight(gameScene.getHeight());
		//stage.setMaxWidth(gameScene.getWidth());
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
