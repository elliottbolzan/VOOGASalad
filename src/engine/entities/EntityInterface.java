package engine.entities;

import java.util.List;

import engine.events.Event;

/**
 * @author nikita jimmy Interface for entities. Has methods for changing
 *         physical properties and appearance of the entities in the game.
 */
public interface EntityInterface {

	/**
	 * Add an event which this entity will react to in some way
	 * 
	 * @param event
	 *            the event to be added
	 */
	void addEvent(Event event);

	/**
	 * tell all events to check if they are triggered. is called once per step
	 * of the game loop. If events are triggered, their actions act.
	 */
	void update();

	/**
	 * get all events this entity reacts to.
	 * 
	 * @return a list of all events this entity reacts to
	 */
	List<Event> getEvents();

	void setYAcceleration(double accel);

	void setXAcceleration(double accel);

	void setYSpeed(double speed);

	void setXSpeed(double speed);

	void setY(double y);

	void setX(double x);

	void setHeight(double height);

	void setWidth(double width);

	String getName();

	String getImagePath();

	double getWidth();

	double getHeight();

	double getX();

	double getY();
	
	double getXSpeed();
	
	double getYSpeed();
	
	double getXAcceleration();
	
	double getYAcceleration();
}