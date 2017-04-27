package engine.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import engine.entities.CameraEntity;
import engine.entities.Entity;
import engine.game.Level;
import engine.game.LevelManager;
import engine.game.gameloop.LevelSelectionStepStrategy;
import engine.game.gameloop.Scorebar;
import game_data.Game;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.MediaManager;
import player.menu.HighscoreMenu;
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
	private ResourceBundle IOResources;

	private Collection<Entity> entities;
	private Collection<ImageView> nodes;
	private CameraEntity camera;

	private Scorebar scorebar;
	private Overlay overlay;

	private Stage stage;
	private BorderPane displayArea;
	private Game game;
	private MediaManager mediaManager;

	public GraphicsEngine(Game game, Overlay overlay, Stage stage, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		this.camera = new CameraEntity();
		this.entities = new ArrayList<Entity>();
		this.nodes = new ArrayList<ImageView>();
		this.scorebar = new Scorebar(game);
		this.overlay = overlay;
		this.stage = stage;
		this.game = game;
		this.mediaManager = mediaManager;
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		this.setupView();
	}

	public void setupLevel(Level level) {
		this.setCamera(level.getCamera());
		this.setEntitiesCollection(level.getEntities());
		
		displayArea.setMaxSize(level.getCamera().getWidth(), level.getCamera().getHeight());
		
		//Image backgroundImage = (new NodeFactory()).getNodeFromEntity(level.getCamera()).getImage();
		//displayArea.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
		//		BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
	
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
	
	public void displayLevelSelectionScreen(LevelManager levelManager, LevelSelectionStepStrategy strategy){
		this.clearView();
		new LevelSelectionGraphics(displayArea, levelManager, polyglot, strategy).draw();
	}
	
	/**
	 * Show Highscore and ability to share to Facebook
	 */
	public void endScreen() {
		this.clearView();
		VBox container = new VBox(30);
		
		Label congrats = new Label("New Highscore!");
		congrats.scaleXProperty().bind(displayArea.widthProperty().divide(congrats.widthProperty()).divide(2));
		congrats.scaleYProperty().bind(congrats.scaleXProperty());
		
		TextField enterName = new TextField();
		enterName.setMaxWidth(displayArea.getWidth()/2);
		enterName.setPromptText("Your name here");
		
		Button toHighscores = new Button("Continue");
		toHighscores.setOnAction(e -> {
			getScorebar().saveFinalScore(enterName.getText());
			stage.setScene(new HighscoreMenu(stage, game, mediaManager, polyglot, IOResources).createScene());
		});

		container.getChildren().addAll(congrats, enterName, toHighscores);
		container.setAlignment(Pos.CENTER);
		displayArea.setCenter(container);
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
		for (Entity entity : entities) {
			ImageView node = (ImageView) factory.getNodeFromEntity(entity);
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
