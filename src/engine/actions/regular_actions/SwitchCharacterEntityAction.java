package engine.actions.regular_actions;

import engine.actions.Action;
import engine.Parameter;
import engine.entities.Entity;
import engine.entities.entities.CharacterEntity;
import javafx.application.Platform;

public class SwitchCharacterEntityAction extends Action {

	public SwitchCharacterEntityAction() {
		addParam(new Parameter("Character Entity", String.class, ""));
	}

	@Override
	public void act() {
		Platform.runLater(new Runnable() {
			public void run() {
				CharacterEntity tempEntity = (CharacterEntity) getEntity().clone();
				Entity newEntity = null;
				for (Entity entity : getGameInfo().getLevelManager().getCurrentLevel().getEntities()) {
					if (((String) getParam("Character Entity")).equals(entity.getName())) {
						newEntity = entity.clone();
						break;
					}
				}
				System.out.println(getEntity().getEvents());
				System.out.println(getEntity().getEvents().get(0).getActions());
				tempEntity.setImagePath(newEntity.getImagePath());
				tempEntity.setEvents(newEntity.getEvents());
				getGameInfo().getLevelManager().getCurrentLevel().removeEntity(getEntity());
				tempEntity.getGameInfo().getLevelManager().getCurrentLevel().addEntity(tempEntity);
				tempEntity.getGameInfo().getGraphicsEngine().updateView();
//				getGameInfo().getLevelManager().getCurrentLevel().removeEntity(getEntity());
//				getGameInfo().getGraphicsEngine().updateView();
			}
		});
	}

}
