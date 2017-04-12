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
public class LayerEditor extends View
{
	Workspace workspace;
	Canvas canvas;
	Map<Integer, List<EntityDisplay>> layerEntities;
	List<EntityDisplay> copiedEntities;
	int layerCount;
	int currLayer;
	Bounds lastBounds;
	ComponentMaker maker;

	public LayerEditor(Workspace workspace)
	{
		super("");
		this.workspace = workspace;
		maker = new ComponentMaker(workspace.getResources());
		setup();
	}

	public Level getLevel()
	{
		Level thisLevel = new Level();
		for (List<EntityDisplay> entityList : layerEntities.values()) {
			for (EntityDisplay entity : entityList) {
				thisLevel.addEntity(entity.getEntity());
			}
		}
		return thisLevel;
	}

	public void loadLevel(Level level)
	{
		this.clear();
		canvas.clear();
		for (Entity entity : level.getEntities()) {
			addEntity(entity, entity.getX(), entity.getY(), (int) entity.getZ());
		}
		newLayerSelected(0);
	}

	private void clear()
	{
		while (layerCount > 0) {
			executeDelete(layerCount - 1);
		}
		setup();
	}

	private void setup()
	{
		canvas = new Canvas(workspace);
		setCenter(canvas);
		layerEntities = new HashMap<Integer, List<EntityDisplay>>();
		copiedEntities = new ArrayList<EntityDisplay>();
		layerCount = 0;
		currLayer = 0;
		lastBounds = new Rectangle().getBoundsInLocal();
		clickToAddEntity();
		addKeyActions();
		layerCount++;
		layerEntities.put(layerCount, new ArrayList<EntityDisplay>());
	}

	private void clickToAddEntity()
	{
		canvas.setPaneOnMouseClicked(e -> {
			if (e.isControlDown()) {
				placeEntity(e);
			}
		});
		canvas.setPaneOnMouseDragged(e -> {
			try {
				Bounds newBounds = boundsFromEntity(getCurrentEntity(), e);
				if (e.isControlDown() && e.isShiftDown() && !lastBounds.intersects(newBounds)) {
					lastBounds = newBounds;
					placeEntity(e);
				}
			} catch (Exception exception) {
			}
		});
	}

	private void addKeyActions()
	{
		workspace.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.BACK_SPACE)) {
				for (List<EntityDisplay> list : layerEntities.values()) {
					Iterator<EntityDisplay> iter = list.iterator();
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
				for (List<EntityDisplay> list : layerEntities.values()) {
					for (EntityDisplay entity : list) {
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

	private Entity getCurrentEntity()
	{
		return workspace.getSelectedEntity();
	}

	private void placeEntity(MouseEvent e)
	{
		try {
			addEntity(workspace.getSelectedEntity(), e.getX(), e.getY(), currLayer);
		} catch (Exception exception) {
			exception.printStackTrace();
			showSelectMessage();
		}
	}

	private Bounds boundsFromEntity(Entity entity, MouseEvent e)
	{
		Bounds bounds = new Rectangle(e.getX(), e.getY(), entity.getWidth(), entity.getHeight()).getBoundsInLocal();
		return bounds;
	}

	private void addEntity(Entity entity, double x, double y, int z)
	{
		EntityDisplay addedEntity = canvas.addEntity(entity, x, y);
		addedEntity.getEntity().setZ(z);
		setNumLayers(z);
		layerEntities.get(z).add(addedEntity);
		addedEntity.setOnMouseClicked(e -> {
			if (!e.isShiftDown()) {
				for (List<EntityDisplay> list : layerEntities.values()) {
					for (EntityDisplay display : list) {
						display.setSelected(false);
					}
				}
			}
			addedEntity.setSelected(!addedEntity.isSelected());
			//workspace.updateEntity(addedEntity.getEntity());
		});
	}

	// private void addEntity(Entity entity, double x, double y)
	// {
	// addEntity(entity, x, y, currLayer);
	// }

	private void setNumLayers(int z)
	{
		while (layerCount <= z) {
			newTab();
		}
	}

	public void newTab()
	{
		layerCount++;
		layerEntities.put(layerCount, new ArrayList<EntityDisplay>());
		workspace.setNewLayer(String.format("Layer %d", layerCount));
		// newLayerSelected(layerCount);
	}

	public void newLayerSelected(int newVal)
	{
		for (List<EntityDisplay> entityList : layerEntities.values()) {
			for (Node entity : entityList) {
				entity.setOpacity(0.3);
				entity.toBack();
			}
		}
		System.out.println(newVal + "This");
		if(layerEntities.get(newVal).size()!=0){
		for (Node entity : layerEntities.get(newVal)) {
			entity.setOpacity(1);
			entity.toFront();
			}
		}
		currLayer = newVal;
	}

	public void select()
	{
		this.newLayerSelected(1);
		// allow this layer to have key actions
		addKeyActions();
	}

	private void showSelectMessage()
	{
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		String message = workspace.getResources().getString("SelectAnEntity");
		Alert alert = maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", message);
		alert.show();
	}

	public int getLayerCount()
	{
		return layerCount;
	}

	/*
	 * ALERT: DO NOT TRY DELETING LAYER 1.
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

	private void executeDelete(int layer)
	{
		if (layerEntities.get(layer).size() != 0) {
			layerEntities.get(layer).stream().forEach(id -> {
				canvas.removeEntity(id);
			});
		}
		layerEntities.remove(layer);
		List<Integer> changedValues = layerEntities.keySet().stream().filter(elt -> elt > layer).map(elt -> elt - 1)
				.collect(Collectors.toList());
		changedValues.stream().forEach(id -> {
			layerEntities.put(id, layerEntities.get(id + 1));
		});
		layerCount--;
		workspace.selectExistingLevel(layerCount);
	}

}