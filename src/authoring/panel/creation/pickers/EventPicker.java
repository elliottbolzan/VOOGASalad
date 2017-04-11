package authoring.panel.creation.pickers;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.panel.creation.EntityMaker;
import authoring.panel.creation.editors.EventEditor;
import engine.Event;
import engine.game.EngineController;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

/**
 * @author Elliott Bolzan
 *
 */
public class EventPicker extends Picker {

	private EntityMaker entityMaker;
	private EngineController engine = new EngineController();
	private ListView<Event> list;
	private ComponentMaker componentMaker;

	public EventPicker(Workspace workspace, EntityMaker entityMaker) {
		super(workspace, "EventPickerTitle", entityMaker);
		this.entityMaker = entityMaker;
		componentMaker = new ComponentMaker(workspace.getResources());
		update();
	}

	@Override
	public void createContainer() {
		list = new ListView<Event>();
		list.setPlaceholder(new Label(getWorkspace().getResources().getString("EmptyEvents")));
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
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				entityMaker.setSelectedEvent(list.getSelectionModel().getSelectedItem());
			}
		});
		setCenter(list);
	}

	@Override
	public void createNew() {
		if (entityMaker.getEntity() != null) {
			setCurrentlyEditing(null);
			showEditor();
		}
	}

	@Override
	public <E> void add(E element) {
		if (getCurrentlyEditing() != null) {
			remove(getCurrentlyEditing());
		}
		Event event = (Event) element;
		event.setEntity(entityMaker.getEntity());
		System.out.println(entityMaker.getEntity());
		entityMaker.getEntity().getEvents().add(event);
		update();
	}

	@Override
	public <E> void remove(E element) {
		entityMaker.getEntity().getEvents().remove((Event) element);
		update();
	}

	@Override
	public void delete() {
		if (selectionExists(list.getSelectionModel().getSelectedItem())) {
			remove(list.getSelectionModel().getSelectedItem());
			entityMaker.setSelectedEvent(null);
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
		list.setItems(FXCollections.observableArrayList(entityMaker.getEntity().getEvents()));
	}

	@Override
	public void showEditor() {
		EventEditor editor = new EventEditor(getWorkspace(), this, (Event) getCurrentlyEditing(),
				engine.getAllEvents());
		componentMaker.display("NewEventTitle", 300, 400, editor, Modality.APPLICATION_MODAL);
	}

}
