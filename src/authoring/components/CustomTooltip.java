package authoring.components;

import java.lang.reflect.Field;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class CustomTooltip extends Tooltip {
	
	public CustomTooltip(StringBinding string, Node node){
		this(string);
		Tooltip.install(node,this);
	}
	
	public CustomTooltip(StringBinding string){
		super();
		this.textProperty().bind(string);
		this.setMaxWidth(200);
		this.setWrapText(true);
		this.shortenToolTipTime();
	}
	
	//Resource used: http://stackoverflow.com/questions/26854301/control-javafx-tooltip-delay
	private void shortenToolTipTime() {
	    try {
	        Field fieldBehavior = this.getClass().getSuperclass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(this);

	        Field fieldTimer = objBehavior.getClass().getSuperclass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
	    } catch (Exception e) {
	    	System.out.println("not working");

	    }
	}


}
