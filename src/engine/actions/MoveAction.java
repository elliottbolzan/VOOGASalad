package engine.actions;

import engine.Action;
import engine.Entity;
import engine.game.gameloop.Screen;

public class MoveAction extends Action {

	public static final int TIME_STEP = Screen.FRAME_TIME_MILLISECONDS/1000;
	@Override
	public void act() {
		Entity entity = getEntity();
		
		entity.setX(entity.getX() + entity.getXSpeed() * TIME_STEP);
		entity.setY(entity.getY() + entity.getYSpeed() * TIME_STEP);
		
		entity.setXSpeed(entity.getXSpeed() + entity.getXAcceleration() * TIME_STEP);
		entity.setYSpeed(entity.getYSpeed() + entity.getYAcceleration() * TIME_STEP);
	}
}