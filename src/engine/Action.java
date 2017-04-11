package engine;

import engine.game.gameloop.StepStrategy;

public abstract class Action extends GameObject implements ActionInterface {
	
	private StepStrategy currentStepStrategy;

	public Action() {
		super("Action");
	}

	@Override
	public abstract void act();
	
}