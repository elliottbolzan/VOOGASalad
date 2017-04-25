package authoring.panel.creation.pickers;

import authoring.Workspace;
import authoring.panel.creation.EntityMaker;
import authoring.panel.creation.editors.ActionEditor;
import engine.Action;
import engine.GameObject;
import engine.game.EngineController;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;

/**
 * @author Elliott Bolzan
 *
 *         This class lets the user view, edit, delete, and select Actions he or
 *         she has created. It extends Picker, a superclass which provides its
 *         editing and deleting abilities.
 *
 */
public class ActionPicker extends Picker {

	private EntityMaker editor;
	private EngineController engine = new EngineController();
	private ListView<Action> list;

	/** 
	 * Creates an ActionPicker.
	 * @param workspace the workspace that pertains to this picker.
	 * @param editor the EntityMaker which created this ActionPicker.
	 */
	public ActionPicker(Workspace workspace, EntityMaker editor) {
		super(workspace, "ActionPickerTitle", editor);
		this.editor = editor;
		addTooltips(workspace.getPolyglot().get("AddAction"),workspace.getPolyglot().get("EditAction"),
				workspace.getPolyglot().get("DeleteAction"));
		update();
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#createContainer()
	 */
	@Override
	public void createContainer() {
		list = new ListView<Action>();
		Label placeholder = new Label();
		placeholder.textProperty().bind(getWorkspace().getPolyglot().get("EmptyActions"));
		list.setPlaceholder(placeholder);
		list.setEditable(false);
		list.prefHeightProperty().bind(heightProperty());
		list.setCellFactory(param -> new ListCell<Action>() {
			@Override
			protected void updateItem(Action action, boolean empty) {
				super.updateItem(action, empty);
				if (empty || action == null || action.getDisplayName() == null) {
					setText(null);
				} else {
					setText(action.getDisplayName());
				}
			}
		});
		setOnClick(list, null);
		setCenter(list);
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#createNew()
	 */
	@Override
	public void createNew() {
		if (editor.getSelectedEvent() != null && editor.getEntity() != null) {
			setCurrentlyEditing(null);
			showEditor();
		} else {
			editor.showMessage(getWorkspace().getPolyglot().get("NoEventSelected").get());
		}
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#add(java.lang.Object)
	 */
	@Override
	public <E> void add(E element) {
		if (getCurrentlyEditing() != null) {
			remove(getCurrentlyEditing());
		}
		Action action = (Action) element;
		action.setEntity(editor.getEntity());
		editor.getSelectedEvent().getActions().add(action);
		update();
		select(action);
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#remove(java.lang.Object)
	 */
	@Override
	public <E> void remove(E element) {
		editor.getSelectedEvent().getActions().remove((Action) element);
		update();
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#delete()
	 */
	@Override
	public void delete() {
		if (selectionExists(list.getSelectionModel().getSelectedItem())) {
			remove(list.getSelectionModel().getSelectedItem());
		}
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#edit()
	 */
	@Override
	public void edit() {
		if (selectionExists(list.getSelectionModel().getSelectedItem())) {
			setCurrentlyEditing(list.getSelectionModel().getSelectedItem());
			showEditor();
		}
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#update()
	 */
	@Override
	public void update() {
		if (editor.getSelectedEvent() != null)
			list.setItems(FXCollections.observableArrayList(editor.getSelectedEvent().getActions()));
		else
			list.setItems(FXCollections.emptyObservableList());
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#showEditor()
	 */
	@Override
	public void showEditor() {
		ActionEditor editor = new ActionEditor(getWorkspace(), this, (Action) getCurrentlyEditing(),
				engine.getAllActions());
		getWorkspace().getMaker().display("NewActionTitle", 300, 400, editor, Modality.APPLICATION_MODAL);
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#select(engine.GameObject)
	 */
	@Override
	public void select(GameObject object) {
		list.getSelectionModel().select((Action) object);
	}

}
