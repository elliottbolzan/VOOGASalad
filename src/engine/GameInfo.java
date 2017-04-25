package engine;

import engine.game.LevelManager;
import engine.game.gameloop.GameLoop;
import engine.game.gameloop.ObservableBundle;
import engine.game.gameloop.Scorebar;
import engine.game.gameloop.TimelineManipulator;
import engine.graphics.GraphicsEngine;

/**
 * @author nikita matt This class is used to convey information about the
 *         current status of the game from things that monitor the status of the
 *         game (collision detection, timer, input detection, etc), to things
 *         that observe that status (actions, events, etc).
 */
public class GameInfo {
	private ObservableBundle bundle; // immutable/no setter (same for whole game)
	private Scorebar scorebar; // immutable/no setter (same for whole game)
	private TimelineManipulator timelineManipulator;
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;
	/**
	 * True if entities have never been updated in current game. Once updated for first time, false for rest of game.
	 */
	private boolean entitiesNeverUpdated;
	
	public GameInfo(GameLoop gameLoop) {
		this.bundle = gameLoop.getObservableBundle();
		this.scorebar = gameLoop.getScorebar();
		this.timelineManipulator = gameLoop.timelineManipulator();
		this.levelManager = gameLoop.getLevelManager();
		this.graphicsEngine = gameLoop.getGraphicsEngine();
		this.entitiesNeverUpdated = true;
	}
	
	public void setEntitiesNeverUpdatedFalse(){
		entitiesNeverUpdated = false;
	}
	
	public boolean getEntitiesNeverUpdated(){
		return entitiesNeverUpdated;
	}
	
	public LevelManager getLevelManager(){
		 return levelManager;
	}
	
	public ObservableBundle getObservableBundle() {
		return bundle;
	}
	
	public TimelineManipulator getTimelineManipulator(){
		return timelineManipulator;
	}


	public Scorebar getScorebar() {
		return scorebar;
	}
	
	public GraphicsEngine getGraphicsEngine(){
		 return graphicsEngine;
	}
	
	/*
	NOTE TO OTHER PROGRAMMERS: Call this in LevelStepStrategy right before act(). Replaces any "setters"; if need to
	set a field in GameInfo, add it here. Currently empty because all fields in GameInfo are currently immutable (set
	only one through constructor. 
	*/
	/*
	public void updateFieldsBeforeAct(LevelStepStrategy levelStepStrategy){
		 
	}
	*/
}