package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

public class BooleanEvent extends Event {
	private BooleanParser parser;

	public BooleanEvent() {
		addParam(new Parameter(getResource("Expression"), String.class, ""));
		parser = new BooleanParser();
	}

	@Override
	public boolean act() {
		try {
			return parser.parse((String) getParam(getResource("Expression")));
		} catch (Exception e) {

		}
	}
}
