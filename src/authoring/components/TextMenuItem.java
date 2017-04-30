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
public class TextMenuItem extends MenuItem {
	
	private Label label;
	
	public TextMenuItem(EventHandler<ActionEvent> handler) {
		label = new Label();
		label.setMinWidth(100);
		setGraphic(label);
		setOnAction(handler);
	}

	public TextMenuItem(StringBinding binding, String keyCombination, EventHandler<ActionEvent> handler) {
		this(handler);
		setBinding(binding);
	    setAccelerator(KeyCombination.keyCombination(keyCombination));
	}
	
	public TextMenuItem(StringBinding binding, EventHandler<ActionEvent> handler) {
		this(handler);
		setBinding(binding);
	}
	
	private void setBinding(StringBinding binding) {
		label.textProperty().bind(binding);
	}

}
