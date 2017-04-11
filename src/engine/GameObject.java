package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import exceptions.GameObjectException;

public abstract class GameObject {
	private transient ResourceBundle resources;
	private List<Parameter> params;
	private Entity entity;
	private transient ResourceBundle gameObjectExceptions = ResourceBundle.getBundle("resources/GameObjectExceptions");
	private GameInfo info;

	public GameObject(String name) {
		resources = ResourceBundle.getBundle("resources/" + name);
		params = new ArrayList<Parameter>();
	}

	public String getDisplayName() {
		return resources.getString(getClass().getSimpleName().toString());
	}

	public String getDisplayDescription() {
		return resources.getString(getClass().getSimpleName() + "Description");
	}

	public List<Parameter> getParams() {
		return params;
	}

	public void addParam(Parameter param) {
		params.add(param);
	}

	public void updateParam(String name, Object value) {
		findParameter(name).setObject(value);
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
			throw new GameObjectException(gameObjectExceptions.getString("NoParameter"));
		}
		return null;
	}
	
	public GameInfo getGameInfo(){
		return this.info;
	}
	public void setGameInfo(GameInfo info){
		this.info = info;
	}
	
	public GameObject getInstance(){
		try {
			return getClass().getConstructor().newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public GameObject clone(){
		GameObject copy = getInstance();
		copy.setGameInfo(getGameInfo());
		List<Parameter> params = new ArrayList<Parameter>();
		params.addAll(getParams());
		copy.setParams(params);
		return copy;
	}
}
