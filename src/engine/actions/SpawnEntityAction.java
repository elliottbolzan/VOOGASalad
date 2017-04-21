package engine.actions;

import engine.Action;
import engine.CollisionSide;
import engine.Entity;
import engine.Event;
import engine.Parameter;
import engine.events.AlwaysEvent;
import javafx.application.Platform;

public class SpawnEntityAction extends Action {

	public SpawnEntityAction() {
		// addParam(new Parameter("Entity Type", String.class, ""));
		addParam(new Parameter("Entity Name", String.class, ""));
		addParam(new Parameter("Side", String.class, ""));
		addParam(new Parameter("X Speed", double.class, 0.0));
		addParam(new Parameter("Y Speed", double.class, 0.0));
		addParam(new Parameter("X Acceleration", double.class, 0.0));
		addParam(new Parameter("Y Acceleration", double.class, 0.0));
	}

	@Override
	public void act() {
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
				collisionSide.placeEntity(getEntity(), newEntity);
				if (newEntity.getXSpeed() != 0 || newEntity.getYSpeed() != 0) {
					Event event = new AlwaysEvent();
					event.setEntity(newEntity);
					Action moveAction = new MoveAction();
					moveAction.setEntity(newEntity);
					event.addAction(moveAction);
					newEntity.addEvent(event);
				}
				newEntity.getGameInfo().getLevelManager().getCurrentLevel().addEntity(newEntity);
				newEntity.getGameInfo().getGraphicsEngine().updateView();
			}
		});
	}

	private CollisionSide getCollisionSide(String side) {
			return CollisionSide.valueOf(side);
	}
}
