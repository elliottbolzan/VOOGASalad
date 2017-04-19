package engine.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import engine.entities.CameraEntity;
import engine.Entity;
import engine.game.gameloop.Scorebar;
import javafx.geometry.Pos;
import game_data.Game;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import player.Overlay;

/**
 * @author Jay Doherty
 * 
 * This class handles the graphics display for the GamePlayer. The GameEngine can set
 * the Entities to display, creates ImageViews for all of them, and then binds their coordinates
 * so that changes to the Entities in the game engine automatically cause their image to move
 * on screen. This class also holds the Scorebar (which has time/lives/score) and the Camera.
 */
public class GraphicsEngine {
	
	private Collection<Entity> entities;
	private Collection<ImageView> nodes;
	private CameraEntity camera;
	private Scorebar scorebar;
	private Overlay overlay;
	
	private Pane displayArea;
	
	public GraphicsEngine(Game game, Overlay overlay) {
		this.camera = new CameraEntity();
		this.entities = new ArrayList<Entity>();
		this.nodes = new ArrayList<ImageView>();
		this.scorebar = new Scorebar(game);
		this.overlay = overlay;
		this.setupView();
	}
	
	/**
	 * @return the graphical display for the game
	 */
	public Pane getView() {
		return displayArea;
	}
	
	/**
	 * Sets the camera used to move around the display
	 * @param newCamera
	 */
	public void setCamera(CameraEntity newCamera) {
		this.camera = newCamera;
	}
	
	/**
	 * Returns the scorebar used in the GameLoop
	 * @return
	 */
	public Scorebar getScorebar(){
		return scorebar;
	}
	
	/**
	 * Sets the collection of entities that will be drawn on every call to update.
	 * @param entities : current entities to draw on screen
	 */
	public void setEntitiesCollection(Collection<Entity> entities) {
		this.entities = entities;
		this.updateView();
	}
	
	/**
	 * Clears the display and places text on the screen.
	 * @param text : 
	 */
	public void fillScreenWithText(String text) {
		this.clearView();
		Label label = new Label(text);
		label.setPrefSize(displayArea.getWidth(), displayArea.getHeight());
		label.setFont(new Font(displayArea.getWidth()/text.length()));
		label.setAlignment(Pos.CENTER);
		displayArea.getChildren().add(label);
	}
	
	/**
	 * Call this every frame to animate the camera.
	 */
	public void updateFrame() {
		this.updateCamera();
		this.updateScorebar();
	}
	
	/**
	 * Call this conservatively, it seems to require a lot of memory.
	 */
	public void updateView() {
		this.clearView();
		this.drawAllEntities();
		this.sortViewByZIndex();
	}
	
	private void updateCamera() {
		for(ImageView node : nodes) {
			node.setTranslateX(-camera.getX());
			node.setTranslateY(-camera.getY());
		}
	}
	
	private void updateScorebar() {
		overlay.setScore(scorebar.getScore());
		overlay.setLives(Integer.toString(scorebar.getLives()));
		overlay.setLevel(Integer.toString(scorebar.getLevel()));
		overlay.setTime(scorebar.getTime());
	}
	
	private void clearView() {
		this.nodes.clear();
		displayArea.getChildren().clear();
	}
	
	/**
	 * Uses NodeFactory to fetch a JavaFX ImageView for each entity and binds their x and y coordinates.
	 */
	private void drawAllEntities() {
		NodeFactory factory = new NodeFactory();
		for(Entity entity : entities) {
			ImageView node = (ImageView)factory.getNodeFromEntity(entity);
			this.makeBindings(node, entity);
			this.nodes.add(node);
			displayArea.getChildren().add(node);	
		}
	}
	
	private void makeBindings(ImageView node, Entity entity) {
		node.xProperty().bind(entity.xProperty());
		node.yProperty().bind(entity.yProperty());
		node.setTranslateZ(entity.getZ());
		node.visibleProperty().bind(entity.isVisibleProperty());
		entity.imagePathProperty().addListener(
				(observer, oldPath, newPath) -> {
					node.setImage(new Image(newPath));
			});
	}
	
	private void sortViewByZIndex() {
		List<Node> sortedNodes = displayArea.getChildren().sorted((a,b) -> {return Double.compare(b.getTranslateZ(), a.getTranslateZ());});
		displayArea.getChildren().setAll(sortedNodes); 
	}
	
	private void setupView() {
		displayArea = new Pane();
		displayArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		HBox.setHgrow(displayArea, Priority.ALWAYS);
		VBox.setVgrow(displayArea, Priority.ALWAYS);
		this.clipAtEdges(displayArea);
		this.updateView();
	}
	
	private void clipAtEdges(Pane pane) {
		Rectangle clipBoundaries = new Rectangle();
		clipBoundaries.widthProperty().bind(pane.widthProperty());
		clipBoundaries.heightProperty().bind(pane.heightProperty());
		pane.setClip(clipBoundaries);
	}
}
