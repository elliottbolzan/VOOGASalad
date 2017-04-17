package authoring.components;

import authoring.Workspace;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Elliott Bolzan
 * 
 *         Adapted from:
 *         http://stackoverflow.com/questions/29625170/display-popup-with-progressbar-in-javafx.
 *
 */
public class ProgressDialog {

	private Workspace workspace;
	private Stage stage;
	private ProgressBar progressBar = new ProgressBar();

	public ProgressDialog(Workspace workspace) {
		this.workspace = workspace;
		setupStage();
		setupView();
		stage.show();
	}

	private void setupStage() {
		stage = new Stage();
		stage.initStyle(StageStyle.UNIFIED);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(workspace.getResources().getString("ProgressTitle"));
	}
	
	private void setupView() {
		progressBar.setProgress(-1F);
		progressBar.setPrefSize(Double.MAX_VALUE, 30);
		progressBar.setPadding(new Insets(5));
		HBox.setHgrow(progressBar, Priority.ALWAYS);
		Label info = new Label(workspace.getResources().getString("ProgressContent"));
		VBox box = new VBox();
		box.setSpacing(10);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(progressBar, info);
		box.setPadding(new Insets(20));
		Scene scene = new Scene(box, 300, 80);
		scene.getStylesheets().add(workspace.getResources().getString("StylesheetPath"));
		stage.setScene(scene);
	}

	public Stage getDialogStage() {
		return stage;
	}

}
