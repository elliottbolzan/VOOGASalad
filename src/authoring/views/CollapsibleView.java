package authoring.views;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.components.Direction;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Elliott Bolzan
 * 
 */
public abstract class CollapsibleView extends View {

	private Workspace workspace;
	private SplitPane owner;
	private int dividerIndex;
	private Direction collapseDirection;

	/**
	 * Creates a CollapsibleView.
	 * 
	 * @param workspace
	 *            the Workspace that owns the CollapsibleView.
	 * @param owner
	 *            the SplitPane the view is placed in.
	 * @param dividerIndex
	 *            the index of the divider the view depends on.
	 * @param collapseDirection
	 *            the direction the view needs to collapse in.
	 * @param hasToolbar
	 *            whether a toolbar should be displayed.
	 */
	public CollapsibleView(Workspace workspace, SplitPane owner, String title, int dividerIndex, Direction collapseDirection,
			boolean hasToolbar) {
		super(title);
		this.workspace = workspace;
		this.owner = owner;
		this.dividerIndex = dividerIndex;
		this.collapseDirection = collapseDirection;
		if (hasToolbar) {
			createToolbar();
		}
	}

	/**
	 * Creates a toolbar and adds it to the top of the view.
	 */
	private void createToolbar() {
		HBox spacing = new HBox();
		spacing.maxWidth(Double.MAX_VALUE);
		HBox.setHgrow(spacing, Priority.ALWAYS);
		ToolBar toolBar = new ToolBar(new Label(getTitle()), spacing,
				makeMinimizeButton(workspace.getResources().getString("MinimizePath")));
		toolBar.setPrefSize(getWidth(), 18);
		setTop(toolBar);
	}

	/**
	 * Creates a minimize button.
	 * 
	 * @param imagePath
	 *            the path to the minimize button's image.
	 * @return a minimize button.
	 */
	private Button makeMinimizeButton(String imagePath) {
		ComponentMaker componentMaker = new ComponentMaker(workspace.getResources());
		return componentMaker.makeTabButton(imagePath, e -> minimize(), "minimize", 16, 10);
	}

	/**
	 * Minimize the view by modifying the SplitPane it is located in.
	 */
	private void minimize() {
		owner.setDividerPosition(dividerIndex,
				(collapseDirection == Direction.RIGHT || collapseDirection == Direction.BACK) ? 1 : 0);
	}

}
