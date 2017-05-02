package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.game.gameloop.Screen;

/**
 * Make an entity move randomly. Can set the maximum speed at which an entity is
 * allowed to move.
 * 
 * @author nikita
 */
public class RandomMoveAction extends Action {
	private int steps;

	public RandomMoveAction() {
		addParam(new Parameter(getResource("MaxSpeed"), double.class, 0.0));
		steps = 0;
	}

	@Override
	public void act() {
		if (steps % Screen.FRAME_TIME_MILLISECONDS == 0) {
			getEntity().setXSpeed(getRandomSign() * Math.random() * (double) getParam(getResource("MaxSpeed")));
			getEntity().setYSpeed(getRandomSign() * Math.random() * (double) getParam(getResource("MaxSpeed")));
		}
		steps++;
	}

	private int getRandomSign() {
		return Math.random() > 0.5 ? -1 : 1;
	}
}
