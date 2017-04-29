package authoring.menu;

import java.io.File;
import java.util.Optional;

import authoring.Workspace;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

/**
 * @author Elliott Bolzan
 *
 */
public class SettingsMenu extends WorkspaceMenu {

	/**
	 * @param workspace
	 */
	public SettingsMenu(Workspace workspace) {
		super(workspace);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see authoring.menu.WorkspaceMenu#setTitle()
	 */
	@Override
	protected void setTitle() {
		setTitle("SettingsMenu");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see authoring.menu.WorkspaceMenu#setItems()
	 */
	@Override
	protected void setItems() {
		MenuItem musicItem = getMaker().makeMenuItem("MusicSelect", e -> chooseSong(), true);
		CheckMenuItem directionItem = getMaker().makeCheckItem("Direction", e -> setTimeDirection(),
				getWorkspace().getGame().getClockGoingDown());
		MenuItem timeItem = getMaker().makeMenuItem("Time", e -> setMaxTime(), true);
		getItems().addAll(musicItem, new SeparatorMenuItem(), directionItem, timeItem);
	}

	private void chooseSong() {
		String directory = System.getProperty("user.dir") + getIOResources().getString("DefaultDirectory");
		FileChooser chooser = getMaker().makeFileChooser(directory, getPolyglot().get("MusicChooserTitle").get(),
				getIOResources().getString("MusicChooserExtensions"));
		File selectedFile = chooser.showOpenDialog(getWorkspace().getScene().getWindow());
		if (selectedFile != null) {
			getWorkspace().getGame().setSongPath(selectedFile.getAbsolutePath());
		}
	}

	private void setTimeDirection() {
		getWorkspace().getGame().setClockGoingDown(!getWorkspace().getGame().getClockGoingDown());
	}

	private void setMaxTime() {
		TextInputDialog dialog = getMaker().makeTextInputDialog("TimeTitle", "TimeHeader", "TimePrompt",
				Double.toString(getWorkspace().getGame().getCurrentTime()));
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				double time = Double.parseDouble(result.get());
				getWorkspace().getGame().setCurrentTime(time);
			} catch (Exception e) {
				getMaker().showFailure();
			}
		}
	}

}
