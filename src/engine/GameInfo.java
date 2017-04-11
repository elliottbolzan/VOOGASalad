package engine;

import engine.game.gameloop.ObservableBundle;
import engine.game.gameloop.StepStrategy;

public class GameInfo {
	private ObservableBundle bundle;
	private StepStrategy currentStepStrategy;

	public GameInfo(ObservableBundle bundle, StepStrategy strategy){
		this.bundle = bundle;
		this.currentStepStrategy = strategy;
	}
	public void setCurrentStepStrategy(StepStrategy strategy) {
		this.currentStepStrategy = strategy;
	}

	public void setObservableBundle(ObservableBundle bundle) {
		this.bundle = bundle;
	}

	public ObservableBundle getObservableBundle() {
		return bundle;
	}

	public StepStrategy getCurrentStepStrategy() {
		return currentStepStrategy;
	}
}
