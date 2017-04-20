package engine;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author nikita Abstract class for entities. Methods are implemented that are
 *         common to all kinds of entites (character, block background, etc)
 */
public abstract class Entity extends GameObject implements EntityInterface, Cloneable
{

	public static final Integer YACCELERATION = 1;
	private SimpleDoubleProperty x, y, width, height, zIndex;
	private SimpleStringProperty name, imagePath;
	private SimpleBooleanProperty isVisible;
	private double xSpeed, ySpeed, xAcceleration, yAcceleration;
	private List<Event> events;

	public Entity()
	{
		super("Entity");
		try {
			setup("Mario", new File("src/resources/images/mario.png").toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			//TODO
		}
	}

	private void setup(String name, String imagePath)
	{
		x = new SimpleDoubleProperty();
		y = new SimpleDoubleProperty();
		width = new SimpleDoubleProperty();
		height = new SimpleDoubleProperty();
		zIndex = new SimpleDoubleProperty();
		events = new ArrayList<Event>();
		this.name = new SimpleStringProperty(name);
		this.imagePath = new SimpleStringProperty(imagePath);
		isVisible = new SimpleBooleanProperty(true);
	}

	/**
	 * tell all events to check if they are triggered. is called once per step
	 * of the game loop. If events are triggered, their actions act.
	 */
	@Override
	public void update()
	{
		List<Event> eventsToTrigger = events.stream().filter(s -> s.act()).collect(Collectors.toList());
		eventsToTrigger.forEach(event -> event.trigger());
	}

	@Override
	public void addEvent(Event event)
	{
		this.events.add(event);
	}

	public double getZ()
	{
		return this.zIndex.get();
	}

	@Override
	public double getX()
	{
		return this.x.get();
	}

	@Override
	public void setX(double x)
	{
		this.x.set(x);
	}

	public void setZ(int z)
	{
		this.zIndex.set(z);
	}

	@Override
	public String getName()
	{
		return this.name.get();
	}

	public void setName(String name)
	{
		this.name.set(name);
	}

	@Override
	public String getImagePath()
	{
		return this.imagePath.get();
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath.set(imagePath);
	}

	public SimpleDoubleProperty xProperty()
	{
		return x;
	}

	public SimpleDoubleProperty yProperty()
	{
		return y;
	}

	public SimpleDoubleProperty heightProperty()
	{
		return height;
	}

	public SimpleDoubleProperty widthProperty()
	{
		return width;
	}

	public SimpleStringProperty nameProperty()
	{
		return name;
	}

	public SimpleStringProperty imagePathProperty()
	{
		return imagePath;
	}

	public SimpleBooleanProperty isVisibleProperty()
	{
		return this.isVisible;
	}

	@Override
	public double getY()
	{
		return this.y.get();
	}

	@Override
	public void setY(double y)
	{
		this.y.set(y);
	}

	@Override
	public double getWidth()
	{
		return this.width.get();
	}

	@Override
	public void setWidth(double width)
	{
		this.width.set(width);
	}

	@Override
	public double getHeight()
	{
		return this.height.get();
	}

	@Override
	public void setHeight(double height)
	{
		this.height.set(height);
		;
	}

	@Override
	public double getXSpeed()
	{
		return xSpeed;
	}

	@Override
	public void setXSpeed(double xSpeed)
	{
		this.xSpeed = xSpeed;
	}

	@Override
	public double getYSpeed()
	{
		return ySpeed;
	}

	@Override
	public void setYSpeed(double ySpeed)
	{
		this.ySpeed = ySpeed;
	}

	@Override
	public double getXAcceleration()
	{
		return xAcceleration;
	}

	@Override
	public void setXAcceleration(double xAcceleration)
	{
		this.xAcceleration = xAcceleration;
	}

	@Override
	public double getYAcceleration()
	{
		return yAcceleration;
	}

	@Override
	public void setYAcceleration(double yAcceleration)
	{
		this.yAcceleration = yAcceleration;
	}

	@Override
	public List<Event> getEvents()
	{
		return events;
	}

	public void setEvents(List<Event> events)
	{
		this.events = events;
	}

	public void setIsVisible(boolean visiblbe)
	{
		this.isVisible.set(visiblbe);
	}

	public boolean getIsVisible()
	{
		return this.isVisible.get();
	}

	private void set(Entity entity)
	{
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
	public Entity clone()
	{
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