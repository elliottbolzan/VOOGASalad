package engine;

import engine.game.gameloop.LevelStepStrategy;
import engine.game.gameloop.ObservableBundle;
import engine.game.gameloop.Scorebar;
import engine.game.gameloop.Screen;
import engine.game.gameloop.StepStrategy;

/**
 * @author nikita matt This class is used to convey information about the
 *         current status of the game from things that monitor the status of the
 *         game (collision detection, timer, input detection, etc), to things
 *         that observe that status (actions, events, etc).
 */
public class GameInfo {
	private ObservableBundle bundle; // immutable/no setter (same for whole game)
	private Scorebar scorebar; // immutable/no setter (same for whole game)
	private LevelEnder levelEnder;
	
	public GameInfo(ObservableBundle bundle, Scorebar scorebar, LevelEnder levelEnder) {
		this.bundle = bundle;
		this.scorebar = scorebar;
		this.levelEnder = levelEnder;
	}

	/*
	public void setCurrentStepStrategy(StepStrategy strategy) {
		this.currentStepStrategy = strategy;
	}
	*/

	public ObservableBundle getObservableBundle() {
		return bundle;
	}

	public StepStrategy getCurrentStepStrategy() {
		return currentStepStrategy;
	}

	public Scorebar getScorebar() {
		return scorebar;
	}
	
	/*
	public void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
	}
	*/
	
	//I think may not be necessary because all fields are now immutable (just set once through constructor)!
	/*
	public void updateFieldsBeforeAct(LevelStepStrategy levelStepStrategy){
		 this.currentScreen = levelStepStrategy.getCurrentScreen();
		 this.currentLevelEnder = levelStepStrategy.getLevelEnder();
	}
	*/
}
