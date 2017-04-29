package engine.entities.entities;

import java.util.HashMap;
import java.util.Map;

import engine.Parameter;
import engine.entities.Entity;
import engine.events.Event;
import engine.events.additional_events.FinishAchievementEvent;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/**
 * Entity for achievements. Properties of achievements are very similar to those
 * of entities, so achievements were made to be a type of entity. Events owned
 * by this achievement should include finish achievement event. Other events
 * shouldn't have any actions associated with them.
 * 
 * @author nikita
 */
public class AchievementEntity extends Entity {
	private Map<Event, DoubleBinding> percent;

	@Override
	protected void setupDefaultParameters() {
		addParam(new Parameter("Description", String.class, ""));
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		addAdditionalEventClass(FinishAchievementEvent.class);
		percent = new HashMap<>();
	}

	@Override
	protected void move() {
	}

	
	public Map<Event, DoubleBinding> createBindings(){
		for(Event event : getEvents()){
			if (!(event instanceof FinishAchievementEvent)) {
				System.out.println(event.getNumberTimesTriggered());
				
				SimpleIntegerProperty completed = new SimpleIntegerProperty();
				if(event.getNumberTimesTriggered().get() <= (int) event.getParam("How often to trigger")){
					completed.bind(event.getNumberTimesTriggered());	
					
					
				}
				double total = (int) event.getParam("How often to trigger");
				percent.put(event, completed.divide(total).multiply(100));
			}
		}
		return percent;
	}
	
}
