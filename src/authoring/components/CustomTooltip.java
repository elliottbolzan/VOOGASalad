package authoring.components;

import java.lang.reflect.Field;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class CustomTooltip {
	
	public CustomTooltip(StringBinding string, Node node){
		Tooltip t = new Tooltip();
		t.textProperty().bind(string);
		shortenToolTipTime(t);
		Tooltip.install(node,t);
	}
	
	//Resource used: http://stackoverflow.com/questions/26854301/control-javafx-tooltip-delay
	private void shortenToolTipTime(Tooltip tooltip) {
	    try {
	        Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(tooltip);

	        Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
	    } catch (Exception e) {

	    }
	}


}
