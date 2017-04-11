package authoring.panel.creation.pickers;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.panel.creation.EntityMaker;
import authoring.panel.creation.editors.ActionEditor;
import engine.Action;
import engine.game.EngineController;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;

/**
 * @author Elliott Bolzan
 *
 */
public class ActionPicker extends Picker {

	private EntityMaker editor;
	private EngineController engine = new EngineController();
	private ListView<Action> list;
	private ComponentMaker maker;

	public ActionPicker(Workspace workspace, EntityMaker editor) {
		super(workspace, "ActionPickerTitle", editor);
		this.editor = editor;
		maker = new ComponentMaker(workspace.getResources());
		update();
	}

	@Override
	public void createContainer() {
		list = new ListView<Action>();
		list.setPlaceholder(new Label(getWorkspace().getResources().getString("EmptyActions")));
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
		setCenter(list);
	}

	@Override
	public void createNew() {
		if (editor.getSelectedEvent() != null && editor.getEntity() != null) {
			setCurrentlyEditing(null);
			showEditor();
		} else {
			editor.showMessage(getWorkspace().getResources().getString("NoEventSelected"));
		}
	}

	@Override
	public <E> void add(E element) {
		if (getCurrentlyEditing() != null) {
			remove(getCurrentlyEditing());
		}
		Action action = (Action) element;
		action.setEntity(editor.getEntity());
		editor.getSelectedEvent().getActions().add(action);
		update();
	}

	@Override
	public <E> void remove(E element) {
		editor.getSelectedEvent().getActions().remove((Action) element);
		update();
	}

	@Override
	public void delete() {
		if (selectionExists(list.getSelectionModel().getSelectedItem())) {
			remove(list.getSelectionModel().getSelectedItem());
		}
	}

	@Override
	public void edit() {
		if (selectionExists(list.getSelectionModel().getSelectedItem())) {
			setCurrentlyEditing(list.getSelectionModel().getSelectedItem());
			showEditor();
		}
	}

	@Override
	public void update() {
		if (editor.getSelectedEvent() != null)
			list.setItems(FXCollections.observableArrayList(editor.getSelectedEvent().getActions()));
		else
			list.setItems(FXCollections.emptyObservableList());
	}

	@Override
	public void showEditor() {
		ActionEditor editor = new ActionEditor(getWorkspace(), this, (Action) getCurrentlyEditing(),
				engine.getAllActions());
		maker.display("NewActionTitle", 300, 400, editor, Modality.APPLICATION_MODAL);
	}

}
