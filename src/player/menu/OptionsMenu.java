package player.menu;

import java.util.ResourceBundle;

import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.Action;
import engine.Entity;
import engine.Event;
import engine.Parameter;
import game_data.Game;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OptionsMenu extends AbstractMenu {

	private ScrollPane center;
	private Map<String, Parameter> keys;
	private Map<String, Parameter> keyReleases;
	private List<Entity> entities;
	private GridPane grid;
	private int count = 0;

	public OptionsMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, mediaManager, "OptionsTitle", polyglot, IOResources);
		setup();
	}

	private void setup() {
		center = new ScrollPane();
		center.setFitToWidth(true);
		keys = new HashMap<>();
		keyReleases = new HashMap<>();
		this.setCenter(center);
		loadKeyBindings();
		setupView();
	}

	private void setupView() {
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		center.setContent(grid);

		this.addHeading(getPolyglot().get("Controls"), count);
		count++;

		for (String s : keys.keySet()) {
			addControlRow(s, keys.get(s).getObject(), count);
			count++;
		}
		if (this.getLoader().getMediaPlayer() != null) {
			this.addHeading(getPolyglot().get("Audio"), count);
			count++;

			Slider volume = setupVolumeSlider();
			grid.add(new Label("Master Volume"), 0, count);
			grid.add(volume, 1, count);
			count++;
		}

	}

	private Slider setupVolumeSlider() {
		Slider volume = new Slider();
		MediaPlayer songPlayer = this.getLoader().getMediaPlayer();
		
		volume.setValue(songPlayer.getVolume());
		volume.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (volume.isValueChanging()) {
					songPlayer.setVolume(volume.getValue() / 100.0);
				}
			}
		});
		volume.setShowTickLabels(true);
		volume.setShowTickMarks(true);

		return volume;
	}

	private void loadKeyBindings() {
		entities = (List<Entity>) this.getGame().getLevels().get(0).getEntities();
		for (int i = 0; i < entities.size(); i++) {
			// Get all the events of each entity
			List<Event> events = entities.get(i).getEvents();
			for (int j = 0; j < events.size(); j++) {
				// Get parameters and actions for each event
				List<Parameter> params = events.get(j).getParams();
				List<Action> actions = events.get(j).getActions();

				for (int k = 0; k < params.size(); k++) {
					// Look for Key parameters
					if (params.get(k).getName().equals("Key")) {
						// If the action has no parameters then its a key
						// release event and will be ignored
						if (actions.get(k).getParams().size() > 0) {
							// name of action associated with key event
							String action = actions.get(k).getParams().get(0).getName();
							// value for the action
							String value = actions.get(k).getParams().get(0).getObject().toString();
							// The Parameter with key value
							Parameter key = params.get(k);
							String actionValue = action + " " + value;
							keys.put(actionValue, key);
						} else {
							// map key value to the parameter
							keyReleases.put(params.get(k).getObject().toString(), params.get(k));
						}
					}
				}
			}
		}
	}

	private void addControlRow(String action, Object key, int row) {
		Label actionLabel = new Label(action);
		TextField keyLabel = makeKeyLabel(key.toString(), actionLabel);
		grid.add(actionLabel, 0, row);
		grid.add(keyLabel, 1, row);
	}

	private void addHeading(StringBinding heading, int row) {
		Label title = new Label();
		title.textProperty().bind(heading);
		title.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
		grid.add(title, 0, row, 2, 1);
	}

	private TextField makeKeyLabel(String key, Label actionLabel) {
		TextField keyLabel = new TextField();
		keyLabel.setText(key);
		keyLabel.setEditable(false);
		keyLabel.setOnMouseClicked(e -> keyLabelAction(keyLabel, actionLabel));

		return keyLabel;
	}

	private void keyLabelAction(TextField key, Label actionLabel) {
		key.setEditable(true);
		key.setOnKeyPressed(e -> keyPressAction(e.getCode(), key, actionLabel));
		key.setEditable(false);
	}

	private void keyPressAction(KeyCode e, TextField key, Label actionLabel) {
		keys.get(actionLabel.getText()).setObject(e);
		// Change associated key releases
		if (keyReleases.get(key.getText()) != null) {
			keyReleases.get(key.getText()).setObject(e);
		}
		// this.getLoader().loadData().saveGame(this.getLoader().loadGame(),
		// this.getLoader().getGamePath());
		key.setText(e.toString());
	}

	@Override
	public void addElements() {
		// TODO Auto-generated method stub
		
	}

}
