package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;
import javafx.scene.input.KeyCode;

/**
 * Event that reacts to a key press from the user
 * 
 * @author nikita
 */
public class KeyPressEvent extends Event {

	public KeyPressEvent() {
		addParam(new Parameter("Key", KeyCode.class, KeyCode.UNDEFINED));
	}

	@Override
	public boolean act() {
		return getGameInfo().getObservableBundle().getInputObservable().isKeyPressToProcess()
				&& getParam("Key").equals(getGameInfo().getObservableBundle().getInputObservable().getLastPressedKey());
	}
}