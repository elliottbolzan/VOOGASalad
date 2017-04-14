package authoring.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
 * 
 * @author jimmy Modified by Mina Mungekar
 *
 */
public class LayerEditor extends View {
	private Workspace workspace;
	private Canvas canvas;
	private Map<Integer, Layer> layers;
	private List<EntityDisplay> copiedEntities;
	private int layerCount;
	private int currLayer;
	private Bounds lastBounds;
	private ComponentMaker maker;

	public LayerEditor(Workspace workspace) {
		super("");
		this.workspace = workspace;
		maker = new ComponentMaker(workspace.getResources());
		setup();
	}

	public Level getLevel() {
		Level thisLevel = new Level();
		for (Layer layer : layers.values()) {
			for (EntityDisplay entity : layer.getEntities()) {
				thisLevel.addEntity(entity.getEntity());
			}
		}
		return thisLevel;
	}

	public void loadLevel(Level level) {
		this.clear();
		canvas.clear();
		for (Entity entity : level.getEntities()) {
			addEntity(entity, entity.getX(), entity.getY(), (int) entity.getZ());
		}
		selectLayer(1);
	}

	public int getCurrentLayer() {
		return currLayer;
	}

	private void clear() {
		while (layerCount > 1) {
			executeDelete(layerCount);
		}
		setup();
	}

	private void setup() {
		canvas = new Canvas(workspace);
		setCenter(canvas);
		layers = new HashMap<Integer, Layer>();
		copiedEntities = new ArrayList<EntityDisplay>();
		layerCount = 0;
		currLayer = 1;
		lastBounds = new Rectangle().getBoundsInLocal();
		addKeyActions();
		newLayer();
	}

	private void addKeyActions() {
		workspace.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.BACK_SPACE)) {
				for (Layer layer : layers.values()) {
					List<EntityDisplay> entities = layer.getEntities();
					Iterator<EntityDisplay> iter = entities.iterator();
					while (iter.hasNext()) {
						EntityDisplay entity = iter.next();
						if (entity.isSelected()) {
							iter.remove();
							canvas.removeEntity(entity);
						}
					}
				}
			}
			if (e.getCode().equals(KeyCode.C) && e.isControlDown()) {
				copiedEntities.clear();
				for (Layer layer : layers.values()) {
					for (EntityDisplay entity : layer.getEntities()) {
						if (entity.isSelected()) {
							copiedEntities.add(entity);
						}
					}
				}
			}
			if (e.getCode().equals(KeyCode.V) && e.isControlDown()) {
				for (EntityDisplay entity : copiedEntities) {
					addEntity(entity.getEntity(), entity.getEntity().getX() + 25, entity.getEntity().getY() + 25,
							currLayer);
				}
			}
		});
	}

	private Entity getCurrentEntity() {
		return workspace.getSelectedEntity();
	}

	private void placeEntity(MouseEvent e) {
		try {
			addEntity(workspace.getSelectedEntity(), e.getX(), e.getY(), currLayer);
		} catch (Exception exception) {
			exception.printStackTrace();
			showSelectMessage();
		}
	}

	private Bounds boundsFromEntity(Entity entity, MouseEvent e) {
		Bounds bounds = new Rectangle(e.getX(), e.getY(), entity.getWidth(), entity.getHeight()).getBoundsInLocal();
		return bounds;
	}

	public void addEntity(Entity entity, double x, double y, int z) {
		EntityDisplay addedEntity = canvas.addEntity(entity, x, y);
		addedEntity.getEntity().setZ(z);
		setNumLayers(z);
		layers.get(z).addEntity(addedEntity);
		addedEntity.setOnMouseClicked(e -> {
			if (!e.isShiftDown()) {
				for (Layer layers : layers.values()) {
					for (EntityDisplay display : layers.getEntities()) {
						selectEntity(display, false);
					}
				}
			}
			selectEntity(addedEntity, !addedEntity.isSelected());
		});
	}

	private void selectEntity(EntityDisplay entity, boolean selected) {
		entity.setSelected(selected);
	}

	private List<EntityDisplay> getSelectedEntities() {
		List<EntityDisplay> selectedEntities = new ArrayList<EntityDisplay>();
		for (Layer layer : layers.values()) {
			for (EntityDisplay entity : layer.getEntities()) {
				if (entity.isSelected()) {
					selectedEntities.add(entity);
				}
			}
		}
		return selectedEntities;
	}

	private void setNumLayers(int z) {
		while (layerCount < z) {
			newLayer();
		}
	}

	public void makeLayer() {
		newLayer();
	}

	private void newLayer() {
		layerCount++;
		layers.put(layerCount, new Layer());
		workspace.setNewLayer(String.format("Layer %d", layerCount));
		// newLayerSelected(layerCount);
	}

	public void selectLayer(int newLayer) {
		newLayerSelected(newLayer);
	}

	private void newLayerSelected(int newVal) {
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

	public void select() {
		this.selectLayer(1);
		// allow this layer to have key actions
		addKeyActions();
	}

	private void showSelectMessage() {
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		String message = workspace.getResources().getString("SelectAnEntity");
		Alert alert = maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", message);
		alert.show();
	}

	public int getLayerCount() {
		return layerCount;
	}

	/*
	 * ALERT: DO NOT TRY DELETING LAYER 1.
	 */
	public void deleteLayer(int layer) {
		if (layerCount == 1) {
			String message = workspace.getResources().getString("LayerError");
			Alert alert = maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", message);
			alert.showAndWait();
		} else {
			executeDelete(layer);
		}
	}

	private void executeDelete(int layer) {
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
		workspace.selectExistingLevel(layerCount);
	}

}