package authoring.components;

import authoring.Workspace;
import utils.views.View;
import javafx.beans.binding.StringBinding;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
	private Button newButton, editButton, deleteButton;

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
		HBox spacing = new HBox();
		spacing.maxWidth(Double.MAX_VALUE);
		HBox.setHgrow(spacing, Priority.ALWAYS);
		Button qButton = new Button("?");
		ToolBar toolBar = new ToolBar(spacing,qButton);
		setTop(toolBar);
		VBox buttonBox = new VBox();
		newButton = workspace.getMaker().makeButton("New", e -> createNew(), true);
		editButton = workspace.getMaker().makeButton("Edit", e -> edit(), true);
		deleteButton = workspace.getMaker().makeButton("Delete", e -> delete(), true);
		HBox modificationButtons = new HBox(editButton, deleteButton);
		buttonBox.getChildren().addAll(newButton, modificationButtons);
		setBottom(buttonBox);
	}
	
	/**
	 * Add tooltips to display text when the mouse hovers over the buttons. 
	 * The text will vary based on the context the editable container is used in. 
	 * @param s1
	 * @param s2
	 * @param s3
	 */
	public void addTooltips(StringBinding s1,StringBinding s2,StringBinding s3){
		new CustomTooltip(s1,newButton);
		new CustomTooltip(s2,editButton);
		new CustomTooltip(s3,deleteButton);
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
