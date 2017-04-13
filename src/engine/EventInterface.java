package engine;

import java.util.List;

/**
 * @author nikita This is the interface which all events must implement.
 */
public interface EventInterface {
	// external API
	/**
	 * add an action which will be carried out when this event is triggered.
	 * 
	 * @param action
	 *            the action to be added
	 */
	void addAction(Action action);

	/**
	 * get all parameters necessary for this event to act
	 * 
	 * @return a list of parameter objects
	 */
	List<Parameter> getParams();

	/**
	 * set the parameters necessary for this event to act
	 * 
	 * @param params
	 *            the parameters to be set
	 */
	void setParams(List<Parameter> params);

	String getDisplayName();

	String getDisplayDescription();

	// internal API
	/**
	 * check if this event is being triggered
	 * 
	 * @return if the event is triggered or not
	 */
	boolean act();

	/**
	 * tell all actions held by this event to act
	 */
	void trigger();// tell all actions to act
}
