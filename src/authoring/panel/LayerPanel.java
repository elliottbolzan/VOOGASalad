package authoring.panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import authoring.Workspace;
import authoring.components.CustomTooltip;
import utils.views.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import polyglot.Case;

/**
 * 
 * @author Mina Mungekar
 *
 */
public class LayerPanel extends View {

	private Workspace workspace;
	private ComboBox<String> myBox;
	private Map<String, ArrayList<String>> nameList;
	private ObservableList<String> selectionModel;

	public LayerPanel(Workspace workspace) {
		super(workspace.getPolyglot().get("LayerPanelTitle", Case.TITLE));
		this.workspace = workspace;
		setup();
	}

	private void setup() {
		getStyleClass().add("background");
		selectionModel = FXCollections.observableArrayList();
		nameList = new HashMap<String, ArrayList<String>>();
		myBox = new ComboBox<String>();
		selectionModel.add(workspace.getPolyglot().get("DefaultLayer").get());
		myBox.setItems(selectionModel);
		myBox.setValue(selectionModel.get(0));
		myBox.setPrefWidth(Double.MAX_VALUE);
		setupView();
	}

	private void setupView() {
		VBox container = new VBox(8);
		initLayerSelector();
		Button addButton = workspace.getMaker().makeButton("AddLayerButton", e -> addLayer(), true);
		new CustomTooltip(workspace.getPolyglot().get("AddLayer"), addButton);
		Button deleteButton = workspace.getMaker().makeButton("DeleteLayerButton", e -> {
			initCloseRequest(e);
			delete();
		}, true);
		new CustomTooltip(workspace.getPolyglot().get("DeleteLayer"), deleteButton);
		VBox buttonBox = new VBox(2);
		buttonBox.getChildren().addAll(deleteButton, addButton);
		container.getChildren().addAll(myBox, buttonBox);
		container.setPadding(new Insets(20));
		setCenter(container);
	}

	private void addLayer() {
		workspace.addLayer();
		myBox.getItems().add("Layer" + " " + (myBox.getItems().size() + 1));
		myBox.setValue("Layer" + " " + (myBox.getItems().size()));
	}

	private void initCloseRequest(Event e) {
		Alert alert = workspace.getMaker().makeAlert(AlertType.CONFIRMATION, "ConfirmationTitle", "ConfirmationHeader",
				workspace.getPolyglot().get("ConfirmationContent"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != ButtonType.OK) {
			e.consume();
		}
	}

	private void delete() {
		workspace.deleteLayer(myBox.getSelectionModel().getSelectedIndex() + 1);
		selectionModel.remove(myBox.getSelectionModel().getSelectedIndex());
		myBox.setItems(selectionModel);
		myBox.setValue(selectionModel.get(0));
	}

	private void initLayerSelector() {
		myBox.promptTextProperty().bind(workspace.getPolyglot().get("LayerBoxPrompt"));
		myBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					workspace.selectLayer(myBox.getSelectionModel().getSelectedIndex() + 1);
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
		nameList.put(oldLevel, new ArrayList<String>(selectionModel));
		if (oldLevel.equals("+")) {
			nameList.put(newLevel, new ArrayList<String>(selectionModel));
		}
		if (!newLevel.equals("+")) {
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
	public void selectLevelBox(List<String> loadedList) {
		myBox.getItems().clear();
		selectionModel = FXCollections.observableArrayList(loadedList);
		myBox.setItems(selectionModel);
		myBox.setValue(selectionModel.get(0));
	}

	public void selectLevelBox(int layerNum) {
		myBox.getItems().clear();
		for (int i = 0; i < layerNum; i++) {
			myBox.getItems().add(String.format("Layer %d", i + 1));
		}
		myBox.setValue(String.format("Layer %d", 1));
	}

}
