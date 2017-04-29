package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.collisions.CollisionSide;
import engine.entities.Entity;
import exceptions.ActionException;
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
					for (Entity entity : getGameInfo().getLevelManager().getGame().getDefaults()) {
						if (((String) getParam("Entity Name")).equals(entity.getName())) {
							newEntity = entity.clone();
							newEntity.setGameInfo(getGameInfo());
							break;
						}
					}
					newEntity.setZ(getEntity().getZ());
					newEntity.setXSpeed((Double) newEntity.getParam("X Speed"));
					newEntity.setYSpeed((Double) newEntity.getParam("Y Speed"));
					newEntity.setXAcceleration((Double) newEntity.getParam("X Acceleration"));
					newEntity.setYAcceleration((Double) newEntity.getParam("Y Acceleration"));
					String side = ((String) getParam("Side"));
					CollisionSide collisionSide;
					try {
						collisionSide = getCollisionSide(side.toUpperCase());
					} catch (Exception e) {
						throw new ActionException(getResource("InvalidCollisionSide"));
					}
					if ((boolean) getParam("Random Spawn"))
						collisionSide.placeEntityRandomly(getEntity(), newEntity);
					else
						collisionSide.placeEntity(getEntity(), newEntity);
					newEntity.getGameInfo().getLevelManager().getCurrentLevel().addEntity(newEntity);
					newEntity.getGameInfo().getObservableBundle().attachEntityToAll(newEntity);
					newEntity.getGameInfo().getGraphicsEngine().updateView();
				}
			});
		}
	}

	private CollisionSide getCollisionSide(String side) {
		return CollisionSide.valueOf(side);
	}
}
