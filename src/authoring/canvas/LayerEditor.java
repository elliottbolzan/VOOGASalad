package authoring.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.views.View;
import engine.Entity;
import engine.game.Level;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * LayerEditor keeps track of a Canvas and all of the layers on the canvas. Each
 * level can be represented by its own LayerEditor, and thus a Level can be
 * created from the entities on a LayerEditor.
 * 
 * @author jimmy Modified by Mina Mungekar
 *
 */
public class LayerEditor extends View
{
	private Workspace workspace;
	private Canvas canvas;
	private Map<Integer, Layer> layers;
	private int layerCount;
	private int currLayer;
	private ComponentMaker maker;

	/**
	 * Make a new LayerEditor.
	 * 
	 * @param workspace
	 *            Workspace that the LayerEditor will be in.
	 */
	public LayerEditor(Workspace workspace)
	{
		super("");
		this.workspace = workspace;
		maker = new ComponentMaker(workspace.getResources());
		setup();
	}

	/**
	 * Convert all of the Layers/entities currently in the LayerEditor to a
	 * Level object.
	 * 
	 * @return Level object represented by this LayerEditor.
	 */
	public Level getLevel()
	{
		Level thisLevel = new Level();
		for (Layer layer : layers.values()) {
			for (EntityView entity : layer.getEntities()) {
				thisLevel.addEntity(entity.getEntity());
			}
		}
		return thisLevel;
	}

	/**
	 * Update the Entities in each layer to reflect the default Entity they
	 * correspond to. This method is called when a default Entity is edited by
	 * the user.
	 * 
	 * @param entity
	 *            the Entity to be edited.
	 */
	public void updateEntity(Entity entity)
	{
		for (Layer layer : layers.values()) {
			List<EntityView> concerned = new ArrayList<>();
			for (EntityView entityView : layer.getEntities()) {
				Entity toEdit = entityView.getEntity();
				if (toEdit.getName().equals(entity.getName())) {
					concerned.add(entityView);
				}
			}
			concerned.forEach(entityView -> {
				Entity toRemove = entityView.getEntity();
				addEntity(entity, toRemove.getX(), toRemove.getY(), (int) toRemove.getZ());
				canvas.removeEntity(entityView);
			});
			layer.getEntities().removeAll(concerned);
		}
	}

	/**
	 * Sets the entities/layers of this LayerEditor to those described in the
	 * given Level object.
	 * 
	 * @param level
	 *            Level to be loaded
	 */
	public void loadLevel(Level level)
	{
		this.clear();
		canvas.clear();
		for (Entity entity : level.getEntities()) {
			addEntity(entity, entity.getX(), entity.getY(), (int) entity.getZ());
		}
		selectLayer(1);
	}

	public List<Layer> getLayers()
	{
		return new ArrayList<Layer>(layers.values());
	}

	/**
	 * Returns the layer that is currently selected.
	 * 
	 * @return Currently selected layer.
	 */
	public int getCurrentLayer()
	{
		return currLayer;
	}

	/**
	 * Clear all o the layers/entities currently in the LayerEditor and
	 * reinitialize the LayerEditor.
	 */
	private void clear()
	{
		while (layerCount > 1) {
			executeDelete(layerCount);
		}
		setup();
	}

	/*
	 * Initialize the LayerEditor.
	 */
	private void setup()
	{
		canvas = new Canvas(workspace);
		setCenter(canvas);
		layers = new HashMap<Integer, Layer>();
		layerCount = 0;
		currLayer = 1;
		addKeyActions();
		newLayer();
	}

	/**
	 * Add all of the key actions to the LayerEditor Backspace --> delete
	 * currently selected entities ctrl + 'C' --> copy currently selected
	 * entities ctrl + 'V' --> paste currently selected entities
	 */
	private void addKeyActions()
	{
		workspace.setFocusTraversable(true);
		workspace.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.BACK_SPACE)) {
				for (Layer layer : layers.values()) {
					List<EntityView> selectedEntities = layer.getSelectedEntities();
					layer.getEntities().removeAll(layer.getSelectedEntities());
					selectedEntities.forEach(entity -> canvas.removeEntity(entity));
				}
			}
			if (e.getCode().equals(KeyCode.RIGHT)) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(entity -> {
						entity.moveX(canvas.getTileSize());
					});
				}
			}
			if (e.getCode().equals(KeyCode.LEFT)) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(entity -> {
						if (entity.getEntity().getX() > canvas.getTileSize()) {
							entity.moveX(-1 * canvas.getTileSize());
						}
					});
				}
			}
			if (e.getCode().equals(KeyCode.DOWN)) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(entity -> {
						entity.moveY(canvas.getTileSize());
					});
				}
			}
			if (e.getCode().equals(KeyCode.UP)) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(entity -> {
						if (entity.getEntity().getY() > canvas.getTileSize()) {
							entity.moveY(-1 * canvas.getTileSize());
						}
					});
				}
			}
		});
	}

	/**
	 * Get the currently selected entity from the workspace's sidebar.
	 * 
	 * @return Workspace's currently selected entity.
	 */
	private Entity getCurrentEntity()
	{
		return workspace.getSelectedEntity();
	}

	/**
	 * Place the entity at the position of the given MouseEvent.
	 * 
	 * @param e
	 *            MouseEvent to place the entity at.
	 */
	public void addEntity(Entity entity, MouseEvent e)
	{
		try {
			addEntity(entity, e.getX() + canvas.getXScrollAmount(), e.getY() + canvas.getYScrollAmount(), currLayer);
		} catch (Exception exception) {
			showSelectMessage();
		}
	}

	/**
	 * Gets the bounds of the given Entity at the position given by the mouse
	 * 
	 * @param entity
	 *            Entity whose bounds is being retrieved.
	 * @param e
	 *            Mouse event which describes the location of the Entity.
	 * @return Bounds of the Entity.
	 */
	private Bounds boundsFromEntity(Entity entity, MouseEvent e)
	{
		Bounds bounds = new Rectangle(e.getX(), e.getY(), entity.getWidth(), entity.getHeight()).getBoundsInLocal();
		return bounds;
	}

	/**
	 * Add the given entity to the given x, y, and z position. z position =
	 * layer number.
	 * 
	 * @param entity
	 *            Entity to be added to the LayerEditor.
	 * @param x
	 *            x position to place the Entity
	 * @param y
	 *            y position to place the Entity
	 * @param z
	 *            layer to place the Entity in
	 * @return EntityView that was added.
	 */
	public EntityView addEntity(Entity entity, double x, double y, int z)
	{
		EntityView addedEntity = canvas.addEntity(entity, x, y);
		addedEntity.getEntity().setZ(z);
		setNumLayers(z);
		layers.get(z).addEntity(addedEntity);
		addedEntity.setOnMouseClicked(e -> {
			if (!e.isShiftDown()) {
				for (Layer layers : layers.values()) {
					for (EntityView display : layers.getEntities()) {
						selectEntity(display, false);
					}
				}
			}
			selectEntity(addedEntity, !addedEntity.isSelected());
		});
		return addedEntity;
	}

	/**
	 * Set the given EntityViews selected value.
	 * 
	 * @param entity
	 *            EntityView to be selected/deselected.
	 * @param selected
	 *            true if selected, false if deselected
	 */
	private void selectEntity(EntityView entity, boolean selected)
	{
		entity.setSelected(selected);
	}

	/**
	 * Set the upper limit on the number of layers that this LayerEditor has.
	 * 
	 * @param z
	 *            Number for last layer in this LayerEditor
	 */
	private void setNumLayers(int z)
	{
		while (layerCount < z) {
			newLayer();
		}
	}

	/**
	 * Make a new layer
	 */
	public void newLayer()
	{
		layerCount++;
		layers.put(layerCount, new Layer());
		System.out.println(layerCount);
		// newLayerSelected(layerCount);
	}

	/**
	 * Select the given layer.
	 * 
	 * @param newLayer
	 *            Layer to be selected.
	 */
	public void selectLayer(int newLayer)
	{
		newLayerSelected(newLayer);
	}

	private void newLayerSelected(int newVal)
	{
		for (Layer layer : layers.values()) {
			for (Node entity : layer.getEntities()) {
				entity.setOpacity(0.3);
				entity.toBack();
			}
		}

		for (Node entity : layers.get(newVal).getEntities()) {
			entity.setOpacity(1);
			entity.toFront();
		}
		currLayer = newVal;
	}

	/**
	 * Select this LayerEditor (select the first layer)
	 */
	public void select()
	{
		this.selectLayer(1);
		// allow this layer to have key actions
		addKeyActions();
	}

	/**
	 * Show an error message
	 */
	private void showSelectMessage()
	{
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		String message = workspace.getResources().getString("SelectAnEntity");
		Alert alert = maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", message);
		alert.show();
	}

	/**
	 * Get the number of layers in this LayerEditor
	 * 
	 * @return Current number of layers.
	 */
	public int getLayerCount()
	{
		return layerCount;
	}

	/**
	 * Delete the given layer from the LayerEditor.
	 * 
	 * @param layer
	 *            Layer to be deleted
	 */
	public void deleteLayer(int layer)
	{
		if (layerCount == 1) {
			String message = workspace.getResources().getString("LayerError");
			Alert alert = maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", message);
			alert.showAndWait();
		} else {
			executeDelete(layer);
		}
	}

	/**
	 * Actually delete the given layer
	 * 
	 * @param layer
	 */
	private void executeDelete(int layer)
	{
		if (layers.get(layer).getEntities().size() != 0) {
			layers.get(layer).getEntities().stream().forEach(id -> {
				canvas.removeEntity(id);
			});
		}
		layers.remove(layer);
		layerCount--;
	}

}