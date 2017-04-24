package engine.events;

import engine.Event;
import engine.Parameter;
import javafx.scene.input.KeyCode;

/**
 * A level is defined as "unlocked" if the previous level in the game has been
 * won at least once (via a NextLevelAction or a NewLevelAction).
 * 
 * @author Matthew Barbano
 *
 */
public class LevelUnlockedEvent extends Event {

	public LevelUnlockedEvent() {
		addParam(new Parameter("Level", Integer.class, 0));
	}

	@Override
	public boolean act() {
		return getGameInfo().getLevelManager().getWonLevelNumbers().contains((Integer) getParam("Level") - 1)
				|| ((Integer) getParam("Level")) == 1;
	}
}
