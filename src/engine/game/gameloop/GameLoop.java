package engine.game.gameloop;

import java.util.ResourceBundle;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import game_data.Game;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import player.score.Overlay;
import polyglot.Polyglot;

/**
 * Manages the highest level of time flow in the game. The client class for the game loop.
 * 
 * @author Matthew Barbano
 *
 */
public class GameLoop {
	private ObservableBundle observableBundle;
	private LevelManager levelManager;
	private Screen level1Screen;
	private GraphicsEngine graphicsEngine;
	private GameInfo info;
	
	public GameLoop(Scene gameScene, Game game, Overlay overlay, Stage stage, Polyglot polyglot, ResourceBundle IOResources){
		//Instantiate GraphicsEngine
		graphicsEngine = new GraphicsEngine(game, overlay, stage, polyglot, IOResources);
		
		//TODO: what happens if level changes, camera gets reset??
		graphicsEngine.setCamera(game.getCamera());
		
		//Setup scorebar
		Scorebar scorebar = graphicsEngine.getScorebar();
	
		// Setup Observables - at beginning of entire game only
		observableBundle = new ObservableBundle();
		
		// Setup levelManager
		levelManager = new LevelManager(game);
		
		//Setup the first level screen
		StepStrategy strategy = new LevelStepStrategy();
		GameInfo info = new GameInfo(observableBundle, strategy, scorebar, level1Screen, levelManager, graphicsEngine);
		this.info = info;
		level1Screen = new Screen(strategy, gameScene, graphicsEngine, info);
	}
	
	public void startTimeline(){
		info.getCurrentScreen().start();
	}
	
	public void pauseTimeline(){
		info.getCurrentScreen().pause();
	}
	
	public Pane getGameView() {
		return graphicsEngine.getView();
	}
}

