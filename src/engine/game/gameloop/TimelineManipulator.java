package engine.game.gameloop;

import engine.Entity;
import engine.GameInfo;
import engine.game.LevelManager;

/**
 * Allows Game Objects (Entities, Events, Actions) to manipulate the timeline, including
 * ending levels (DieAction, NextLevelAction), and pausing the timeline.
 * @author Matthew Barbano
 *
 */

public class TimelineManipulator {
	private LevelManager levelManager;
	private GameInfo info;
	
	public TimelineManipulator(LevelManager levelManager){
		this.levelManager = levelManager;
	}
	
	public void setInfo(GameInfo info){
		this.info = info;
	}
	
	public void start(){
		levelManager.getCurrentScreen().start();
	}
	
	public void pause(){
		levelManager.getCurrentScreen().pause();
	}
	
	
	public void startNewLevel(int newLevel) {
		for(Entity entity : levelManager.getCurrentLevel().getEntities()){
			info.getObservableBundle().detachEntityFromAll(entity);
		}
		moveToNextScreen(new NewLevelStepStrategy(levelManager, newLevel));
	}
	
	/**
	 * Logic for ending this level screen when won the level. IMPORTANT: Called from NextLevelAction
	 * (and can be called from other Actions), NOT from step(). Stops this screen's
	 * timeline, instantiates the next screen with a NextLevelStepStrategy, and starts that timeline.
	 * Although this method uses a Timeline, it is specific to Level Screens, so
	 * I put it here in LevelStepStrategy rather than in Screen.
	 */
	
	public void startNextLevel() {
		for(Entity entity : levelManager.getCurrentLevel().getEntities()){
			info.getObservableBundle().detachEntityFromAll(entity);
		}
		moveToNextScreen(new NextLevelStepStrategy(levelManager));
	}

	/**
	 * Logic for ending this level screen when you die. IMPORTANT: Called from DieAction
	 * (and can be called from other Actions), NOT from step(). Stops this screen's
	 * timeline, instantiates the next screen with a TransitionStepStrategy
	 * appropriate to whether there is a gameOver, and starts that timeline.
	 * Although this method uses a Timeline, it is specific to Level Screens, so
	 * I put it here in LevelStepStrategy rather than in Screen.
	 * 
	 * @param gameOver
	 */

	public void die(boolean gameOver) {
		for(Entity entity : levelManager.getCurrentLevel().getEntities()){
			info.getObservableBundle().detachEntityFromAll(entity);
		}
		StepStrategy nextStepStrategy;
		if (gameOver) {
			nextStepStrategy = new GameOverStepStrategy(levelManager);
		} else {
			nextStepStrategy = new LoseLifeStepStrategy(levelManager);
		}
		moveToNextScreen(nextStepStrategy);
	}
	
	private void moveToNextScreen(StepStrategy nextStepStrategy) {
		levelManager.getCurrentScreen().getTimeline().stop();
		((LevelStepStrategy) levelManager.getCurrentStepStrategy()).flagScreenFinished(nextStepStrategy);
	}
	

}
