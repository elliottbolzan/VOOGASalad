package authoring.panel.creation;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.panel.creation.editors.EntityEditor;
import authoring.panel.creation.pickers.*;
import authoring.panel.display.EntityDisplay;
import authoring.views.ConcreteView;
import authoring.views.View;
import engine.Entity;
import engine.Event;
import engine.game.EngineController;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Elliott Bolzan
 *
 */
public class EntityMaker {
	
	private Workspace workspace;
	private EntityDisplay display;
	private Stage stage;
	private View view;
	private SplitPane pane;
	private EngineController engine;
	
	private EntityInfo entityInfo;
	private EntityEditor entityEditor;
	private EventPicker eventPicker;
	private ActionPicker actionPicker;
	
	private Event selectedEvent;

	/**
	 * 
	 */
	public EntityMaker(Workspace workspace, EntityDisplay display, Entity entity) {
		this.workspace = workspace;
		this.display = display;
		engine = new EngineController();
		if (entity == null) {
			entity = engine.getDefaultEntity();
		}
		setupView(entity);
		setupStage();
	}
	
	public Entity getEntity() {
		return entityEditor.getEntity();
	}
	
	private void setupView(Entity entity) {
		view = new ConcreteView(workspace.getResources().getString("EntityMakerTitle"));
		entityEditor = new EntityEditor(workspace, entity.clone(), engine.getAllEntities());
		entityInfo = new EntityInfo(workspace, this);
		eventPicker = new EventPicker(workspace, this);
		actionPicker = new ActionPicker(workspace, this);
		pane = new SplitPane(entityInfo, entityEditor, eventPicker, actionPicker);
		pane.setDividerPositions(0.25, 0.5, 0.75);
		view.setCenter(pane);
	}
	
	private void setupStage() {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(workspace.getResources().getString("EntityMakerTitle"));
		stage.setResizable(true);
		stage.setScene(createScene());
		stage.show();
		stage.centerOnScreen();
	}
	
	private Scene createScene() {
		Scene scene = new Scene(view, 800, 300);
		scene.getStylesheets().add(workspace.getResources().getString("StylesheetPath"));
		return scene;
	}
	
	public void dismiss() {
		stage.close();
	}
	
	public void setSelectedEvent(Event event) {
		selectedEvent = event;
		actionPicker.update();
	}
	
	public Event getSelectedEvent() {
		return selectedEvent;
	}
	
	public void showMessage(String message) {
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", message).show();
	}
	
	public void save() {
		if (entityInfo.getName().trim().equals("")) {
			showMessage(workspace.getResources().getString("EmptyName"));
			return;
		}
		getEntity().nameProperty().set(entityInfo.getName());
		getEntity().imagePathProperty().set(entityInfo.getImagePath());
		display.addEntity(getEntity());
		dismiss();
	}
	
}
