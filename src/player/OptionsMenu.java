package player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.Action;
import engine.Entity;
import engine.Event;
import engine.Parameter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OptionsMenu extends AbstractMenu{
	private ScrollPane center;
	private Map<String, Parameter> keys;
	private Map<String, Parameter> keyReleases;
	private GridPane grid;
	private int count = 0;
	
	public OptionsMenu(Stage stage, Loader loader){
		super(stage, loader, "OptionsTitle");
		setup();
	}

	private void setup(){
		center = new ScrollPane();
		center.setFitToWidth(true);
		keys = new HashMap<>();
		keyReleases = new HashMap<>();
		this.setCenter(center);
		
		loadKeyBindings();
		setupView();
	}
	
	private void setupView(){
		StackPane container = new StackPane();
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		center.setContent(grid);
		
		for(String s : keys.keySet()){
			addRow(s, keys.get(s).getObject(), count);
			count++;
		}
		container.getChildren().add(grid);
		center.setContent(container);
	}
	
	private void loadKeyBindings(){
		List<Entity> entities = (List<Entity>) this.getLoader().loadGame().getLevels().get(0).getEntities();
		for(int i = 0; i < entities.size(); i++){
			//Get all the events of each entity
			List<Event> events = entities.get(i).getEvents();
			for(int j = 0; j < events.size(); j++){
				//Get parameters and actions for each event
				List<Parameter> params = events.get(j).getParams();
				List<Action> actions = events.get(j).getActions();
				
				for(int k = 0; k < params.size(); k++){
					//Look for Key parameters
					if(params.get(k).getName().equals("Key")){
						//If the action has no parameters then its a key release event and will be ignored
						if(actions.get(k).getParams().size() > 0){
							//name of action associated with key event
							String action = actions.get(k).getParams().get(0).getName();
							//value for the action
							String value = actions.get(k).getParams().get(0).getObject().toString();
							//The Parameter with key value
							Parameter key = params.get(k);
							String actionValue = action+" "+value;
							keys.put(actionValue, key);
						}else{
							//map key value to the parameter
							keyReleases.put(params.get(k).getObject().toString(), params.get(k));
						}					
					}
				}				
			}
		}
	}
	
	private void addRow(String action, Object key, int row){
		Label actionLabel = new Label(action);
		TextField keyLabel = makeKeyLabel(key.toString(), actionLabel);
		grid.add(actionLabel, 0, row);
		grid.add(keyLabel, 1, row);
	}
	
	private TextField makeKeyLabel(String key, Label actionLabel){
		TextField keyLabel = new TextField();
		keyLabel.setText(key);
		keyLabel.setEditable(false);
		keyLabel.setOnMouseClicked(e -> keyLabelAction(keyLabel, actionLabel));
		
		return keyLabel;
	}
	
	private void keyLabelAction(TextField key, Label actionLabel){
		key.setEditable(true);
		key.setOnKeyPressed(e -> keyPressAction(e.getCode(), key, actionLabel));
		key.setEditable(false);
	}
	
	private void keyPressAction(KeyCode e, TextField key, Label actionLabel){
		keys.get(actionLabel.getText()).setObject(e);
		//Change associated key releases
		if(keyReleases.get(key.getText()) != null){
			keyReleases.get(key.getText()).setObject(e);
		}
		key.setText(e.toString());
	}

}
