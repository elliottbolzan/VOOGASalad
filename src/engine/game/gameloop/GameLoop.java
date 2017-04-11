package engine.game.gameloop;

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
	
	public GameLoop(Scene gameScene, String dataFolderPath){
		//Instantiate GraphicsEngine
		graphicsEngine = new GraphicsEngine(dataFolderPath);
		
		// Setup Observables - at beginning of entire game only
		observableBundle = new ObservableBundle();
		
		// Setup levelManager
		levelManager = new LevelManager(dataFolderPath);
		levelManager.loadAllSavedLevels();  //now done within LevelStepStrategy to refresh levels when they restart
		
		//Setup the first level screen
		level1Screen = new Screen(new LevelStepStrategy(), observableBundle, levelManager, gameScene, graphicsEngine);
	}
	
	public void startTimeline(){
		level1Screen.start();
	}
	
	public void pauseTimeline(){
		level1Screen.pause();
	}
	
	public Pane getGameView() {
		return graphicsEngine.getView();
	}
	
	public Label getGameScorebar() {
		return graphicsEngine.getScorebarDisplay();
	}
}

