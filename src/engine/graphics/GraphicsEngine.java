package engine.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import engine.entities.Entity;
import engine.entities.entities.AchievementEntity;
import engine.entities.entities.CameraEntity;
import engine.game.Level;
import engine.game.LevelManager;
import engine.game.gameloop.LevelSelectionStepStrategy;
import engine.game.gameloop.Scorebar;
import game_data.Game;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import player.launcher.AbstractPlayer;
import player.score.Overlay;
import polyglot.Case;
import polyglot.Polyglot;

/**
 * @author Jay Doherty (modified by Jesse Yue)
 * 
 *         This class handles the graphics display for the GamePlayer. The
 *         GameEngine can set the Entities to display, creates ImageViews for
 *         all of them, and then binds their coordinates so that changes to the
 *         Entities in the game engine automatically cause their image to move
 *         on screen. This class also holds the Scorebar (which has
 *         time/lives/score) and the Camera.
 */
public class GraphicsEngine {
	
	private Polyglot polyglot;
	private ResourceBundle resources;

	private Collection<Entity> entities;
	private Collection<ImageView> nodes;
	private CameraEntity camera;

	private AbstractPlayer player;
	private Scorebar scorebar;
	private Overlay overlay;

	private BorderPane displayArea;
	private Game game;

	public GraphicsEngine(Game game, AbstractPlayer player, Overlay overlay, Polyglot polyglot, ResourceBundle resources) {
		this.camera = new CameraEntity();
		this.entities = new ArrayList<Entity>();
		this.nodes = new ArrayList<ImageView>();
		this.scorebar = new Scorebar(game);

		this.overlay = overlay;
		this.polyglot = polyglot;
		this.resources = resources;
		this.player = player;
		this.game = game;

		this.setupView();
	}

	public void setupLevel(Level level) {
		
		this.setCamera(level.getCamera());
		this.setEntitiesCollection(level.getEntities());

		displayArea.setMaxSize(level.getCamera().getWidth(), level.getCamera().getHeight());
		
		Image backgroundImage = (new NodeFactory()).getNodeFromEntity(level.getBackground()).getImage();
		displayArea.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

	}

	/**
	 * @return the graphical display for the game
	 */
	public Pane getView() {
		return displayArea;
	}

	/**
	 * @return the camera used to move around the display
	 */
	public CameraEntity getCamera() {
		return this.camera;
	}

	/**
	 * Returns the scorebar used in the GameLoop
	 * 
	 * @return
	 */
	public Scorebar getScorebar() {
		return scorebar;
	}

	/**
	 * Sets the camera used to move around the display
	 * 
	 * @param newCamera
	 */
	private void setCamera(CameraEntity newCamera) {
		this.camera = newCamera;
	}

	/**
	 * Sets the collection of entities that will be drawn on every call to
	 * update.
	 * 
	 * @param entities
	 *            current entities to draw on screen
	 */
	private void setEntitiesCollection(Collection<Entity> entities) {
		this.entities = entities;
		this.updateView();
	}

	/**
	 * Clears the display and places text on the screen.
	 * 
	 * @param resourceFileTextName
	 */
	public void fillScreenWithText(String resourceFileTextName) {
		this.clearView();
		Label label = new Label();
		label.textProperty().bind(polyglot.get(resourceFileTextName, Case.TITLE));
		label.scaleXProperty().bind(displayArea.widthProperty().divide(label.widthProperty()));
		label.scaleYProperty().bind(displayArea.widthProperty().divide(label.widthProperty()));
		label.setAlignment(Pos.CENTER);
		displayArea.setCenter(label);
	}

	public void showImage(String imageName) {
		this.clearView();
		Image image = new Image(resources.getString(imageName));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(400);
		imageView.setPreserveRatio(true);
		displayArea.setCenter(imageView);
	}

	public void displayLevelSelectionScreen(LevelManager levelManager, LevelSelectionStepStrategy strategy) {
		this.clearView();
		new LevelSelectionGraphics(displayArea, levelManager, polyglot, strategy).draw();
	}

	/**
	 * Show Highscore and ability to share to Facebook
	 */
	public void endGame() {
		player.endGame(scorebar);
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
		for (ImageView node : nodes) {
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

	public void blankScorebar(boolean firstPass) {
		if (firstPass) {
			overlay.setScore(Overlay.BLANK_SCOREBAR_DISPLAY);
			overlay.setLives(Overlay.BLANK_SCOREBAR_DISPLAY);
		}
		overlay.setLevel(Overlay.BLANK_SCOREBAR_DISPLAY);
		overlay.setTime(Overlay.BLANK_SCOREBAR_DISPLAY);
	}

	private void clearView() {
		this.nodes.clear();
		displayArea.getChildren().clear();
	}

	/**
	 * Uses NodeFactory to fetch a JavaFX ImageView for each entity and binds
	 * their x and y coordinates.
	 */
	private void drawAllEntities() {
		NodeFactory factory = new NodeFactory();
		entities.stream().filter(s -> !(s instanceof AchievementEntity)).forEach(entity -> {
			ImageView node = (ImageView) factory.getNodeFromEntity(entity);
			this.makeBindings(node, entity);
			this.nodes.add(node);
			displayArea.getChildren().add(node);
		});
	}

	private void makeBindings(ImageView node, Entity entity) {
		node.xProperty().bind(entity.xProperty());
		node.yProperty().bind(entity.yProperty());
		node.setTranslateZ(entity.getZ());
		node.visibleProperty().bind(entity.isVisibleProperty());
		entity.imagePathProperty().addListener((observer, oldPath, newPath) -> {
			node.setImage(new Image(newPath));
		});
	}

	private void sortViewByZIndex() {
		List<Node> sortedNodes = displayArea.getChildren().sorted((a, b) -> {
			return Double.compare(a.getTranslateZ(), b.getTranslateZ());
		});
		displayArea.getChildren().setAll(sortedNodes);
	}

	private void setupView() {
		displayArea = new BorderPane();
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
