package utils.views;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Elliott Bolzan
 *
 *         This class represents a View in the program. Specific kinds of views
 *         can extend this class, like CollapsibleView. Every View must have a
 *         title.
 * 
 *         This code's purpose: providing a base GUI class for all views in the
 *         project.
 * 
 *         Why I think this code is well designed: this code is as minimal as it
 *         should be. A View, in our project, is characterized by the fact it
 *         has a title and by the fact it can have subcomponents. This class
 *         provides this capability without providing any extra bells and
 *         whistles.
 *
 */
public abstract class View extends BorderPane {

	private StringBinding title;

	/**
	 * Create a View.
	 */
	public View(StringBinding title) {
		this.title = title;
		setMinSize(0, 0);
		if(title!=null){
		createToolbar();}
	}

	public View() {
		this(null);
	}

	/**
	 * @return a StringBinding representing the View's title.
	 */
	public StringBinding getTitle() {
		return title;
	}
	
	/**
	 * Creates a toolbar and adds it to the top of the view.
	 */
	private void createToolbar() {
		HBox spacing = new HBox();
		spacing.maxWidth(Double.MAX_VALUE);
		HBox.setHgrow(spacing, Priority.ALWAYS);
		Label title = new Label();
		title.textProperty().bind(getTitle());
		ToolBar toolBar = new ToolBar(title, spacing,
				new Button("?"));
		toolBar.setPrefSize(getWidth(), 18);
		setTop(toolBar);
	}
	
}
