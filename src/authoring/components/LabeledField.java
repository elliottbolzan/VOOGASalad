package authoring.components;

import authoring.Workspace;
import utils.views.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author Elliott Bolzan
 *
 *         This class serves to encapsulate a common need: the combination of a
 *         TextField and a label preceding it.
 */
public class LabeledField extends View {

	private TextField field;

	/**
	 * Creates a LabeledField.
	 * @param workspace the workspace that owns this field.
	 * @param labelProperty the property corresponding to the Label's text.
	 * @param text the text to be displayed in the TextField.
	 * @param editable whether the TextField should be editable or not.
	 */
	public LabeledField(Workspace workspace, String labelProperty, String text, boolean editable) {
		Label label = new Label();
		label.textProperty().bind(workspace.getPolyglot().get(labelProperty));
		label.setMinWidth(Region.USE_PREF_SIZE);
		label.setPadding(new Insets(5));
		field = new TextField(text);
		field.setEditable(editable);
		HBox box = new HBox(label, field);
		box.setAlignment(Pos.CENTER);
		box.setPadding(new Insets(2));
		HBox.setHgrow(field, Priority.ALWAYS);
		setCenter(box);
	}

	/**
	 * Set the field's text.
	 * @param text the content of the field.
	 */
	public void setText(String text) {
		field.setText(text);
	}

	/**
	 * @return the TextField's contents.
	 */
	public String getText() {
		return field.getText();
	}

}
