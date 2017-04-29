package engine.actions.regular_actions;

import engine.actions.Action;
import javafx.application.Platform;

public class RemoveEntityAction extends Action {

	@Override
	public void act() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getEntity().setIsVisible(false);
				getGameInfo().getObservableBundle().detachEntityFromAll(getEntity());
				getGameInfo().getLevelManager().getCurrentLevel().removeEntity(getEntity());
			}
		});
	}
}
