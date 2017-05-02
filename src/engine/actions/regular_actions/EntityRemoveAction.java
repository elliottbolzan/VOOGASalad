package engine.actions.regular_actions;

import engine.actions.Action;
import javafx.application.Platform;

/**
 * Remove the Entity associated with this Action from the game.
 * 
 * @author Kyle Finke
 *
 */
public class EntityRemoveAction extends Action {

	@Override
	public void act() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getEntity().setIsVisible(false);
				getGameInfo().getObservableBundle().detachEntityFromAll(getEntity());
				getGameInfo().getLevelManager().getCurrentLevel().removeEntity(getEntity());
				getGameInfo().getGraphicsEngine().updateView();
			}
		});
	}
}
