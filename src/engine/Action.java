package engine;

public abstract class Action extends GameObject implements ActionInterface {
	
	public Action() {
		super("Action");
	}

	@Override
	public abstract void act();
	
}