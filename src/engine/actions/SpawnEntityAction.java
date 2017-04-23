package engine.actions;

import engine.Action;
import engine.CollisionSide;
import engine.Entity;
import engine.Parameter;
import javafx.application.Platform;

/**
 * Spawn an entity with given parameters.
 * 
 * @author nikita
 */
public class SpawnEntityAction extends Action {

	public SpawnEntityAction() {
		addParam(new Parameter("Entity Name", String.class, ""));
		addParam(new Parameter("Side", String.class, ""));
		addParam(new Parameter("X Speed", double.class, 0.0));
		addParam(new Parameter("Y Speed", double.class, 0.0));
		addParam(new Parameter("X Acceleration", double.class, 0.0));
		addParam(new Parameter("Y Acceleration", double.class, 0.0));
		addParam(new Parameter("Spawn Probability", double.class, 1.0));
		addParam(new Parameter("Random Spawn", boolean.class, false));
	}

	@Override
	public void act() {
		if (Math.random() < (double) getParam("Spawn Probability")) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Entity newEntity = null;
					for (Entity entity : getGameInfo().getLevelManager().getCurrentLevel().getEntities()) {
						if (((String) getParam("Entity Name")).equals(entity.getName())) {
							newEntity = entity.clone();
							break;
						}
					}
					newEntity.setXSpeed((Double) getParam("X Speed"));
					newEntity.setYSpeed((Double) getParam("Y Speed"));
					newEntity.setXAcceleration((Double) getParam("X Acceleration"));
					newEntity.setYAcceleration((Double) getParam("Y Acceleration"));
					String side = ((String) getParam("Side"));
					CollisionSide collisionSide = getCollisionSide(side.toUpperCase());
					if ((boolean) getParam("Random Spawn"))
						collisionSide.placeEntityRandomly(getEntity(), newEntity);
					else
						collisionSide.placeEntity(getEntity(), newEntity);
					newEntity.getGameInfo().getLevelManager().getCurrentLevel().addEntity(newEntity);
					newEntity.getGameInfo().getGraphicsEngine().updateView();
				}
			});
		}
	}

	private CollisionSide getCollisionSide(String side) {
		return CollisionSide.valueOf(side);
	}
}
