package authoring.panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.components.LabeledField;
import authoring.views.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * 
 * @author Mina Mungekar
 *
 */
public class LayerPanel extends View {

	private Workspace workspace;
	private VBox editorContainer;
	private ComboBox<String> myBox;
	private ComponentMaker maker;
	private LabeledField nameField;
	private Map<String, ArrayList<String>> nameList;
	private ObservableList<String> selectionModel;

	public LayerPanel(Workspace workspace) {
		super(workspace.getResources().getString("LayerPanelTitle"));
		this.workspace = workspace;
		selectionModel = FXCollections.observableArrayList();
		nameList = new HashMap<String, ArrayList<String>>();
		maker = new ComponentMaker(workspace.getResources());
		myBox = new ComboBox<String>();
		selectionModel.add(workspace.getResources().getString("DefaultLayer"));
		//nameList.put(workspace.getResources().getString("DefaultLayer"), new ArrayList<String>(selectionModel));
		myBox.setItems(selectionModel);
		myBox.setValue(selectionModel.get(0));
		configureEditing();
	}

	private void configureEditing() {
		editorContainer = new VBox();
		editorContainer.setSpacing(Integer.parseInt(workspace.getResources().getString("SettingsSpacing")));
		Button addLayerButton = maker.makeButton("AddLayerButton", e -> addLayer(), true);
		Button deleteLayerButton = maker.makeButton("DeleteLayerButton", e -> {
			initCloseRequest(e);
			delete();
		}, true);
		initLayerSelector();
		configureVelocitySettings();
		editorContainer.getChildren().addAll(addLayerButton, deleteLayerButton);
		setCenter(editorContainer);
	}

	private void addLayer(){
	workspace.addLayer();
	myBox.getItems().add("Layer" + " "+ (myBox.getItems().size()+1));
	System.out.println(selectionModel);
	myBox.setValue("Layer" + " "+ (myBox.getItems().size()));
	}
	
	private void initCloseRequest(Event e) {
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		String message = workspace.getResources().getString("ConfirmationContent");
		Alert alert = maker.makeAlert(AlertType.CONFIRMATION, "ConfirmationTitle", "ConfirmationHeader", message);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != ButtonType.OK) {
			e.consume();
		}
	}

	private void delete() {
		//int layer = Integer.parseInt(myBox.getSelectionModel().getSelectedItem().split(" ")[1]);
		workspace.deleteLayer(myBox.getSelectionModel().getSelectedIndex()+1);
		selectionModel.remove(myBox.getSelectionModel().getSelectedIndex());
		myBox.setItems(selectionModel);
		myBox.setValue(selectionModel.get(0));
	}

	private void configureVelocitySettings() {
		Slider velocitySlider = new Slider() {
			{
				setMin(0);
				setMax(100);
				setValue(0);
			}
		};

		HBox sliderBox = new HBox() {
			{
				setSpacing(Integer.parseInt(workspace.getResources().getString("SettingsSpacing")));
				Text t = new Text(workspace.getResources().getString("LayerSpeedPrompt"));
				t.setFill(Color.WHITE);
				getChildren().addAll(t, velocitySlider);
			}
		};

		Button velocityButton = maker.makeButton("SaveLayerSpeed", null, true);
		createNameField();
		editorContainer.getChildren().addAll(sliderBox, velocityButton);
	}

private void createNameField(){
	
	HBox nameFieldBox = new HBox() {
		{
			setSpacing(Integer.parseInt(workspace.getResources().getString("SettingsSpacing")));
			nameField = new LabeledField(workspace, "LayerPrompt", null, true);
			getChildren().addAll(nameField);
		}
	};
	Button nameButton = maker.makeButton("LayerSave", e->saveName(), true);
	editorContainer.getChildren().addAll(myBox, nameFieldBox,nameButton); 
}

private void saveName(){
	if(nameField.getText()!=null){
	selectionModel.set(myBox.getSelectionModel().getSelectedIndex(), nameField.getText());
	myBox.setItems(selectionModel);
	myBox.setValue(nameField.getText());
	nameField.setText(null);
	}
}

	private void initLayerSelector() {
		myBox.setPromptText(workspace.getResources().getString("LayerBoxPrompt"));
		myBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					workspace.selectLayer(myBox.getSelectionModel().getSelectedIndex()+1);
				}
			}
		});
	}

	/**
	 * selectLevelBox is called whenever levels are switched and the set of
	 * layers changes. The new level reports its new layer set to the workspace,
	 * which, in turn passes the new layer count to the combobox.
	 * 
	 * @param layerNum
	 */

	public void selectLevelBox(String oldLevel, String newLevel) {
		if(oldLevel.equals("+")){
			selectionModel.clear();
			selectionModel.add(workspace.getResources().getString("DefaultLayer"));
			nameList.put(newLevel, new ArrayList<String>(selectionModel));
		}
		nameList.put(oldLevel,new ArrayList<String>(selectionModel));
		if(!newLevel.equals("+")){
			selectionModel = FXCollections.observableArrayList(nameList.get(newLevel));
			}
		myBox.setItems(selectionModel);
		myBox.setValue(selectionModel.get(0));
		}
	
	/**
	 * selectLevelBox is called whenever levels are switched and the set of
	 * layers changes. The new level reports its new layer set to the workspace,
	 * which, in turn passes the new layer count to the combobox.
	 * 
	 * @param layerNum
	 */
	public void selectLevelBox(int layerNum) {
		myBox.getItems().clear();
		for (int i = 0; i < layerNum; i++) {
			myBox.getItems().add(String.format("Layer %d", i + 1));
		}
		myBox.setValue(String.format("Layer %d", 1));
	}
		
	}

