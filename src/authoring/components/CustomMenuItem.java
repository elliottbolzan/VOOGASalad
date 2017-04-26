/**
 * 
 */
package authoring.components;

import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * @author Elliott Bolzan
 *
 */
public class CustomMenuItem extends MenuItem {
	
	private Label label;
	
	public CustomMenuItem(EventHandler<ActionEvent> handler) {
		label = new Label();
		label.setMinWidth(100);
		setGraphic(label);
		setOnAction(handler);
	}

	public CustomMenuItem(StringBinding binding, String keyCombination, EventHandler<ActionEvent> handler) {
		this(handler);
		setBinding(binding);
	    setAccelerator(KeyCombination.keyCombination(keyCombination));
	}
	
	public CustomMenuItem(StringBinding binding, EventHandler<ActionEvent> handler) {
		this(handler);
		setBinding(binding);
	}
	
	public CustomMenuItem(String text, EventHandler<ActionEvent> handler) {
		this(handler);
		label.setText(text);
	}
	
	private void setBinding(StringBinding binding) {
		label.textProperty().bind(binding);
	}

}
