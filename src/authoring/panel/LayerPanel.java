package authoring.panel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.views.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class LayerPanel extends View
{

	private Workspace workspace;
	private VBox editorContainer;
	private ComboBox myBox;
	private Map<Integer, List<String>> layerOptions;
	private ComponentMaker maker;

	public LayerPanel(Workspace workspace)
	{
		super(workspace.getResources().getString("LayerPanelTitle"));
		this.workspace = workspace;
		maker = new ComponentMaker(workspace.getResources());
		layerOptions = new HashMap<Integer, List<String>>();
		myBox = new ComboBox();
		configureEditing();
	}

	private void configureEditing()
	{
		editorContainer = new VBox();
		editorContainer.setSpacing(Integer.parseInt(workspace.getResources().getString("SettingsSpacing")));
		Button addLayerButton = maker.makeButton("AddLayerButton", e -> workspace.addLayer(), true);
		Button deleteLayerButton = maker.makeButton("DeleteLayerButton", e -> {
			initCloseRequest(e);
			delete();
			}, true);
		initLayerSelector();
		configureVelocitySettings();
		editorContainer.getChildren().addAll(addLayerButton, deleteLayerButton);
		setCenter(editorContainer);
	}
	
	private void initCloseRequest(Event e)
	{
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		String message = workspace.getResources().getString("ConfirmationContent");
		Alert alert = maker.makeAlert(AlertType.CONFIRMATION, "ConfirmationTitle", "ConfirmationHeader", message);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != ButtonType.OK) {
			e.consume();
		}
	}

	private void delete()
	{
		int layer = Integer.parseInt(((String) myBox.getSelectionModel().getSelectedItem()).split(" ")[1]);
		myBox.setValue((layer == 1 ? String.format("Layer %d", layer) : String.format("Layer %d", layer - 1)));
		workspace.deleteLayer(layer);
	}

	private void configureVelocitySettings()
	{
		Slider velocitySlider = new Slider()
		{
			{
				setMin(0);
				setMax(100);
				setValue(0);
			}
		};

		HBox sliderBox = new HBox()
		{
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

	public void updateBox(String newLayer)
	{
		myBox.getItems().add(newLayer);
		// myBox.setValue(newLayer);
	}

	private void initLayerSelector()
	{
		myBox.setPromptText(workspace.getResources().getString("LayerBoxPrompt"));
		myBox.valueProperty().addListener(new ChangeListener()
		{

			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2)
			{
				if (arg2 != null) {
					workspace.selectLayer(Integer.parseInt(((String) arg2).split(" ")[1]));
				}
			}
		});
	}

	public void selectLevelBox(int layerNum)
	{
		myBox.getItems().clear();
		for (int i = 0; i < layerNum; i++) {
			myBox.getItems().add(String.format("Layer %d", i + 1));
		}
	}

}