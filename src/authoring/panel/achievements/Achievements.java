package authoring.panel.achievements;

import java.util.ArrayList;
import java.util.List;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.views.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Achievements extends View{
	
	private Workspace workspace;
	private ComponentMaker maker;
	private TextField achievementName;
	private TextArea description;
	private ComboBox<String> condition;
	private List<ComboBox<String>> conditions;
	private GridPane grid;

	public Achievements(Workspace workspace) {
		super(workspace.getResources().getString("AchievementsTitle"));
		this.workspace = workspace;
		maker = new ComponentMaker(workspace.getResources());
		initiateObjects();
		setup();
	}
	
	private void initiateObjects(){
		achievementName = new TextField();
		description = new TextArea();
		condition = new ComboBox<>();
		fillComboBox(condition);
		conditions = new ArrayList<>();
		conditions.add(condition);
		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(5);
	}
	
	private void setup(){
		ScrollPane scroll = new ScrollPane();
		VBox container = new VBox(15);
		scroll.setContent(container);
		
		scroll.setFitToWidth(true);
		
		Button plus = maker.makeButton("AchievementsPlus", e -> newParameter(), true);
		grid.add(condition, 0, conditions.size()-1, 1, 1);
		grid.add(plus, 1, 0, 1, 1);
		
		VBox name = labelBox("AchievementsNameLabel", achievementName);
		VBox description = labelBox("AchievementsDescriptionLabel", this.description);
		VBox parameter = labelBox("AchievementsParamLabel", grid);
		Button set = maker.makeButton("SetName", e -> setAchievement(), true);
		container.getChildren().addAll(name, description, parameter, set);
		setCenter(scroll);
	}
	
	private VBox labelBox(String title, Node object){
		VBox box = new VBox(5);
		Label label = new Label(workspace.getResources().getString(title));
		box.getChildren().addAll(label, object);
		
		return box;
	}
	
	private void setAchievement(){
		
	}
	
	private void newParameter(){
		ComboBox<String> param = new ComboBox<>();
		fillComboBox(param);
		conditions.add(param);
		grid.add(param, 0, conditions.size()-1, 1, 1);
	}
	
	private void fillComboBox(ComboBox<String> box){
		
	}


}
