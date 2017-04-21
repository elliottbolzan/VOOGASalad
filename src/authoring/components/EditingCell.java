package authoring.components;

import authoring.Workspace;
import engine.Parameter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Elliott Bolzan
 * 
 *         A custom TableView cell used for displaying and editing both normal
 *         input (Strings, Numbers) and more complex inputs, like KeyCodes.
 *         Could be extended to provide different inputs, eventually.
 *
 */
public class EditingCell extends TableCell<Parameter, Object> {

	private Workspace workspace;
	private TextField textField;
	private KeyCodeField keyCodeField;
	private String invalidEdit;

	/**
	 * Creates an EditingCell.
	 * 
	 * @param workspace
	 *            the workspace that owns the Cell.
	 */
	public EditingCell(Workspace workspace) {
		this.workspace = workspace;
		invalidEdit = workspace.getPolyglot().get("InvalidEdit").get();
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			if (getItem() == null || getItem() instanceof KeyCode) {
				createKeyCodeField();
				setGraphic(keyCodeField);
				keyCodeField.selectAll();
				keyCodeField.requestFocus();
			} else {
				createTextField();
				setGraphic(textField);
				textField.selectAll();
			}
			setText(null);
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getItem() == null ? "" : getItem().toString());
		setGraphic(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
	 */
	@Override
	public void updateItem(Object item, boolean empty) {
		super.updateItem(item, empty);
		if (isEditing()) {
			if (getItem() instanceof KeyCode) {
				if (keyCodeField != null) {
					keyCodeField.setText(getKeyCode().toString());
				}
				setGraphic(keyCodeField);
			} else {
				if (textField != null) {
					textField.setText(getString());
				}
				setGraphic(textField);
			}
			setText(null);
		} else {
			if (getItem() instanceof KeyCode)
				setStyle("-fx-font-weight: bold;");
			else
				setStyle("-fx-font-weight: normal;");
			setText(getString());
			setGraphic(null);
		}
	}

	private void createTextField() {
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					validateInput();
				}
			}
		});
		textField.setOnKeyPressed(e -> keyPressed(e));
	}

	private void createKeyCodeField() {
		keyCodeField = new KeyCodeField();
		keyCodeField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		keyCodeField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					commitEdit(keyCodeField.getKeyCode());
				}
			}
		});
	}

	private void keyPressed(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			textField.parentProperty().get().requestFocus();
		}
	}

	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}

	/**
	 * @return the current KeyCode for the cell.
	 */
	public KeyCode getKeyCode() {
		return getItem() == null ? null : (KeyCode) getItem();
	}

	private void validateInput() {
		Parameter param = (Parameter) getTableRow().getItem();
		String input = textField.getText();
		try {
			if (param.getParameterClass().equals(Integer.class) || param.getParameterClass().equals(int.class)) {
				commitEdit(Integer.parseInt(input));
			} else if (param.getParameterClass().equals(Double.class)
					|| param.getParameterClass().equals(double.class)) {
				commitEdit(Double.parseDouble(input));
			} else if (param.getParameterClass().equals(Boolean.class)
					|| param.getParameterClass().equals(boolean.class)) {
				commitEdit(Boolean.parseBoolean(input));
			}
			commitEdit(input);
		} catch (Exception e) {
			String content = String.format(invalidEdit, param.getParameterClass().getSimpleName());
			Alert alert = workspace.getMaker().makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", content);
			alert.show();
		}
	}

}
