package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Manages the highest level of time flow in the game.
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
	
	public GameLoop(Scene gameScene, String dataFolderPath){
		//Instantiate GraphicsEngine
		graphicsEngine = new GraphicsEngine(dataFolderPath);
		
		// Setup Observables - at beginning of entire game only
		observableBundle = new ObservableBundle();
		
		//Setup scorebar
		Scorebar scorebar = new Scorebar();
		graphicsEngine.setScorebar(scorebar);
	
		// Setup levelManager
		levelManager = new LevelManager(dataFolderPath);
		levelManager.loadAllSavedLevels();  //now done within LevelStepStrategy to refresh levels when they restart
		
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
	
	public Label getGameScorebar() {
		return graphicsEngine.getScorebarDisplay();
	}
}

