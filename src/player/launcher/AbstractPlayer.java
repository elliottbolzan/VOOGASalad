package player.launcher;

import java.util.ResourceBundle;
import engine.game.gameloop.GameLoop;
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
	
	private Stage stage;
	private Scene gameScene;
	private Scene loadScene;
	
	private Game game;
	private GameLoop gameLoop;

	public AbstractPlayer(Stage primaryStage, Game game, Polyglot polyglot, ResourceBundle IOResources) {
		super(polyglot, IOResources);
		this.stage = primaryStage;
		this.game = game;
		
		this.buildStage();
		this.buildGameView();
	}
	
	private void buildStage() {
		loadScene = stage.getScene();
		gameScene = this.createScene(600, 600);

		stage.setScene(gameScene);
		stage.centerOnScreen();
		stage.setOnCloseRequest(e -> this.exit());
	}
	
	protected void buildGameView() {
		Overlay scorebar = new Overlay(this.getPolyglot(), this.getResources());
		gameLoop = new GameLoop(gameScene, game, scorebar);
		
		StackPane pane = new StackPane();
		pane.getChildren().addAll(gameLoop.getGameView(), scorebar.display());
		this.setCenter(pane);
	}
	
	protected GameLoop getRunningGameLoop() {
		return this.gameLoop;
	}
	
	protected Game getGame() {
		return this.game;
	}
	
	protected void exit() {
		gameLoop.pauseTimeline();
		
		if(loadScene != null) {
			stage.setScene(loadScene);
		} else {
			stage.close();
		}
	}
}
