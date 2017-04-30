package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;
import javafx.scene.input.KeyCode;

/**
 * Event that reacts to a release of a key by a user
 * 
 * @author nikita
 */
public class KeyReleaseEvent extends Event {

	public KeyReleaseEvent() {
		addParam(new Parameter(getResource("Key"), KeyCode.class, KeyCode.UNDEFINED));
	}

	@Override
	public boolean act() {
		return getGameInfo().getObservableBundle().getInputObservable().isKeyReleaseToProcess()
				&& getParam(getResource("Key"))
						.equals(getGameInfo().getObservableBundle().getInputObservable().getLastPressedKey());
	}

}
