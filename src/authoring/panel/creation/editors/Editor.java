package authoring.panel.creation.editors;

import java.util.ArrayList;
import java.util.List;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.components.EditingCell;
import authoring.views.View;
import engine.GameObject;
import engine.Parameter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Elliott Bolzan
 *
 */
public abstract class Editor extends View {

	private Workspace workspace;
	private ComboBox<String> comboBox;
	private Label description;
	private List<String> elements;
	private List<Parameter> parameters = new ArrayList<Parameter>();
	private TableView<Parameter> table;
	private boolean showSave;
	private ComponentMaker maker;

	/**
	 * @param title
	 */
	public Editor(Workspace workspace, List<String> elements, GameObject object, boolean showSave) {
		super("");
		this.workspace = workspace;
		this.elements = elements;
		this.showSave = showSave;
		maker = new ComponentMaker(workspace.getResources());
		setupView(object);
		if (object != null)
			update(object);
	}

	private void setupView(GameObject object) {
		setPadding(new Insets(10));
		createChooserBox(object);
		createTable(object);
		if (showSave) {
			createButtonBox();
		}
	}

	private void createChooserBox(GameObject object) {
		comboBox = new ComboBox<String>();
		comboBox.getItems().addAll(elements);
		comboBox.setOnAction((e) -> selected(comboBox.getSelectionModel().getSelectedItem()));
		comboBox.prefWidthProperty().bind(widthProperty());
		if (object == null) {
			comboBox.setValue(workspace.getResources().getString("Type"));
		} else {
			comboBox.setValue(object.getDisplayName());
		}
		description = new Label();
		description.setWrapText(true);
		description.textProperty().addListener(e -> descriptionChanged());
		setTop(new VBox(comboBox, description));
	}

	private void descriptionChanged() {
		description.setPadding(new Insets(description.getText().equals("") ? 0 : 8));
	}

	@SuppressWarnings("unchecked")
	private void createTable(GameObject object) {
		table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPlaceholder(new Label(workspace.getResources().getString("NoParameters")));
		table.prefHeightProperty().bind(heightProperty());
		table.setEditable(true);
		ObservableList<Parameter> items = FXCollections.observableArrayList(parameters);
		table.setItems(items);
		table.getColumns().setAll(makeKeyColumn(), makeValueColumn());
		if (object != null) {
			table.setItems(FXCollections.observableArrayList(object.getParams()));
		}
		setCenter(table);
	}

	private TableColumn<Parameter, Object> makeKeyColumn() {
		TableColumn<Parameter, Object> column = new TableColumn<>("Key");
		column.setCellValueFactory(new PropertyValueFactory<Parameter, Object>("name"));
		return column;
	}

	private TableColumn<Parameter, Object> makeValueColumn() {
		TableColumn<Parameter, Object> column = new TableColumn<>("Value");
		column.setCellValueFactory(new PropertyValueFactory<Parameter, Object>("object"));
		column.setCellFactory(new Callback<TableColumn<Parameter, Object>, TableCell<Parameter, Object>>() {
			@Override
			public TableCell<Parameter, Object> call(TableColumn<Parameter, Object> param) {
				return new EditingCell(workspace);
			}
		});
		column.setOnEditCommit(new EventHandler<CellEditEvent<Parameter, Object>>() {
			@Override
			public void handle(CellEditEvent<Parameter, Object> t) {
				int row = t.getTablePosition().getRow();
				Parameter parameter = (Parameter) t.getTableView().getItems().get(row);
				parameter.setObject(t.getNewValue());
				addToParameters(parameter);
			}
		});
		column.setEditable(true);
		return column;
	}

	private void createButtonBox() {
		Button saveButton = maker.makeButton("TableEditorSaveButton", e -> save(), true);
		setBottom(saveButton);
	}

	protected void update(GameObject object) {
		description.setText(object.getDisplayDescription());
		parameters = object.getParams();
		table.setItems(FXCollections.observableArrayList(parameters));
	}

	private void save() {
		((Stage) getScene().getWindow()).close();
		save(parameters);
	}

	private void addToParameters(Parameter updatedParameter) {
		parameters.removeIf(item -> item.getName().equals(updatedParameter.getName()));
		parameters.add(updatedParameter);
	}

	public abstract void selected(String string);

	public abstract void save(List<Parameter> data);

}
