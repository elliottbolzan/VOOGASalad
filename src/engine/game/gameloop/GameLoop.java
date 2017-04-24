package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import game_data.Game;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Manages the highest level of time flow in the game. The client class for the game loop.
 * 
 * @author Matthew Barbano
 *
 */
public class GameLoop {
	private ObservableBundle observableBundle;
	private Scorebar scorebar;
	private TimelineManipulator timelineManipulator;
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;
	
	public GameLoop(Scene gameScene, Game game, GraphicsEngine graphicsEngine){
		this.graphicsEngine = graphicsEngine;
		scorebar = graphicsEngine.getScorebar();
		observableBundle = new ObservableBundle(gameScene);
		
		levelManager = new LevelManager(game, new LevelStepStrategy());
		levelManager.loadAllSavedLevels();
		timelineManipulator = new TimelineManipulator(levelManager);
		GameInfo info = new GameInfo(this);
		Screen level1Screen = new Screen(levelManager, graphicsEngine, info);
		levelManager.setCurrentScreen(level1Screen);
		timelineManipulator.setInfo(info);
		graphicsEngine.getScorebar().setLevelManager(levelManager);
	}
	
	public void startTimeline(){
		levelManager.getCurrentScreen().start();
	}
	
	public void pauseTimeline(){
		levelManager.getCurrentScreen().pause();
	}
	
	public Pane getGameView() {
		return graphicsEngine.getView();
	}
	
	public ObservableBundle getObservableBundle(){
		return observableBundle;
	}
	
	public Scorebar getScorebar(){
		return scorebar;
	}
	
	public TimelineManipulator timelineManipulator(){
		return timelineManipulator;
	}
	
	public LevelManager getLevelManager(){
		return levelManager;
	}
	
	public GraphicsEngine getGraphicsEngine(){
		return graphicsEngine;
	}
}
