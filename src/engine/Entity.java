package engine;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

public abstract class Entity extends GameObject implements EntityInterface, Cloneable {

	public static final Integer ACCELERATION = 1;
	private SimpleDoubleProperty x, y, width, height, zIndex;
	private SimpleStringProperty name, imagePath;
	private double xSpeed, ySpeed, xAcceleration, yAcceleration;
	private List<Event> events;

	public Entity() {
		super("Entity");
		try {
			setup("Mario", new File("src/resources/images/mario.png").toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void setup(String name, String imagePath) {
		System.out.println("Constructor: " + this);
		x = new SimpleDoubleProperty();
		y = new SimpleDoubleProperty();
		width = new SimpleDoubleProperty();
		height = new SimpleDoubleProperty();
		zIndex = new SimpleDoubleProperty();
		events = new ArrayList<Event>();
		this.name = new SimpleStringProperty(name);
		this.imagePath = new SimpleStringProperty(imagePath);
		addParam(new Parameter("Time Step", Double.class, 0.5));
	}

	@Override
	public void update() {
		List<Event> eventsToTrigger = events.stream().filter(s -> s.act()).collect(Collectors.toList());
		eventsToTrigger.forEach(event -> event.trigger());
	}

	@Override
	public void addEvent(Event event) {
		this.events.add(event);
	}

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

	public void setZ(int z) {
		this.zIndex.set(z);
	}

	@Override
	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public String getImagePath() {
		return this.imagePath.get();
	}

	public void setImagePath(String imagePath) {
		this.imagePath.set(imagePath);
	}

	public SimpleDoubleProperty xProperty() {
		return x;
	}

	public SimpleDoubleProperty yProperty() {
		return y;
	}

	public SimpleDoubleProperty heightProperty() {
		return height;
	}

	public SimpleDoubleProperty widthProperty() {
		return width;
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public SimpleStringProperty imagePathProperty() {
		return imagePath;
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
		;
	}

	public double getXSpeed() {
		return xSpeed;
	}

	@Override
	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public double getYSpeed() {
		return ySpeed;
	}

	@Override
	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	public double getXAcceleration() {
		return xAcceleration;
	}

	@Override
	public void setXAcceleration(double xAcceleration) {
		this.xAcceleration = xAcceleration;
	}

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
	}

	public void setEvents(List<Event> events) {
		/*(this.events.clear();
		for (Event event : events) {
			this.addEvent(event);
		}*/
		this.events = events;
	}

	public void set(Entity entity) {
		this.setImagePath(entity.getImagePath());
		this.setName(entity.getName());
		this.setEvents(entity.getEvents());
		this.setHeight(entity.getHeight());
		this.setWidth(entity.getWidth());
		this.setX(entity.getX());
		this.setY(entity.getY());
	}

	@Override
	public Entity clone() {
		try {
			Entity returnedEntity = getClass().getDeclaredConstructor().newInstance();
			returnedEntity.set(this);
			return returnedEntity;
		} catch (Exception e) {
			Alert alert = new Alert(null, "Couldn't clone Entity", null);
			alert.show();
		}
		return this;
	}

}