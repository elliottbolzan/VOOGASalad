package authoring.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import authoring.Workspace;
import engine.Entity;
import engine.game.Level;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import utils.views.View;

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

	/**
	 * Make a new LayerEditor.
	 * 
	 * @param workspace
	 *            Workspace that the LayerEditor will be in.
	 */
	public LayerEditor(Workspace workspace)
	{
		this.workspace = workspace;
		setup();
	}

	@Override
	public LayerEditor clone()
	{
		LayerEditor newLevel = new LayerEditor(workspace);
		newLevel.setNumLayers(layerCount);
		layers.keySet().stream().forEach(id -> {
			for (EntityView entity : layers.get(id).getEntities()) {
				System.out.println(entity.getEntity().getX() + " " + entity.getEntity().getY());
				newLevel.addEntity(entity.getEntity(), entity.getEntity().getX(), entity.getEntity().getY(), id);
			}
			;

		});
		System.out.println(newLevel.layers.get(1).getEntities().size());
		return newLevel;
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
				entity.widthProperty().set(toRemove.getWidth());
				entity.heightProperty().set(toRemove.getHeight());
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
				e.consume();
			}
			if (e.getCode().equals(KeyCode.RIGHT)) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(entity -> {
						entity.moveX(canvas.getTileSize());
					});
				}
				e.consume();
			}
			if (e.getCode().equals(KeyCode.LEFT)) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(entity -> {
						if (entity.getEntity().getX() > canvas.getTileSize()) {
							entity.moveX(-1 * canvas.getTileSize());
						}
					});
				}
				e.consume();
			}
			if (e.getCode().equals(KeyCode.DOWN)) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(entity -> {
						entity.moveY(canvas.getTileSize());
					});
				}
				e.consume();
			}
			if (e.getCode().equals(KeyCode.UP)) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(entity -> {
						if (entity.getEntity().getY() > canvas.getTileSize()) {
							entity.moveY(-1 * canvas.getTileSize());
						}
					});
				}
				e.consume();
			}
		});
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
			exception.printStackTrace();
		}
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
		addedEntity.setOnMousePressed(e -> {
			if (!e.isShiftDown() && !addedEntity.isSelected()) {
				for (Layer layer : layers.values()) {
					layer.getSelectedEntities().forEach(ent -> {
						ent.setSelected(false);
					});
				}
			}
			selectEntity(addedEntity, !addedEntity.isSelected());
		});
		addedEntity.setOnMouseDragged(e -> {
			addedEntity.setSelected(true);
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
		Layer newLayer = new Layer("Layer" + " " + layerCount);
		layers.put(layerCount, newLayer);
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
		Alert alert = workspace.getMaker().makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader",
				workspace.getPolyglot().get("SelectAnEntity"));
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
			Alert alert = workspace.getMaker().makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader",
					workspace.getPolyglot().get("LayerError"));
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
		List<Integer> changedValues = layers.keySet().stream().filter(elt -> elt > layer).map(elt -> elt - 1)
				.collect(Collectors.toList());
		changedValues.stream().forEach(id -> {
			layers.put(id, layers.get(id + 1));
		});
		layerCount--;
	}

}