package authoring.panel.display;

import authoring.Workspace;
import authoring.components.EditableContainer;
import authoring.components.thumbnail.LiveThumbnail;
import authoring.components.thumbnail.Thumbnail;
import authoring.panel.creation.EntityMaker;
import engine.Entity;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * @author Elliott Bolzan
 *
 */
public class EntityDisplay extends EditableContainer {

	private ListView<Entity> list;

	/**
	 * 
	 */
	public EntityDisplay(Workspace workspace) {
		super(workspace, workspace.getResources().getString("EntityDisplayTitle"));
	}

	public ListView<Entity> getList() {
		return list;
	}

	public void addEntity(Entity entity) {
		if (getCurrentlyEditing() != null) {
			getWorkspace().getDefaults().remove((Entity) getCurrentlyEditing());
			getWorkspace().updateEntity(entity);
		}
		getWorkspace().getDefaults().add(entity);
	}
	
	@Override
	public void createNew() {
		setCurrentlyEditing(null);
		new EntityMaker(getWorkspace(), this, null);
	}

	/*public void updateEntity(Entity entity) {
		for (Entity currEntity : table.getItems()) {
			// won't work, probably? same names two defaults, for example. are we allowing that?
			// also: if you have a block named Mario, and you press a Mario, the block just becomes Mario.
			// problems like that.
			if (currEntity.getName().equals(entity.getName())) {
				currEntity.set(entity);
			}
		}
	}*/

	@Override
	public void edit() {
		if (selectionExists(getSelection())){
			setCurrentlyEditing(getSelection());
			new EntityMaker(getWorkspace(), this, getSelection());
		}
	}

	@Override
	public void delete() {
		if (selectionExists(getSelection()))
			getWorkspace().getDefaults().remove(getSelection());
	}

	@Override
	public void createContainer() {
		list = new ListView<Entity>();
		list.setPlaceholder(new Label(getWorkspace().getResources().getString("EmptyEntities")));
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