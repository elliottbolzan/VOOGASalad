package authoring.panel.display;

import authoring.Workspace;
import authoring.components.EditableContainer;
import authoring.components.thumbnail.LiveThumbnail;
import authoring.components.thumbnail.Thumbnail;
import authoring.panel.creation.EntityMaker;
import engine.entities.Entity;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * @author Elliott Bolzan
 *
 *         A class that displays the user's default Entities. Allows the user to
 *         edit and delete them, or add new ones. Also allows the user to drag
 *         and drop Entities to the Canvas.
 */
public class EntityDisplay extends EditableContainer {

	private ListView<Entity> list;

	/**
	 * Creates an EntityDisplay.
	 * 
	 * @param workspace
	 *            the workspace that owns the EntityDisplay.
	 */
	public EntityDisplay(Workspace workspace) {
		super(workspace, "EntityDisplayTitle");
		getStyleClass().add("bordered");
		addTooltips(workspace.getPolyglot().get("AddEntity"), workspace.getPolyglot().get("EditEntity"),
				workspace.getPolyglot().get("DeleteEntity"));
	}

	/**
	 * @return the EntityDisplay's ListView.
	 */
	public ListView<Entity> getList() {
		return list;
	}

	/**
	 * Add an Entity to the EntityDisplay.
	 * 
	 * @param entity
	 *            the Entity to be added.
	 */
	public void addEntity(Entity entity) {
		if (getCurrentlyEditing() != null) {
			getWorkspace().getDefaults().remove((Entity) getCurrentlyEditing());
			getWorkspace().updateEntity(entity);
		}
		getWorkspace().getDefaults().add(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see authoring.components.EditableContainer#createNew()
	 */
	@Override
	public void createNew() {
		setCurrentlyEditing(null);
		new EntityMaker(getWorkspace(), this, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see authoring.components.EditableContainer#edit()
	 */
	@Override
	public void edit() {
		if (selectionExists(getSelection())) {
			editHelper(getSelection());
		}
	}

	public void editHelper(Entity entity) {
		setCurrentlyEditing(entity);
		new EntityMaker(getWorkspace(), this, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see authoring.components.EditableContainer#delete()
	 */
	@Override
	public void delete() {
		if (selectionExists(getSelection()))
			getWorkspace().getDefaults().remove(getSelection());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see authoring.components.EditableContainer#createContainer()
	 */
	@Override
	public void createContainer() {
		list = new ListView<Entity>();
		Label placeholder = new Label();
		placeholder.textProperty().bind(getWorkspace().getPolyglot().get("EmptyEntities"));
		list.setPlaceholder(placeholder);
		list.setEditable(false);
		list.prefHeightProperty().bind(heightProperty());
		list.setCellFactory(param -> new ListCell<Entity>() {
			@Override
			protected void updateItem(Entity entity, boolean empty) {
				super.updateItem(entity, empty);
				if (entity == null) {
					setGraphic(null);
					return;
				}
				setGraphic(createCellContent(entity));
			}
		});
		setOnClick(list, new Runnable() {
			@Override
			public void run() {
				getWorkspace().getDefaults().setSelectedEntity(getSelection());
			}
		});
		list.setItems(getWorkspace().getDefaults().getEntities());
		setCenter(list);
	}

	private Entity getSelection() {
		return list.getSelectionModel().getSelectedItem();
	}

	private VBox createCellContent(Entity entity) {
		VBox box = new VBox(8);
		box.setPadding(new Insets(8));
		box.setAlignment(Pos.CENTER);
		Label name = new Label();
		name.textProperty().bind(entity.nameProperty());
		Thumbnail thumbnail = new LiveThumbnail(entity.imagePathProperty(), 50, 50);
		box.getChildren().addAll(thumbnail, name);
		return box;
	}

}