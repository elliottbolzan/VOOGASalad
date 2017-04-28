package authoring.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import utils.views.View;
import javafx.beans.binding.StringBinding;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import polyglot.Case;
import polyglot.Polyglot;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Elliott Bolzan
 *
 *         A class that creates default GUI components. Currently, the class can
 *         create the following items: file choosers, directory choosers, text
 *         input dialogs, alerts, accordions, buttons, and tab buttons.
 */
public class ComponentMaker {

	private Polyglot polyglot;
	private ResourceBundle IOResources;
	private String stylesheetPath;

	/**
	 * Returns a Factory.
	 * 
	 * @param resources
	 *            the ResourceBundle that the Factory makes use of.
	 */
	public ComponentMaker(Polyglot polyglot, ResourceBundle IOResources) {
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		this.stylesheetPath = IOResources.getString("StylesheetPath");
	}

	/**
	 * @param path
	 *            the default path for the FileChooser.
	 * @param extensionName
	 *            the name of the type of file being picked.
	 * @param types
	 *            the types of file that can be picked.
	 * @return a FileChooser.
	 */
	public FileChooser makeFileChooser(String path, String extensionName, String... types) {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File(path));
		chooser.getExtensionFilters().setAll(new ExtensionFilter(extensionName, types));
		return chooser;
	}

	/**
	 * @param path
	 *            the default path for the DirectoryChooser.
	 * @param titleProperty
	 *            the property that provides the title for the chooser.
	 * @return a DirectoryChooser.
	 */
	public DirectoryChooser makeDirectoryChooser(String path, String titleProperty) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.titleProperty().bind(polyglot.get(titleProperty));
		chooser.setInitialDirectory(new File(path));
		return chooser;
	}

	/**
	 * @param titleProperty
	 *            the property that provides the title for the dialog.
	 * @param headerProperty
	 *            the property that provides the header for the dialog.
	 * @param contentProperty
	 *            the property that provides the content for the dialog.
	 * @param placeholderProperty
	 *            the property that provides a placeholder for the dialog.
	 * @return a TextInputDialog.
	 */
	public TextInputDialog makeTextInputDialog(String titleProperty, String headerProperty, String promptProperty,
			String content) {
		TextInputDialog dialog = new TextInputDialog(content);
		dialog.titleProperty().bind(polyglot.get(titleProperty, Case.TITLE));
		dialog.headerTextProperty().bind(polyglot.get(headerProperty));
		dialog.contentTextProperty().bind(polyglot.get(promptProperty));
		return dialog;
	}

	/**
	 * @param type
	 *            the type of Alert that needs to be created.
	 * @param titleProperty
	 *            the property that provides the title for the Alert.
	 * @param headerProperty
	 *            the property that provides the header for the Alert.
	 * @param content
	 *            the property that provides the content for the Alert.
	 * @return the Alert.
	 */
	public Alert makeAlert(AlertType type, String titleProperty, String headerProperty, String content) {
		Alert alert = alertHelper(type, titleProperty, headerProperty);
		alert.setContentText(content);
		return alert;
	}

	public Alert makeAlert(AlertType type, String titleProperty, String headerProperty, StringBinding content) {
		Alert alert = alertHelper(type, titleProperty, headerProperty);
		alert.contentTextProperty().bind(content);
		return alert;
	}

	private Alert alertHelper(AlertType type, String titleProperty, String headerProperty) {
		Alert alert = new Alert(type);
		alert.titleProperty().bind(polyglot.get(titleProperty, Case.TITLE));
		alert.headerTextProperty().bind(polyglot.get(headerProperty));
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(stylesheetPath);
		return alert;
	}

	/**
	 * @param subviews
	 *            the subviews that are to be added to the Accordion.
	 * @param subviewTitles
	 *            the titles of the subviews that are added to the Accordion.
	 * @return an Accordion.
	 */
	public Accordion makeAccordion(List<View> subviews) {
		Accordion accordion = new Accordion();
		List<TitledPane> titledPanes = new ArrayList<TitledPane>();
		for (int i = 0; i < subviews.size(); i++) {
			Label infoLabel = new Label("?");
			TitledPane pane = new TitledPane();
			pane.setContentDisplay(ContentDisplay.RIGHT);
			pane.setGraphic(infoLabel);
			pane.textProperty().bind(subviews.get(i).getTitle());
			Text t = new Text(pane.getText());

			pane.setGraphicTextGap(188 - t.getBoundsInLocal().getWidth());
			pane.setContent(subviews.get(i));
			titledPanes.add(pane);
		}
		accordion.getPanes().addAll(titledPanes);
		accordion.setExpandedPane(titledPanes.get(0));
		return accordion;
	}

	public void setToolTips(Accordion accordion, List<StringBinding> nameList) {
		for (int i = 0; i < nameList.size(); i++) {
			new CustomTooltip(nameList.get(i), accordion.getPanes().get(i).getGraphic());
		}
	}

	/**
	 * @param property
	 *            the property that provides the title of the Button.
	 * @param handler
	 *            the handler executed when the button is clicked.
	 * @param fill
	 *            whether the button should fill its available space.
	 * @return a Button.
	 */
	public Button makeButton(String property, EventHandler<ActionEvent> handler, boolean fill) {
		Button button = new Button();
		button.textProperty().bind(polyglot.get(property, Case.TITLE));
		button.setOnAction(handler);
		if (fill) {
			HBox.setHgrow(button, Priority.ALWAYS);
			button.setMaxWidth(Double.MAX_VALUE);
		}
		return button;
	}

	/**
	 * @param path
	 *            the path to the image for the tab button.
	 * @param action
	 *            the action to be executed when the tab button is clicked.
	 * @param style
	 *            the class of the tab button in the CSS stylesheet.
	 * @param width
	 *            the width of the Button.
	 * @param height
	 *            the height of the Button.
	 * @return the tab button.
	 */
	public Button makeTabButton(String path, EventHandler<ActionEvent> action, String style, int width, int height) {
		ImageView imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream(path), width, height, true, true));
		Button button = new Button("", imageView);
		button.setOnAction(action);
		button.getStyleClass().add(style);
		return button;
	}

	/**
	 * @param property
	 *            the property that provides the title of the Button.
	 * @param handler
	 *            the handler executed when the button is clicked.
	 * @param fill
	 *            whether the button should fill its available space.
	 * @return a Button.
	 */
	public Button makeImageButton(String property, ImageView image, EventHandler<ActionEvent> handler, boolean fill) {
		Button button = new Button();
		button.textProperty().bind(polyglot.get(property));
		button.setGraphic(image);
		button.setOnAction(handler);
		if (fill) {
			HBox.setHgrow(button, Priority.ALWAYS);
			button.setMaxWidth(Double.MAX_VALUE);
		}
		return button;
	}

	public void display(String titleProperty, double width, double height, View view, Modality modality) {
		Stage stage = new Stage();
		stage.initModality(modality);
		stage.titleProperty().bind(polyglot.get(titleProperty, Case.TITLE));
		Scene scene = new Scene(view, width, height);
		scene.getStylesheets().add(stylesheetPath);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	public Menu makeMenu(String titleProperty) {
		Menu menu = new Menu();
		menu.textProperty().bind(polyglot.get(titleProperty, Case.TITLE));
		return menu;
	}

	public MenuItem makeMenuItem(String title, EventHandler<ActionEvent> handler) {
		return new CustomMenuItem(title, handler);
	}

	public MenuItem makeMenuItem(StringBinding binding, EventHandler<ActionEvent> handler) {
		return new CustomMenuItem(binding, handler);
	}

	public MenuItem makeMenuItem(StringBinding binding, String keyCombination, EventHandler<ActionEvent> handler) {
		return new CustomMenuItem(binding, keyCombination, handler);
	}
	
	public CheckMenuItem makeCheckItem(StringBinding binding, EventHandler<ActionEvent> handler, boolean selected) {
		CheckMenuItem item = new CheckMenuItem();
		item.textProperty().bind(binding);
		item.setOnAction(handler);
		item.setSelected(selected);
		return item;
	}

	public void showProgressForTask(Task<Void> task, boolean showResult) {
		ProgressDialog dialog = new ProgressDialog(polyglot, IOResources);
		task.setOnSucceeded(event -> {
			dialog.getDialogStage().close();
			if (showResult) {
				showSuccess();
			}
		});
		task.setOnFailed(event -> {
			dialog.getDialogStage().close();
			if (showResult) {
				showFailure();
			}
		});
		Thread thread = new Thread(task);
		thread.start();
	}
	
	public void showSuccess() {
		Alert alert = makeAlert(AlertType.INFORMATION, "SuccessTitle", "SuccessHeader",
				polyglot.get("TaskSucceeded"));
		alert.show();
	}
	
	public void showFailure() {
		Alert alert = makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", polyglot.get("TaskFailed"));
		alert.show();
	}

}
