package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import exceptions.GameObjectException;

/**
 * @author nikita This is the abstract class from which event, action and entity
 *         all inherit. It contains methods that are common to all of these
 *         types of game objects, such as getting names and descriptions to
 *         display to the user, getting and setting parameters, cloning and etc
 */
public abstract class GameObject {
	private transient ResourceBundle resources, notTranslatedResources;
	private List<Parameter> params;
	private Entity entity;
	private GameInfo info;

	public GameObject(String name) {
		setUpResources();
		params = new ArrayList<Parameter>();
	}

	public Object readResolve() {
		setUpResources();
		return this;
	}

	private void setUpResources() {
		resources = ResourceBundle.getBundle("resources/Strings");
		notTranslatedResources = ResourceBundle.getBundle("resources/IO");
	}

	public String getDisplayName() {
		return resources.getString(getClass().getSimpleName().toString());
	}

	public String getDisplayDescription() {
		return resources.getString(getClass().getSimpleName() + "Description");
	}

	public String getResource(String name) {
		return resources.getString(name);
	}

	public String getNotTranslatedResource(String name) {
		return notTranslatedResources.getString(name);
	}

	public List<Parameter> getParams() {
		return params;
		//return Collections.unmodifiableList(params);
	}

	public void addParam(Parameter param) {
		params.add(param);
	}

	public void updateParam(String name, Object value) {
		findParameter(name).setObject(value);
	}
	
	public boolean hasParam(String name) {
		for (Parameter param : params) {
			if (param.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public void setParams(List<Parameter> params) {
		this.params = params;
	}

	public Object getParam(String name) {
		return findParameter(name).getObject();
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	private Parameter findParameter(String name) {
		try {
			for (Parameter param : params) {
				if (param.getName().equals(name))
					return param;
			}
		} catch (Exception e) {
			throw new GameObjectException(notTranslatedResources.getString("NoParameter"));
		}
		return null;
	}

	public GameInfo getGameInfo() {
		return this.info;
	}

	public void setGameInfo(GameInfo info) {
		this.info = info;
	}

	private GameObject getInstance() {
		try {
			return getClass().getConstructor().newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * return a copy of this game object, copying all necessary instance
	 * variables.
	 * 
	 * @return copy of this game object.
	 */
	@Override
	public GameObject clone() {
		GameObject copy = getInstance();
		copy.setGameInfo(getGameInfo());
		List<Parameter> params = new ArrayList<Parameter>();
		params.addAll(getParams().stream().map(s -> {
			return new Parameter(s.getName(), s.getParameterClass(), s.getObject());
		}).collect(Collectors.toList()));
		copy.setParams(params);
		return copy;
	}
}
