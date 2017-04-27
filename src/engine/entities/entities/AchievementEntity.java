package engine.entities.entities;

import engine.Parameter;
import engine.entities.Entity;
import engine.events.additional_events.FinishAchievementEvent;
import javafx.beans.property.*;

/**
 * Entity for achievements. Properties of achievements are very similar to those
 * of entities, so achievements were made to be a type of entity. Events owned
 * by this achievement should include finish achievement event. Other events
 * shouldn't have any actions associated with them.
 * 
 * @author nikita
 */
public class AchievementEntity extends Entity {
	private SimpleDoubleProperty completed;
	private SimpleDoubleProperty total;

	@Override
	protected void setupDefaultParameters() {
		addParam(new Parameter("Description", String.class, ""));
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		addAdditionalEventClass(FinishAchievementEvent.class);
		completed = new SimpleDoubleProperty();
		total = new SimpleDoubleProperty();
	}

	@Override
	protected void move() {
	}

	public DoubleProperty getCompleted() {
		return completed;
	}

	public void setCompleted(double value) {
		completed.set(value);
	}

	public DoubleProperty getTotal() {
		return total;
	}

	public void setTotal(double value) {
		total.set(value);
	}

}
