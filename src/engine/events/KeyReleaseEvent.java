package engine.events;

import engine.Event;
import engine.Parameter;
import engine.game.eventobserver.InputObservable;
import javafx.scene.input.KeyCode;

public class KeyReleaseEvent extends Event {

	public KeyReleaseEvent() {
		addParam(new Parameter("Key", KeyCode.class, KeyCode.UNDEFINED));
	}

	@Override	
	public boolean act(){
		if (getGameInfo().getObservableBundle().getInputObservable().isKeyReleaseToProcess()){
			if (getParam("Key").equals(getGameInfo().getObservableBundle().getInputObservable().getLastPressedKey()))
				return true;
		}
		return false;
	}

}
