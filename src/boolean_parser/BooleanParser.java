package boolean_parser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author Elliott Bolzan
 *
 */
public class BooleanParser {

	public Boolean parse(String expression) throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        return (Boolean) engine.eval(expression);
	}

}
