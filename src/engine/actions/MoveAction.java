package engine.actions;

import engine.Action;
import engine.Entity;
import engine.game.gameloop.Screen;

public class MoveAction extends Action {

	public static final double TIME_STEP = Screen.FRAME_TIME_MILLISECONDS/50.0;
	@Override
	public void act() {
		Entity entity = getEntity();
		System.out.println("BEFORE: " + entity.getY());
		System.out.println("TIMESTEP: " + TIME_STEP + " YSPEED: " + entity.getYSpeed() + " YACCELERATION: " + entity.getYAcceleration());
		entity.setX(entity.getX() + entity.getXSpeed() * TIME_STEP);
		entity.setY(entity.getY() + entity.getYSpeed() * TIME_STEP);
		System.out.println("AFTER: " + entity.getY());
		entity.setXSpeed(entity.getXSpeed() + entity.getXAcceleration() * TIME_STEP);
		entity.setYSpeed(entity.getYSpeed() + entity.getYAcceleration() * TIME_STEP);
	}
}