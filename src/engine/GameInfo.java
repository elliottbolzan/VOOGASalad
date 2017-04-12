package engine;

import engine.game.gameloop.ObservableBundle;
import engine.game.gameloop.Scorebar;
import engine.game.gameloop.Screen;
import engine.game.gameloop.StepStrategy;

public class GameInfo {
	private ObservableBundle bundle;  //immutable/no setter (same for whole game, only set once in constructor)
	private StepStrategy currentStepStrategy;
	private Scorebar scorebar; //immutable/no setter (same for whole game, only set once in constructor)
	private Screen currentScreen;
	
	public GameInfo(ObservableBundle bundle, StepStrategy strategy, Scorebar scorebar, Screen screen){
		this.bundle = bundle;
		this.currentStepStrategy = strategy;
		this.scorebar = scorebar;
		this.currentScreen = screen;
	}
	public void setCurrentStepStrategy(StepStrategy strategy) {
		this.currentStepStrategy = strategy;
	}

	public ObservableBundle getObservableBundle() {
		return bundle;
	}

	public StepStrategy getCurrentStepStrategy() {
		return currentStepStrategy;
	}
	
	public Scorebar getScorebar() {
		return scorebar;
	}
	
	public Screen getCurrentScreen() {
		return currentScreen;
	}
	
	public void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
	}
}
