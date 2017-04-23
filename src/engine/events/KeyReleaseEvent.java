package engine.events;

import engine.Event;
import engine.Parameter;
import javafx.scene.input.KeyCode;

/**
 * Event that reacts to a release of a key by a user
 * 
 * @author nikita
 */
public class KeyReleaseEvent extends Event {

	public KeyReleaseEvent() {
		addParam(new Parameter("Key", KeyCode.class, KeyCode.UNDEFINED));
	}

	@Override
	public boolean act() {
		return getGameInfo().getObservableBundle().getInputObservable().isKeyReleaseToProcess()
				&& getParam("Key").equals(getGameInfo().getObservableBundle().getInputObservable().getLastPressedKey());
	}

}
