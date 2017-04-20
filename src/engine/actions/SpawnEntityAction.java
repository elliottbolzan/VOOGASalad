package engine.actions;

import java.lang.reflect.Constructor;

import engine.Action;
import engine.CollisionSide;
import engine.Entity;
import engine.Parameter;
import engine.game.EngineController;

public class SpawnEntityAction extends Action {

	public SpawnEntityAction() {
		addParam(new Parameter("Entity Type", String.class, ""));
		addParam(new Parameter("Entity Name", String.class, ""));
		addParam(new Parameter("Side", String.class, ""));
		addParam(new Parameter("X Speed", double.class, 0));
		addParam(new Parameter("Y Speed", double.class, 0));
	}

	@Override
	public void act() {
		EngineController controller = new EngineController();
		Entity newEntity = controller.createEntity((String) getParam("Entity"));
		newEntity.setX((double) getParam("X Speed"));
		newEntity.setY((double) getParam("Y Speed"));
		String side = ((String) getParam("Side"));
		CollisionSide collisionSide = getCollisionSide(side.toUpperCase());
		collisionSide.placeEntity(getEntity(), newEntity);
		newEntity.getGameInfo().getLevelManager().getCurrentLevel().addEntity(newEntity);
	}

	private CollisionSide getCollisionSide(String side) {
		try {
			Class<?> clazz = Class.forName("engine.CollisionSide." + side);
			Constructor<?> ctor = clazz.getDeclaredConstructor();
			return (CollisionSide) ctor.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
}
