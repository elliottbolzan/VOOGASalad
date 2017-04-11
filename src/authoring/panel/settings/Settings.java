package authoring.panel.settings;

import java.io.File;
import java.nio.file.Paths;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.components.LabeledField;
import authoring.views.View;
import game_data.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * The Settings sub-panel provides the user with the option of selecting his or
 * her own background music as well as saving the entire game and sending it to
 * the Game Data Module
 * 
 * @author Mina
 *
 */
public class Settings extends View {

	private Workspace workspace;
	private ComponentMaker maker;
	private LabeledField nameField;
	private LabeledField songField;

	/**
	 * The constructor needs a parent workspace specified. The rest of the
	 * constructor is inherited from its superclass. The constructor immediately
	 * instantiates all the buttons necessary.
	 * 
	 * @param workspace
	 */
	public Settings(Workspace workspace) {
		super(workspace.getResources().getString("SettingsTitle"));
		this.workspace = workspace;
		setup();
	}

	private void setup() {
		setPadding(new Insets(20, 10, 20, 10));
		maker = new ComponentMaker(workspace.getResources());
		VBox settingsContainer = new VBox(Integer.parseInt(workspace.getResources().getString("SettingsSpacing")));
		
		nameField = new LabeledField(workspace, "GameNameLabel", workspace.getGame().getName(), true);
		VBox nameBox = buttonBox("SetName", e -> saveName(), nameField);
		
		songField = new LabeledField(workspace, "SongLabel", fileName(workspace.getGame().getSongPath()),
				false);
		VBox musicBox = buttonBox("MusicSelect", e -> pickSong(), songField);
		
		settingsContainer.getChildren().addAll(nameBox, new Separator(), musicBox);
		setCenter(settingsContainer);
	}
	
	public void load(Game game) {
		nameField.setText(game.getName());
		songField.setText(fileName(game.getSongPath()));
	}
	
	private VBox buttonBox(String buttonProperty, EventHandler<ActionEvent> handler, LabeledField field) {
		Button button = maker.makeButton(buttonProperty, handler, true);
		VBox box = new VBox(field, button);
		box.setSpacing(5);
		return box;
	}
	
	private void saveName() {
		workspace.getGame().setName(nameField.getText());
	}

	private void pickSong() {
		String directory = System.getProperty("user.dir") + workspace.getResources().getString("DefaultDirectory");
		FileChooser chooser = maker.makeFileChooser(directory, workspace.getResources().getString("MusicChooserTitle"),
				workspace.getResources().getString("MusicChooserExtensions"));
		File selectedFile = chooser.showOpenDialog(getScene().getWindow());
		if (selectedFile != null) {
			String absolute = selectedFile.getAbsolutePath();
			songField.setText(fileName(absolute));
			workspace.getGame().setSongPath(absolute);
		}
	}

	private String fileName(String path) {
		return Paths.get(path).getFileName().toString();
	}
	
}
