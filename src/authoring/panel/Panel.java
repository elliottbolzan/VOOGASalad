package authoring.panel;

import java.util.ArrayList;
import java.util.List;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.components.Direction;
import authoring.panel.achievements.Achievements;
import authoring.panel.chat.Chat;
import authoring.panel.display.EntityDisplay;
import authoring.panel.settings.Settings;
import authoring.views.CollapsibleView;
import authoring.views.View;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * @author Elliott Bolzan
 *
 *         A class that encapsulates a panel architecture. New subpanels can be
 *         added using a single line of code, placed in the createSubviews()
 *         method. This system seeks to facilitate the addition of new subviews
 *         and to reduce duplicated boilerplate code.
 */
public class Panel extends CollapsibleView {

	private Workspace workspace;
	private List<View> subviews;
	private EntityDisplay entityDisplay;
	private Settings settings;
	private LayerPanel layerPanel;
	private Achievements achievements;

	/**
	 * Returns a Panel.
	 * 
	 * @param workspace
	 *            the Workspace that owns the Panel.
	 * @param index
	 *            the index of the divider that the Panel collapses to, in the
	 *            SplitPane that owns it.
	 */
	public Panel(Workspace workspace, int index) {
		super(workspace, workspace.getPane(), workspace.getResources().getString("PanelTitle"), index, Direction.LEFT,
				true);
		this.workspace = workspace;
		entityDisplay = new EntityDisplay(workspace);
		settings = new Settings(workspace);
		layerPanel = new LayerPanel(workspace);
		achievements = new Achievements(workspace);
		createSubviews();
		setup();
	}

	/**
	 * Populate the subviews List, which dictates which subviews appear in the
	 * Accordion.
	 */
	private void createSubviews() {
		subviews = new ArrayList<View>();
		subviews.add(entityDisplay);
		subviews.add(new Chat(workspace));
		subviews.add(layerPanel);
		subviews.add(achievements);
		subviews.add(settings);
	}

	/**
	 * Create the Accordion and add it to the view.
	 */
	private void setup() {
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		setCenter(maker.makeAccordion(subviews));
		Button save = maker.makeButton("SaveButtonSettings", e -> workspace.save(), true);
		Button test = maker.makeButton("TestButtonSettings", e -> workspace.test(workspace.getGame()), true);
		test.setDisable(!workspace.pathExists());
		setBottom(new VBox(save, test));
	}

	/**
	 * @return the EntityDisplay.
	 */
	public EntityDisplay getEntityDisplay() {
		return entityDisplay;
	}

	/**
	 * @return the Settings subview.
	 */
	public Settings getSettings() {
		return settings;
	}
	

	/**
	 * When the user switches between level tabs or selects a new level, the layerPanel must be notified so that the 
	 * combobox will show only the names of the layers contained in the new level.
	 * @param layerNum
	 */

	/**
	 * Select an existing level.
	 * 
	 * @param layerNum
	 *            the layer to be selected.
	 */
	public void selectExistingLevelBox(String oldLevel, String newLevel) {
		layerPanel.selectLevelBox(oldLevel, newLevel);
	}

}