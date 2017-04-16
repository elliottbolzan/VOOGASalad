package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import game_data.Game;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import player.Overlay;

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
	
	public GameLoop(Scene gameScene, Game game){
		//Instantiate GraphicsEngine
		graphicsEngine = new GraphicsEngine(game);
		
		// Setup Observables - at beginning of entire game only
		observableBundle = new ObservableBundle();
		
		//Setup scorebar
		Scorebar scorebar = graphicsEngine.getScorebar();
	
		// Setup levelManager
		levelManager = new LevelManager(game);
		//levelManager.loadAllSavedLevels();  //now done within LevelStepStrategy to refresh levels when they restart
		
		//Setup the first level screen
		StepStrategy strategy = new LevelStepStrategy();
		GameInfo info = new GameInfo(observableBundle, strategy, scorebar, level1Screen);
		this.info = info;
		level1Screen = new Screen(strategy, levelManager, gameScene, graphicsEngine, info);
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
	
	public Overlay getGameScorebar() {
		return graphicsEngine.getScorebarDisplay();
	}
}

