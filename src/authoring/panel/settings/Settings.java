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
	private LabeledField imageField;

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
		VBox musicBox = buttonBox("MusicSelect", e -> pickMedia(songField, getResource("MusicChooserTitle"), getResource("MusicChooserExtensions")), songField);
		
		imageField = new LabeledField(workspace, "BackgroundLabel", "", false);
		VBox imageBox = buttonBox("MusicSelect", e -> pickMedia(imageField, getResource("ImageChooserTitle"), 
				getResource("ImageChooserExtension1"), getResource("ImageChooserExtension2")), imageField);
		
		settingsContainer.getChildren().addAll(nameBox, new Separator(), musicBox, new Separator(), imageBox);
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
	
	private void pickMedia(LabeledField field, String extensionName, String... type){
		String directory = System.getProperty("user.dir") + workspace.getResources().getString("DefaultDirectory");
		FileChooser chooser = maker.makeFileChooser(directory, extensionName, type);
		File selectedFile = chooser.showOpenDialog(getScene().getWindow());
		if (selectedFile != null) {
			String absolute = selectedFile.getAbsolutePath();
			field.setText(fileName(absolute));
			if(getResource(extensionName).equals("MusicChooserTitle")){		
				workspace.getGame().setSongPath(absolute);
			}else{
				//workspace.getGame().setBackgroundPath(absolute);
			}
		}
	}

	private String fileName(String path) {
		return Paths.get(path).getFileName().toString();
	}
	
	private String getResource(String input){
		return workspace.getResources().getString(input);
	}
	
}
