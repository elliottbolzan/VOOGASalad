package authoring.components;

import authoring.Workspace;
import utils.views.View;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import polyglot.Case;

/**
 * @author Elliott Bolzan
 *
 *         A superclass for all Views that: provide a list-style container
 *         (ListView or TableView), require editing functionality, deletion
 *         functionality, and addition functionality. Because this requirement
 *         was so common across my part of the project, I chose to make a
 *         superclass to prevent duplicated code and increase flexibility and
 *         extensibility.
 */
public abstract class EditableContainer extends View {

	private Workspace workspace;
	private Object currentlyEditing;

	/**
	 * Create an EditableContainer.
	 * 
	 * @param workspace
	 *            the workspace that owns the container.
	 * @param title
	 *            the title of the EditableContainer.
	 */
	public EditableContainer(Workspace workspace, String titleProperty) {
		super(workspace.getPolyglot().get(titleProperty, Case.TITLE));
		this.workspace = workspace;
		setup();
	}

	private void setup() {
		createContainer();
		createButtons();
	}

	protected Workspace getWorkspace() {
		return workspace;
	}

	private void createButtons() {
		VBox buttonBox = new VBox();
		Button newButton = workspace.getMaker().makeButton("New", e -> createNew(), true);
		Tooltip t = new Tooltip();
		t.textProperty().bind(workspace.getPolyglot().get("AddEntity"));
		newButton.setTooltip(t);
		Button editButton = workspace.getMaker().makeButton("Edit", e -> edit(), true);
		Tooltip p = new Tooltip();
		p.textProperty().bind(workspace.getPolyglot().get("EditEntity"));
		editButton.setTooltip(p);
		Button deleteButton = workspace.getMaker().makeButton("Delete", e -> delete(), true);
		Tooltip q = new Tooltip();
		q.textProperty().bind(workspace.getPolyglot().get("DeleteEntity"));
		deleteButton.setTooltip(q);
		HBox modificationButtons = new HBox(editButton, deleteButton);
		buttonBox.getChildren().addAll(newButton, modificationButtons);
		setBottom(buttonBox);
	}

	/**
	 * Determines whether a selection exists. If one does not, an error message
	 * is displayed.
	 * 
	 * @param object
	 *            the object that is currently selected, or null.
	 * @return whether a selection currently exists.
	 */
	public boolean selectionExists(Object object) {
		if (object == null) {
			Alert alert = workspace.getMaker().makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader",
					workspace.getPolyglot().get("PickSomething"));
			alert.show();
		}
		return object != null;
	}

	/**
	 * Set the Object that is currently being edited.
	 * 
	 * @param currentlyEditing
	 *            the object that is currently being edited.
	 */
	public void setCurrentlyEditing(Object currentlyEditing) {
		this.currentlyEditing = currentlyEditing;
	}

	/**
	 * @return the Objec that is currently being edited.
	 */
	public Object getCurrentlyEditing() {
		return currentlyEditing;
	}

	/**
	 * Set the calling ListView to edit on double-click. Optionally, allow the
	 * caller to provide a Runnable, to be run on single click.
	 * 
	 * @param list
	 *            the list that is being clicked.
	 * @param single
	 *            the Runnable that is called on single-click. Can be null.
	 */
	public <E> void setOnClick(ListView<E> list, Runnable single) {
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1) {
					if (single != null) {
						single.run();
					}
				} else if (mouseEvent.getClickCount() == 2) {
					edit();
				}
			}
		});
	}

	/**
	 * Create the container for the data to be displayed. Most often, this is a
	 * ListView, but certain implementations use TableViews.
	 */
	public abstract void createContainer();

	/**
	 * Create a new element in the container.
	 */
	public abstract void createNew();

	/**
	 * Edit an element in the container.
	 */
	public abstract void edit();

	/**
	 * Delete an element in the container.
	 */
	public abstract void delete();

}
