package authoring.panel.creation.pickers;

import authoring.Workspace;
import authoring.panel.creation.EntityMaker;
import authoring.panel.creation.editors.EventEditor;
import engine.Event;
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
 *         This class lets the user view, edit, delete, and select Events he or
 *         she has created. It extends Picker, a superclass which provides its
 *         editing and deleting abilities.
 *
 */
public class EventPicker extends Picker {

	private EntityMaker entityMaker;
	private EngineController engine = new EngineController();
	private ListView<Event> list;

	/**
	 * Creates an EventPicker.
	 * @param workspace the workspace to which this picker belongs.
	 * @param entityMaker the EntityMaker which created this Picker.
	 */
	public EventPicker(Workspace workspace, EntityMaker entityMaker) {
		super(workspace, "EventPickerTitle", entityMaker);
		this.entityMaker = entityMaker;
		update();
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#createContainer()
	 */
	@Override
	public void createContainer() {
		list = new ListView<Event>();
		list.getStyleClass().add("picker-list");
		Label placeholder = new Label();
		placeholder.textProperty().bind(getWorkspace().getPolyglot().get("EmptyEvents"));
		list.setPlaceholder(placeholder);
		list.setEditable(false);
		list.prefHeightProperty().bind(heightProperty());
		list.setCellFactory(param -> new ListCell<Event>() {
			@Override
			protected void updateItem(Event event, boolean empty) {
				super.updateItem(event, empty);
				if (empty || event == null || event.getDisplayName() == null) {
					setText(null);
				} else {
					setText(event.getDisplayName());
				}
			}
		});
		setOnClick(list, new Runnable() {
			@Override
			public void run() {
				setSelectedEvent();
			}
		});
		setCenter(list);
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#createNew()
	 */
	@Override
	public void createNew() {
		if (entityMaker.getEntity() != null) {
			setCurrentlyEditing(null);
			showEditor();
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
		Event event = (Event) element;
		event.setEntity(entityMaker.getEntity());
		entityMaker.getEntity().getEvents().add(event);
		update();
		select(event);
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#remove(java.lang.Object)
	 */
	@Override
	public <E> void remove(E element) {
		entityMaker.getEntity().getEvents().remove((Event) element);
		update();
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#delete()
	 */
	@Override
	public void delete() {
		if (selectionExists(list.getSelectionModel().getSelectedItem())) {
			remove(list.getSelectionModel().getSelectedItem());
			entityMaker.setSelectedEvent(null);
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
		list.setItems(FXCollections.observableArrayList(entityMaker.getEntity().getEvents()));
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#showEditor()
	 */
	@Override
	public void showEditor() {
		EventEditor editor = new EventEditor(getWorkspace(), this, (Event) getCurrentlyEditing(),
				engine.getAllEvents());
		getWorkspace().getMaker().display("NewEventTitle", 300, 400, editor, Modality.APPLICATION_MODAL);
	}

	/* (non-Javadoc)
	 * @see authoring.panel.creation.pickers.Picker#select(engine.GameObject)
	 */
	@Override
	public void select(GameObject object) {
		list.getSelectionModel().select((Event) object);
		setSelectedEvent();
	}

	private void setSelectedEvent() {
		entityMaker.setSelectedEvent(list.getSelectionModel().getSelectedItem());
	}

}
