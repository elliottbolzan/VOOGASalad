package engine.game.gameloop;

import engine.GameInfo;
import engine.LevelEnder;
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
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;
	
	public GameLoop(Scene gameScene, Game game, Overlay overlay){
		graphicsEngine = new GraphicsEngine(game, overlay);
		//TODO: what happens if level changes, camera gets reset??
		//TODO: oh no this doesnt work. This CameraEntity isn't part of the level, so it doesn't get updated :(
		graphicsEngine.setCamera(game.getCamera());
		Scorebar scorebar = graphicsEngine.getScorebar();
		ObservableBundle observableBundle = new ObservableBundle(gameScene);
		
		LevelEnder levelEnder = new LevelEnder(levelManager, graphicsEngine);
		GameInfo info = new GameInfo(observableBundle, scorebar, levelEnder);
		Screen level1Screen = new Screen(new LevelManager(game, new LevelStepStrategy()), graphicsEngine, info);
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
}

