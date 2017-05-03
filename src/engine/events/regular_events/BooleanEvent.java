package engine.events.regular_events;

import boolean_parser.BooleanParser;
import engine.Parameter;
import engine.events.Event;
import utils.math.IntChecker;

/**
 * Evaluate a expression of booleans to react to complicated combinations of
 * expressions. Uses the BooleanParser developed by Elliott and me
 * 
 * @author nikita
 */
public class BooleanEvent extends Event {
	private BooleanParser parser;
	private IntChecker checker;

	public BooleanEvent() {
		addParam(new Parameter(getResource("Expression"), String.class, ""));
		checker = new IntChecker();
	}

	@Override
	public boolean act() {
		if (parser == null)
			parser = new BooleanParser();
		String[] expression = ((String) getParam(getResource("Expression"))).split("\\s+");
		String result = "";
		for (String str : expression) {
			if (checker.check(str))
				result += String.valueOf(getEntity().getEventById(Integer.parseInt(str)).isTriggered(true)) + " ";
			else
				result += str + " ";
		}
		return parser.parse(result);
	}
}
