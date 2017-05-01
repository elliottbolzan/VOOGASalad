package engine.events.regular_events;

import boolean_parser.BooleanParser;
import engine.Parameter;
import engine.events.Event;

/**
 * Evaluate a expression of booleans
 */
public class BooleanEvent extends Event {
	private BooleanParser parser;

	public BooleanEvent() {
		addParam(new Parameter(getResource("Expression"), String.class, ""));
		parser = new BooleanParser();
	}

	@Override
	public boolean act() {
		String[] expression = ((String) getParam(getResource("Expression"))).split("\\s+");
		String result = "";
		for (String str : expression) {
			if (isInt(str))
				result += String.valueOf(getEntity().getEventById(Integer.parseInt(str)).isTriggered(true)) + " ";
			else
				result += str + " ";
		}
		return parser.parse(result);
	}

	private boolean isInt(String expr) {
		try {
			Integer.parseInt(expr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
