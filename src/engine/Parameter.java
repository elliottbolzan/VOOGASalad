package engine;

/**
 * This class is used by entities, events, and actions to store the parameters
 * vital to their function. An instance of this class represents a variable.
 * Instances of this class are passed to the authoring environment, filled out
 * by the user when creating game objects, and returned to the engine.
 * 
 * @author nikita
 */
public class Parameter {
	private String name;
	private Class<?> clazz;
	private Object obj;

	public Parameter(String name, Class<?> clazz, Object def) {
		this.name = name;
		this.clazz = clazz;
		this.obj = def;
	}

	public Class<?> getParameterClass() {
		return this.clazz;
	}

	public String getName() {
		return name;
	}

	public Object getObject() {
		return obj;
	}

	public void setObject(Object value) {
		this.obj = value;
	}
}
