package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import engine.game.gameloop.Screen;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Abstract class for entities. Methods are implemented that are common to all
 * kinds of entites (character, block background, etc)
 * 
 * @author nikita
 */
public abstract class Entity extends GameObject implements EntityInterface, Cloneable {
	public static final double TIME_STEP = Screen.FRAME_TIME_MILLISECONDS / 50.0;
	public static final Integer YACCELERATION = 1;
	private SimpleDoubleProperty x, y, width, height, zIndex;
	private SimpleStringProperty name, imagePath;
	private SimpleBooleanProperty isVisible;
	private double xSpeed, ySpeed, xAcceleration, yAcceleration;
	private List<Event> events;

	public Entity() {
		super("Entity");
		this.setup();
		this.setName("Mario");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/mario.png").toExternalForm());
	}

	private void setup() {
		x = new SimpleDoubleProperty(0);
		y = new SimpleDoubleProperty(0);
		width = new SimpleDoubleProperty(0);
		height = new SimpleDoubleProperty(0);
		zIndex = new SimpleDoubleProperty(0);
		events = new ArrayList<Event>();
		this.name = new SimpleStringProperty();
		this.imagePath = new SimpleStringProperty();
		isVisible = new SimpleBooleanProperty(true);
	}

	/**
	 * Update the position of this entity according to its speed and
	 * acceleration. Then tell all events to check if they are triggered. is
	 * called once per step of the game loop. If events are triggered, their
	 * actions act.
	 */
	@Override
	public void update() {
		setX(getX() + getXSpeed() * TIME_STEP);
		setY(getY() + getYSpeed() * TIME_STEP);
		setXSpeed(getXSpeed() + getXAcceleration() * TIME_STEP);
		setYSpeed(getYSpeed() + getYAcceleration() * TIME_STEP);
		List<Event> eventsToTrigger = events.stream().filter(s -> s.act()).collect(Collectors.toList());
		eventsToTrigger.forEach(event -> event.trigger());
	}

	@Override
	public void addEvent(Event event) {
		this.events.add(event);
	}

	/**
	 * 
	 * @return Value of the Entity meant to represent the Entity's depth.
	 */
	public double getZ() {
		return this.zIndex.get();
	}

	@Override
	public double getX() {
		return this.x.get();
	}

	@Override
	public void setX(double x) {
		this.x.set(x);
	}

	/**
	 * 
	 * @param z
	 *            Set the Entity's depth on the screen with respect to other
	 *            Entity's.
	 */
	public void setZ(int z) {
		this.zIndex.set(z);
	}

	@Override
	public String getName() {
		return this.name.get();
	}

	/**
	 * 
	 * @param name
	 *            Set reference that refers to this specific Entity.
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public String getImagePath() {
		return this.imagePath.get();
	}

	/**
	 * 
	 * @param imagePath
	 *            Set the location in the file system of the image for this
	 *            Entity.
	 */
	public void setImagePath(String imagePath) {
		this.imagePath.set(imagePath);
	}

	/**
	 * 
	 * @return The x location of the Entity's left edge.
	 */
	public SimpleDoubleProperty xProperty() {
		return x;
	}

	/**
	 * 
	 * @return The y location of the Entity's top edge.
	 */
	public SimpleDoubleProperty yProperty() {
		return y;
	}

	/**
	 * 
	 * @return The height of the Entity.
	 */
	public SimpleDoubleProperty heightProperty() {
		return height;
	}

	/**
	 * 
	 * @return The width of the Entity.
	 */
	public SimpleDoubleProperty widthProperty() {
		return width;
	}

	/**
	 * 
	 * @return The name of this Entity.
	 */
	public SimpleStringProperty nameProperty() {
		return name;
	}

	/**
	 * 
	 * @return The location of this Entity in the file system.
	 */
	public SimpleStringProperty imagePathProperty() {
		return imagePath;
	}

	/**
	 * 
	 * @return True if this Entity is meant to be visible. False if this Entity
	 *         is meant to be hidden from view.
	 */
	public SimpleBooleanProperty isVisibleProperty() {
		return this.isVisible;
	}

	@Override
	public double getY() {
		return this.y.get();
	}

	@Override
	public void setY(double y) {
		this.y.set(y);
	}

	@Override
	public double getWidth() {
		return this.width.get();
	}

	@Override
	public void setWidth(double width) {
		this.width.set(width);
	}

	@Override
	public double getHeight() {
		return this.height.get();
	}

	@Override
	public void setHeight(double height) {
		this.height.set(height);
	}

	@Override
	public double getXSpeed() {
		return xSpeed;
	}

	@Override
	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	@Override
	public double getYSpeed() {
		return ySpeed;
	}

	@Override
	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	@Override
	public double getXAcceleration() {
		return xAcceleration;
	}

	@Override
	public void setXAcceleration(double xAcceleration) {
		this.xAcceleration = xAcceleration;
	}

	@Override
	public double getYAcceleration() {
		return yAcceleration;
	}

	@Override
	public void setYAcceleration(double yAcceleration) {
		this.yAcceleration = yAcceleration;
	}

	@Override
	public List<Event> getEvents() {
		return events;
		//return Collections.unmodifiableList(events);
	}

	/**
	 * 
	 * @param events
	 *            Sets the list of Events that are associated with this Entity.
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	/**
	 * 
	 * @param visible
	 *            Sets the visibility of this Entity. True means the Entity is
	 *            visible. False means the Entity is not visible.
	 */
	public void setIsVisible(boolean visible) {
		this.isVisible.set(visible);
	}

	/**
	 * 
	 * @return True if this Entity is meant to be visible. False if this Entity
	 *         is meant to be hidden from view.
	 */
	public boolean getIsVisible() {
		return this.isVisible.get();
	}

	private void set(Entity entity) {
		this.setImagePath(entity.getImagePath());
		this.setName(entity.getName());
		this.setHeight(entity.getHeight());
		this.setWidth(entity.getWidth());
		this.setX(entity.getX());
		this.setY(entity.getY());
	}

	/**
	 * obtain a copy of this entity. Overrides the clone method defined in
	 * GameObject. need to obtain a copy of all events, and all actions of those
	 * events
	 * 
	 * @return a copy of this entity
	 */
	@Override
	public Entity clone() {
		Entity copy = (Entity) super.clone();
		copy.set(this);
		copy.setEvents(events.stream().map(s -> {
			Event eventCopy = (Event) s.clone();
			eventCopy.setEntity(copy);
			eventCopy.setActions(s.getActions().stream().map(p -> {
				Action actionCopy = (Action) p.clone();
				actionCopy.setEntity(copy);
				return actionCopy;
			}).collect(Collectors.toList()));
			return eventCopy;
		}).collect(Collectors.toList()));
		return copy;
	}

}