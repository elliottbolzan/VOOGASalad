package engine.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Event;
import engine.Parameter;
import engine.events.additional_events.FinishAchievementEvent;
import javafx.beans.property.*;

public class AchievementEntity extends Entity{
	private DoubleProperty completed;
	private DoubleProperty total;

	@Override
	protected void setupDefaultParameters() {
		addParam(new Parameter("Description", String.class, ""));
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		addAdditionalEventClass(FinishAchievementEvent.class);
		completed = new SimpleDoubleProperty();
		total = new SimpleDoubleProperty();
	}
	
	public DoubleProperty getCompleted(){	
		List<IntegerProperty> completedAll = new ArrayList<>();
		for(Event event : this.getEvents()){
			completedAll.add(event.getNumberTimesTriggered());
		}
		//completed.bind(comple);
		return completed;
	}
	
	public void setCompleted(double value){
		completed.set(value);
	}
	
	public DoubleProperty getTotal(){
		int count = 0;
		for(Event event : this.getEvents()){
			count += Integer.parseInt(event.getParam("How often to trigger").toString());
		}
		total.set(count);
		return total;
	}
	
	public void setTotal(double value){
		total.set(value);
	}

}
