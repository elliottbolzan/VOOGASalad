package authoring.canvas;

import engine.Entity;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EntityDisplay extends VBox
{
	private Entity entity;
	private ImageView image;
	private int tileSize;
	private boolean selected;

	public EntityDisplay(Entity entity, int gridSize, double x, double y)
	{
		// TODO: Instead of this.entity = entity, need this.entity =
		// entity.clone();
		this.entity = entity.clone();
		// this.entity = entity;
		this.image = new ImageView(new Image(entity.getImagePath()));
		this.setMinHeight(entity.getHeight());
		this.setMinWidth(entity.getWidth());
		this.tileSize = gridSize;
		selected = false;

		setup(gridSize, x, y);
	}

	private void setup(int gridSize, double x, double y)
	{
		this.setPrefHeight(10);
		this.setPrefWidth(10);
		image.fitWidthProperty().bind(this.minWidthProperty());
		image.fitHeightProperty().bind(this.minHeightProperty());

		entity.xProperty().bind(this.translateXProperty());
		entity.yProperty().bind(this.translateYProperty());
		entity.widthProperty().bind(image.fitWidthProperty());
		entity.heightProperty().bind(image.fitHeightProperty());

		this.getChildren().add(image);
		this.setMinWidth(getTiledCoordinate(image.getBoundsInLocal().getWidth()));
		this.setMinHeight(getTiledCoordinate(image.getBoundsInLocal().getHeight()));

		DragResizer.makeResizable(this, gridSize);
	}

	public Entity getEntity()
	{
		return entity;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
		if (selected) {
			Color borderColor = new Color(0, 0, 0, 0.2);
			this.setBorder(
					new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
			DropShadow ds = new DropShadow();
			ds.setOffsetY(7.0);
			ds.setOffsetX(7.0);
			ds.setColor(Color.GRAY);
			this.setEffect(ds);

		} else {
			this.setEffect(null);
			this.setBorder(null);
		}
	}

	public boolean isSelected()
	{
		return this.selected;
	}

	// TODO: This method is repeated in DragResizer
	private double getTiledCoordinate(double coordinate)
	{
		double gridCoordinate = ((int) coordinate / tileSize) * tileSize;
		if (coordinate % tileSize > tileSize / 2) {
			return gridCoordinate + tileSize;
		}
		return gridCoordinate;
	}

}