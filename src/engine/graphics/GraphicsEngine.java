package engine.graphics;

import java.util.ArrayList;
import java.util.Collection;

import engine.graphics.cameras.Camera;
import engine.graphics.cameras.ScrollingCamera;
import engine.Entity;
import engine.game.LevelManager;
import engine.game.gameloop.Scorebar;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * @author Jay Doherty
 * 
 * This class handles the graphics display for the GamePlayer. The GameEngine can set
 * the Entities to display, and then call update() every frame to animate the graphics.
 */
public class GraphicsEngine {
	
	private Collection<Entity> entities;
	private Camera camera;
	private Scorebar scorebar;
	
	private Pane displayArea;
	private Label scorebarDisplay;
	private String dataFolderPath;
	
	public GraphicsEngine(String dataFolderPath) {
		this.camera = new ScrollingCamera(0,0);
		this.entities = new ArrayList<Entity>();
		this.scorebar = new Scorebar();
		this.dataFolderPath = dataFolderPath;
		this.setupView();
		this.setupScorebar();
	}
	
	/**
	 * @return the graphical display for the game
	 */
	public Pane getView() {
		return displayArea;
	}
	
	public Label getScorebarDisplay() {
		return scorebarDisplay;
	}
	
	/**
	 * Sets the camera used to move around the display
	 * @param newCamera
	 */
	public void setCamera(Camera newCamera) {
		this.camera = newCamera;
	}
	
	public void setScorebar(Scorebar currentManager) {
		this.scorebar = currentManager;
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
	 * Call this every frame to animate the camera.
	 */
	public void updateFrame() {
		this.updateCamera();
		this.updateScorebar();
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
		displayArea.getChildren().add(label);
	}
	
	/**
	 * Call this conservatively, it seems to require a lot of memory.
	 */
	public void updateView() {
		this.clearView();
		this.drawAllEntities();
	}
	
	private void updateCamera() {
		camera.update();
		displayArea.setTranslateX(-camera.getX());
		displayArea.setTranslateY(-camera.getY());
	}
	
	private void updateScorebar() {
		scorebarDisplay.setText(String.format("Time: %s \nLives: %s \nScore: %s", scorebar.getTime(), scorebar.getLives(), scorebar.getScore()));
	}
	
	private void clearView() {
		displayArea.getChildren().clear();
	}
	
	private void drawAllEntities() {
		NodeFactory factory = new NodeFactory(dataFolderPath);
		for(Entity entity : entities) {
			ImageView node = (ImageView)factory.getNodeFromEntity(entity);
			node.xProperty().bind(entity.xProperty());
			node.yProperty().bind(entity.yProperty());
			displayArea.getChildren().add(node);	
		}
	}
	
	private void setupView() {
		displayArea = new Pane();
		displayArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		HBox.setHgrow(displayArea, Priority.ALWAYS);
		VBox.setVgrow(displayArea, Priority.ALWAYS);
		this.clipAtEdges(displayArea);
		this.drawAllEntities();
	}
	
	private void setupScorebar() {
		scorebarDisplay = new Label();
		this.updateScorebar();
	}
	
	private void clipAtEdges(Pane pane) {
		Rectangle clipBoundaries = new Rectangle();
		clipBoundaries.widthProperty().bind(pane.widthProperty());
		clipBoundaries.heightProperty().bind(pane.heightProperty());
		pane.setClip(clipBoundaries);
	}
}
