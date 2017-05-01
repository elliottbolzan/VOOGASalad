package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.game.gameloop.Screen;

public class RandomMoveAction extends Action {
	private int steps;

	public RandomMoveAction() {
		addParam(new Parameter(getResource("MaxSpeed"), double.class, 0.0));
		steps = 0;
	}

	@Override
	public void act() {
		if (steps % Screen.FRAME_TIME_MILLISECONDS == 0) {
			getEntity().setXSpeed(getRandomSign() * Math.random() * (double) getParam("MaxSpeed"));
			getEntity().setYSpeed(getRandomSign() * Math.random() * (double) getParam("MaxSpeed"));
		}
		steps++;
	}
	private int getRandomSign(){
		return Math.random() > 0.5 ? -1 : 1;
	}
}
