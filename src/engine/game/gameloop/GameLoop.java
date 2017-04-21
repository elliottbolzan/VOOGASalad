package engine.game.gameloop;

import engine.GameInfo;
import engine.TimelineManipulator;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import game_data.Game;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import player.score.Overlay;

/**
 * Manages the highest level of time flow in the game. The client class for the game loop.
 * 
 * @author Matthew Barbano
 *
 */
public class GameLoop {
	private ObservableBundle observableBundle;
	private Scorebar scorebar;
	private TimelineManipulator levelEnder;
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;
	
	public GameLoop(Scene gameScene, Game game, Overlay overlay){
		graphicsEngine = new GraphicsEngine(game, overlay);
		graphicsEngine.setCamera(game.getCamera());
		scorebar = graphicsEngine.getScorebar();
		observableBundle = new ObservableBundle(gameScene);
		
		levelManager = new LevelManager(game, new LevelStepStrategy());
		levelManager.loadAllSavedLevels();
		levelEnder = new TimelineManipulator(levelManager, graphicsEngine);
		GameInfo info = new GameInfo(this);
		Screen level1Screen = new Screen(levelManager, graphicsEngine, info);
		levelManager.setCurrentScreen(level1Screen);
		levelEnder.setInfo(info);
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
	
	public TimelineManipulator getLevelEnder(){
		return levelEnder;
	}
	
	public LevelManager getLevelManager(){
		return levelManager;
	}
	
	public GraphicsEngine getGraphicsEngine(){
		return graphicsEngine;
	}
}
