package authoring.tutorial;

import authoring.Workspace;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.views.View;

public class AuthoringTutorial extends View {
	
	private static final int PADDING = 20;

	private Workspace workspace;
	private Label label;

	public AuthoringTutorial(Workspace workspace) {
		this.workspace = workspace;
		show();
		editMario();
	}
	
	private void show() {
		setupView();
		Stage stage = workspace.getMaker().display("AuthoringTourMenuItem", 260, 400, this, Modality.NONE, false);
		stage.setX(workspace.getScene().getWindow().getX() + workspace.getScene().getWindow().getWidth() + PADDING);
		stage.setY(workspace.getScene().getWindow().getY());
	}
	
	private void setupView() {
		VBox tutorialBox = new VBox();
		tutorialBox.setPadding(new Insets(20, 0, 0, 0));
		tutorialBox.setPrefWidth(200);
		Image mario = new Image(getClass().getClassLoader().getResource("resources/images/mario.png").toExternalForm());
		ImageView marioView = new ImageView(mario);
		marioView.setScaleX(.75);
		marioView.setScaleY(.75);
		label = new Label();
		label.textProperty().bind(workspace.getPolyglot().get("FirstStep"));
		label.getStyleClass().add("chat-bubble");
		label.setWrapText(true);
		label.setMaxWidth(200);
		tutorialBox.getChildren().addAll(label, marioView);
		tutorialBox.setAlignment(Pos.CENTER);
		label.setContentDisplay(ContentDisplay.CENTER);
		setCenter(tutorialBox);
	}

	private void editMario() {
		label.textProperty().bind(workspace.getPolyglot().get("FirstPrompt"));
		workspace.getPanel().getEntityDisplay().changeEditHandler(() -> clickedEdit());
	}

	private void clickedEdit() {
		label.textProperty().bind(workspace.getPolyglot().get("SecondStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker()
				.changeNewHandler(() -> addedEvent("ThirdStep", workspace.getPolyglot().get("FirstEvent").get(),
						() -> addedKeyPress("FourthStep", () -> savedEvent("FifthStep", () -> addedAction("SixthStep",
								workspace.getPolyglot().get("FirstAction").get(), () -> afterAction("SeventhStep", () -> savedAction()))))));
	}

	
	private void addedEvent(String s, String event, Runnable r) {
		label.textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().getEditor().initTutorialAction(event,
				r);

	}

	private void addedKeyPress(String s, Runnable r) {
		label.textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().getEditor().changeSaveHandler(r);
	}

	private void savedEvent(String s, Runnable r) {
		label.textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().changeNewHandler(r);
	}

	private void addedAction(String s, String action, Runnable r) {
		label.textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor()
				.initTutorialAction(action, r);
	}

	private void afterAction(String s, Runnable r) {
		label.textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getActionPicker().getEditor().changeSaveHandler(r);
	}

	private void savedAction() {
		label.textProperty().bind(workspace.getPolyglot().get("EighthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().changeSaveHandler(() -> savedCharacter());
	}

	private void savedCharacter() {
		label.textProperty().bind(workspace.getPolyglot().get("NinthStep"));
		workspace.getLevelEditor().getCurrentLevel().addEntityListener(() -> canvasCharacter());
	}

	private void canvasCharacter() {
		label.textProperty().bind(workspace.getPolyglot().get("TenthStep"));
		workspace.getPanel().getEntityDisplay().changeNewHandler(() -> createMonster());
	}

	private void createMonster() {
		label.textProperty().bind(workspace.getPolyglot().get("EleventhStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().changeImageHandler(() -> changedMonsterImage());
	}

	private void changedMonsterImage() {
		label.textProperty().bind(workspace.getPolyglot().get("TwelfthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker()
				.changeNewHandler(
						() -> addedEvent("ThirteenthStep", workspace.getPolyglot().get("SecondEvent").get(),
								() -> addedCollision("FourteenthStep",
										() -> savedEvent("FifteenthStep", () -> addedAction("SixteenthStep",
												workspace.getPolyglot().get(workspace.getPolyglot().get("SecondAction").get()).toString(), () -> afterAction("SeventeenthStep",
														() -> savedMonsterAction()))))));
	}

	private void addedCollision(String s, Runnable r) {
		label.textProperty().bind(workspace.getPolyglot().get(s));
		workspace.getPanel().getEntityDisplay().getEntityMaker().getEventPicker().getEditor().changeSaveHandler(r);
	}

	private void savedMonsterAction() {
		label.textProperty().bind(workspace.getPolyglot().get("EighteenthStep"));
		workspace.getPanel().getEntityDisplay().getEntityMaker().changeSaveHandler(() -> savedMonsterCharacter());
	}

	private void savedMonsterCharacter() {
		label.textProperty().bind(workspace.getPolyglot().get("NinteenthStep"));
		 workspace.getLevelEditor().getCurrentLevel().addEntityListener(() ->
		 canvasMonsterCharacter());
	}
	
	private void canvasMonsterCharacter() {
		label.textProperty().bind(workspace.getPolyglot().get("TwentiethStep"));
		//workspace.getPanel().getEntityDisplay().changeNewHandler(() -> createMonster());
	}
}
