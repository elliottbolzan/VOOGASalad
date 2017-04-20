package engine.actions;

import java.lang.reflect.Constructor;

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
		addParam(new Parameter("X Speed", double.class, 0));
		addParam(new Parameter("Y Speed", double.class, 0));
	}

	@Override
	public void act() {
		System.out.println("INSIDE ACT");
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				System.out.println("INSIDE RUN");
				Entity newEntity = null;
				for (Entity entity : getGameInfo().getLevelManager().getCurrentLevel().getEntities()) {
					if (((String) getParam("Entity Name")).equals(entity.getName()))
						newEntity = entity.clone();
				}
				newEntity.setX((Double) getParam("X Speed"));
				newEntity.setY((Double) getParam("Y Speed"));
				String side = ((String) getParam("Side"));
				CollisionSide collisionSide = getCollisionSide(side.toUpperCase());
				collisionSide.placeEntity(getEntity(), newEntity);
				if (newEntity.getXSpeed() != 0 || newEntity.getYSpeed() != 0) {
					Event event = new AlwaysEvent();
					event.addAction(new MoveAction());
					newEntity.addEvent(event);
				}
				newEntity.getGameInfo().getLevelManager().getCurrentLevel().addEntity(newEntity);
			}
		});
//		newEntity.getGameInfo().getLevelManager().getCurrentLevel().addEntity(newEntity);
	}

	private CollisionSide getCollisionSide(String side) {
		try {
			//Class<?> clazz = Class.forName("engine.CollisionSide." + side);
			CollisionSide collisionSide = CollisionSide.valueOf(side); 
			//Constructor<?> ctor = clazz.getDeclaredConstructor();
			return collisionSide;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
