package engine.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import engine.Entity;
import engine.entities.CameraEntity;
import engine.game.gameloop.Scorebar;
import game_data.Game;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
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

	public GraphicsEngine(Game game, Overlay overlay, Stage stage, Polyglot polyglot, ResourceBundle IOResources) {
		this.camera = new CameraEntity();
		this.entities = new ArrayList<Entity>();
		this.nodes = new ArrayList<ImageView>();
		this.scorebar = new Scorebar(game);
		this.overlay = overlay;
		this.stage = stage;
		this.game = game;
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		this.setupView();
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
	 * Sets the camera used to move around the display
	 * 
	 * @param newCamera
	 */
	public void setCamera(CameraEntity newCamera) {
		this.camera = newCamera;
		Image backgroundImage = (new NodeFactory()).getNodeFromEntity(newCamera).getImage();
		displayArea.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
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
	 * Sets the collection of entities that will be drawn on every call to
	 * update.
	 * 
	 * @param entities
	 *            current entities to draw on screen
	 */
	public void setEntitiesCollection(Collection<Entity> entities) {
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
		label.setPrefSize(displayArea.getWidth(), displayArea.getHeight());
		label.setFont(new Font(displayArea.getWidth() / label.textProperty().getValue().length()));
		label.setAlignment(Pos.CENTER);
		displayArea.setCenter(label);
	}

	/**
	 * Show Highscore and ability to share to Facebook
	 */
	public void endScreen() {
		this.clearView();
		VBox container = new VBox(20);
		String text = "Congratulations!\nNew Highscore!";
		Label congrats = new Label(text);
		congrats.setFont(new Font(displayArea.getWidth() / text.length()));
		TextField enterName = new TextField();
		enterName.setPromptText("Your name here");
		Button toHighscores = new Button("Continue");
		toHighscores.setOnAction(e -> saveName(enterName.getText()));

		container.getChildren().addAll(congrats, enterName, toHighscores);
		container.setAlignment(Pos.CENTER);
		displayArea.setCenter(container);
	}

	private void saveName(String name) {
		getScorebar().saveFinalScore(name);
		stage.setScene(new HighscoreMenu(stage, game, polyglot, IOResources).createScene());
	}

	public boolean isHighscore() {
		return this.getScorebar().isHighscore();
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
