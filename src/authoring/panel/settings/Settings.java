package authoring.panel.settings;

import java.io.File;

import authoring.Workspace;
import utils.views.View;
import javafx.stage.FileChooser;
import polyglot.Case;

/**
 * The Settings sub-panel provides the user with the option of selecting his or
 * her own background music as well as saving the entire game and sending it to
 * the Game Data Module
 * 
 * Some code adapted from:
 * http://www.java2s.com/Tutorials/Java/JavaFX/0340__JavaFX_GridPane.htm.
 * 
 * @author Mina
 *
 */
public class Settings extends View {

	private Workspace workspace;

	/**
	 * The constructor needs a parent workspace specified. The rest of the
	 * constructor is inherited from its superclass. The constructor immediately
	 * instantiates all the buttons necessary.
	 * 
	 * @param workspace
	 */
	public Settings(Workspace workspace) {
		super(workspace.getPolyglot().get("SettingsTitle", Case.TITLE));
		this.workspace = workspace;
		//setup();
	}

	/*private void setup() {
		GridPane grid = createGrid();
		createSongSection(grid);
		createImageSection(grid);
		setCenter(grid);
	}*/

	/*
	 * private void createSongSection(GridPane grid) { Label songLabel = new
	 * Label();
	 * songLabel.textProperty().bind(workspace.getPolyglot().get("SongLabel"));
	 * songField = new TextField(fileName(workspace.getGame().getSongPath()));
	 * songField.setEditable(false); Button songButton =
	 * workspace.getMaker().makeButton("MusicSelect", e -> pickMedia(songField,
	 * getString("MusicChooserTitle"), getResource("MusicChooserExtensions")),
	 * true); GridPane.setHalignment(songLabel, HPos.LEFT); grid.add(songLabel,
	 * 0, 0); GridPane.setHalignment(songField, HPos.RIGHT); grid.add(songField,
	 * 1, 0); GridPane.setHalignment(songButton, HPos.RIGHT);
	 * grid.add(songButton, 2, 0); }
	 * 
	 * private void createImageSection(GridPane grid) { Label imageLabel = new
	 * Label(); imageLabel.textProperty().bind(workspace.getPolyglot().get(
	 * "BackgroundLabel")); imageField = new TextField();
	 * imageField.setEditable(false); Button imageButton =
	 * workspace.getMaker().makeButton("MusicSelect", e -> pickMedia(imageField,
	 * getString("ImageChooserTitle"), getResource("ImageChooserExtension1"),
	 * getResource("ImageChooserExtension2")), true);
	 * GridPane.setHalignment(imageLabel, HPos.LEFT); grid.add(imageLabel, 0,
	 * 1); GridPane.setHalignment(imageField, HPos.RIGHT); grid.add(imageField,
	 * 1, 1); GridPane.setHalignment(imageButton, HPos.RIGHT);
	 * grid.add(imageButton, 2, 1); }
	 * 
	 * private GridPane createGrid() { GridPane grid = new GridPane();
	 * grid.setPadding(new Insets(20)); grid.setHgap(5); grid.setVgap(5);
	 * ColumnConstraints column1 = new ColumnConstraints(70); ColumnConstraints
	 * column2 = new ColumnConstraints(50, 150, 300); ColumnConstraints column3
	 * = new ColumnConstraints(70); column2.setHgrow(Priority.ALWAYS);
	 * grid.getColumnConstraints().addAll(column1, column2, column3); return
	 * grid; }
	 * 
	 * public void load(Game game) {
	 * songField.setText(fileName(game.getSongPath())); }
	 */

	/*
	 * private String fileName(String path) { return
	 * Paths.get(path).getFileName().toString(); }
	 */

}
