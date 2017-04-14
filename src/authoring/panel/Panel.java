package authoring.panel;

import java.util.ArrayList;
import java.util.List;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.panel.achievements.Achievements;
import authoring.panel.chat.Chat;
import authoring.panel.display.EntityDisplay;
import authoring.panel.settings.Settings;
import authoring.utils.Direction;
import authoring.views.CollapsibleView;
import authoring.views.View;
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
		setBottom(new VBox(maker.makeButton("SaveButtonSettings", e -> workspace.save(), true),
				maker.makeButton("TestButtonSettings", e -> workspace.test(workspace.getGame()), true)));
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
	 * When a new layer is selected by the LevelEditor, the panel must notify the layerPanel of the change and instruct
	 * it to update the value it displays.
	 * @param newLayer
	 */

	/**
	 * Update the layer panel with a new layer.
	 * 
	 * @param newLayer
	 *            the layer to be added to the layer panel.
	 */
	public void updateLayerPanel(String newLayer) {
		layerPanel.updateBox(newLayer);
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
	public void selectExistingLevelBox(int layerNum) {
		layerPanel.selectLevelBox(layerNum);
	}

}