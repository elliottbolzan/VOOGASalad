package engine.events;

import engine.Event;
import engine.Parameter;
import javafx.scene.input.KeyCode;

/**
 * @author nikita Event that reacts to a key press from the user
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