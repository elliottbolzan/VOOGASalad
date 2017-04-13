package engine;

import java.util.List;

/**
 * @author nikita Interface for all actions.
 */
public interface ActionInterface {
	// external API
	/**
	 * get all parameters necessary for this action to act
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
	 * carry out the action. is called when triggered by an event
	 */
	void act();
}
