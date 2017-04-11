package authoring.panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import authoring.Workspace;
import authoring.components.ComponentMaker;
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
	private Map<Integer, List<String>> layerOptions;
	private ComponentMaker maker;
	private ObservableList<String> layerNames;

	public LayerPanel(Workspace workspace) {
		super(workspace.getResources().getString("LayerPanelTitle"));
		this.workspace = workspace;
		maker = new ComponentMaker(workspace.getResources());
		layerOptions = new HashMap<Integer, List<String>>();
		myBox = new ComboBox<String>();
		layerNames = FXCollections.observableArrayList(new ArrayList<>());
		configureEditing();
	}

	private void configureEditing() {
		editorContainer = new VBox();
		editorContainer.setSpacing(Integer.parseInt(workspace.getResources().getString("SettingsSpacing")));
		Button addLayerButton = maker.makeButton("AddLayerButton", e -> addLayer(), true);
		Button deleteLayerButton = maker.makeButton("DeleteLayerButton", e -> removeLayer(e), true);
		//initLayerSelector();
		createComboBox();
		configureVelocitySettings();
		editorContainer.getChildren().addAll(addLayerButton, deleteLayerButton);
		setCenter(editorContainer);
	}
	
	private void createComboBox() {
		myBox.setItems(layerNames);
	}
	
	private void addLayer() {
		layerNames.add("Layer" + Integer.toString(layerNames.size()));
		//workspace.addLayer();
	}
	
	private void removeLayer(Event e) {
		layerNames.remove(myBox.getSelectionModel().getSelectedItem());
		initCloseRequest(e);
		delete();
	}
	
	private void selectedLayer(String layerName) {
		//workspace.selectLayer(Integer.parseInt(((String) layerName).split(" ")[1]));
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
		int layer = Integer.parseInt(((String) myBox.getSelectionModel().getSelectedItem()).split(" ")[1]);
		//myBox.setValue((layer == 1 ? String.format("Layer %d", layer) : /*String.format("Layer %d", layer - 1)*/  ));
		//workspace.deleteLayer(layer);
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
		editorContainer.getChildren().addAll(myBox, sliderBox, velocityButton);
	}

	/*public void updateBox(String newLayer) {
		myBox.getItems().add(newLayer);
		myBox.setValue(newLayer);
	}*/

	/*private void initLayerSelector() {
		System.out.println("init layer selector");
		myBox.setPromptText(workspace.getResources().getString("LayerBoxPrompt"));
		myBox.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				if (arg2 != null) {
					workspace.selectLayer(Integer.parseInt(((String) arg2).split(" ")[1]));
				}
			}
		});
	}*/

	/*public void selectLevelBox(int layerNum) {
		myBox.getItems().clear();
		for (int i = 0; i < layerNum; i++) {
			myBox.getItems().add(String.format("Layer %d", i + 1));
		}
	}*/

}