package boolean_parser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * Boolean parsing adapted from:
 * http://stackoverflow.com/questions/19383953/is-it-possible-to-evaluate-a-boolean-expression-for-string-comparions.
 * 
 * Uses the default JavaScript engine to parse a boolean expression. In the
 * event that the expression is improperly formatted, the parser returns false.
 * This is by design: no Event will be triggered if the expression is incorrect,
 * which is the expected behavior.
 * 
 * @author Elliott Bolzan
 *
 */
public class BooleanParser {
	ScriptEngine engine;

	public BooleanParser() {
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("JavaScript");
	}

	public Boolean parse(String expression) {
		try {
			return (Boolean) engine.eval(expression);
		} catch (ScriptException e) {
			return false;
		}
	}

}
