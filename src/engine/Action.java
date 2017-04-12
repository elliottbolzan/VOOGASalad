package engine;

import java.util.ResourceBundle;

import engine.game.gameloop.StepStrategy;

public abstract class Action extends GameObject implements ActionInterface {
	private transient ResourceBundle actionExceptions = ResourceBundle.getBundle("resources/ActionExceptions");
	
	private StepStrategy currentStepStrategy;

	public Action() {
		super("Action");
	}

	@Override
	public abstract void act();
	
	protected ResourceBundle getActionExceptionsBundle(){
		return actionExceptions;
	}
	
}