package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * Subclass of StepStrategy implementing step() when a screen of buttons for
 * selecting which level to play should be displayed.
 * 
 * @author Matthew Barbano
 *
 */
public class LevelSelectionStepStrategy implements StepStrategy{
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;
	private GameInfo info;
	private boolean firstPass;
	
	public LevelSelectionStepStrategy(boolean firstPass){
		 this.firstPass = firstPass;
	}
	
	@Override
	public void setup(LevelManager levelManager, GraphicsEngine graphicsEngine, GameInfo info) {
		this.levelManager = levelManager;
		this.graphicsEngine = graphicsEngine;
		this.info = info;
		graphicsEngine.displayLevelSelectionScreen(levelManager, this);
		graphicsEngine.blankScorebar(firstPass);
	}

	@Override
	public void step() {
		//Intentionally left blank
	}
	
	public void moveToLevelScreen(int levelNumber){
		levelManager.getCurrentScreen().getTimeline().stop();
		boolean hasSelectedLevel = levelManager.setLevelNumber(levelNumber);
		if(hasSelectedLevel){
			StepStrategy nextStepStrategy = new LevelStepStrategy();
			levelManager.setCurrentStepStrategy(nextStepStrategy);
			Screen nextScreen = new Screen(levelManager, graphicsEngine, info, false);
			nextScreen.getTimeline().play();
		}
	}
	
}
