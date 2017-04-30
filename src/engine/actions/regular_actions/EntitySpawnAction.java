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
public class EntitySpawnAction extends Action {
	public EntitySpawnAction() {
		addParam(new Parameter(getResource("EntityName"), String.class, ""));
		addParam(new Parameter(getResource("Side"), String.class, ""));
		addParam(new Parameter(getResource("SpawnProbability"), double.class, 1.0));
		addParam(new Parameter(getResource("RandomSpawn"), boolean.class, false));
	}

	@Override
	public void act() {
		if (Math.random() < (double) getParam(getResource("SpawnProbability"))) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Entity newEntity = null;
					for (Entity entity : getGameInfo().getLevelManager().getGame().getDefaults()) {
						if (((String) getParam(getResource("EntityName"))).equals(entity.getName())) {
							newEntity = entity.clone();
							newEntity.setGameInfo(getGameInfo());
							break;
						}
					}
					newEntity.setZ(getEntity().getZ());
					newEntity.setXSpeed(newEntity.getXSpeed());
					newEntity.setYSpeed(newEntity.getYSpeed());
					newEntity.setXAcceleration(newEntity.getXAcceleration());
					newEntity.setYAcceleration(newEntity.getYAcceleration());
					String side = ((String) getParam(getResource("Side")));
					CollisionSide collisionSide;
					try {
						collisionSide = getCollisionSide(side.toUpperCase());
					} catch (Exception e) {
						throw new ActionException(getResource("InvalidCollisionSide"));
					}
					if ((boolean) getParam(getResource("RandomSpawn")))
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
