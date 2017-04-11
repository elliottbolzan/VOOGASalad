package authoring.panel.display;

import authoring.Workspace;
import authoring.components.EditableContainer;
import authoring.components.thumbnail.LiveThumbnail;
import authoring.components.thumbnail.Thumbnail;
import authoring.panel.creation.EntityMaker;
import engine.Entity;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * @author Elliott Bolzan
 *
 */
public class EntityDisplay extends EditableContainer {

	private TableView<Entity> table;

	/**
	 * 
	 */
	public EntityDisplay(Workspace workspace) {
		super(workspace, workspace.getResources().getString("EntityDisplayTitle"));
	}

	/**
	 * @return a TableView.
	 */
	private TableView<Entity> makeTable() {
		TableView<Entity> table = new TableView<Entity>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPlaceholder(new Label(getWorkspace().getResources().getString("EmptyEntities")));
		table.prefHeightProperty().bind(heightProperty());
		table.setId("entity-table");
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getWorkspace().getDefaults().setSelectedEntity(table.getSelectionModel().getSelectedItem());
			}
		});
		return table;
	}

	private TableColumn<Entity, Entity> makeEntityColumn() {
		TableColumn<Entity, Entity> entityColumn = new TableColumn<>("Entity");
		entityColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		entityColumn.setCellFactory(param -> new TableCell<Entity, Entity>() {
			@Override
			protected void updateItem(Entity entity, boolean empty) {
				super.updateItem(entity, empty);
				if (entity == null) {
					setGraphic(null);
					return;
				}
				VBox box = new VBox(8);
				box.setPadding(new Insets(8));
				box.setAlignment(Pos.CENTER);
				Label name = new Label();
				name.textProperty().bind(entity.nameProperty());
				Thumbnail thumbnail = new LiveThumbnail(entity.imagePathProperty(), 50, 50);
				box.getChildren().addAll(thumbnail, name);
				setGraphic(box);
			}
		});
		return entityColumn;
	}

	public void addEntity(Entity entity) {
		if (getCurrentlyEditing() != null) {
			getWorkspace().getDefaults().remove((Entity) getCurrentlyEditing());
		}
		getWorkspace().getDefaults().add(entity);
	}

	@Override
	public void createContainer() {
		table = makeTable();
		table.setItems(getWorkspace().getDefaults().getEntities());
		table.getColumns().add(makeEntityColumn());
		setCenter(table);
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
		Entity selection = table.getSelectionModel().getSelectedItem();
		System.out.println(selection);
		if (selectionExists(selection)){
			setCurrentlyEditing(selection);
			new EntityMaker(getWorkspace(), this, selection);
		}
	}

	@Override
	public void delete() {
		if (selectionExists(table.getSelectionModel().getSelectedItem()))
			getWorkspace().getDefaults().remove(table.getSelectionModel().getSelectedItem());
	}

}
